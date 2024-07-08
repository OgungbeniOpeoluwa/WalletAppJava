package com.example.TrustWallet.services;

import com.example.TrustWallet.Exception.*;
import com.example.TrustWallet.Util.Mapper;
import com.example.TrustWallet.Util.validation.ValidateRequest;
import com.example.TrustWallet.data.model.*;
import com.example.TrustWallet.data.model.Currency;
import com.example.TrustWallet.data.repository.WalletRepository;
import com.example.TrustWallet.dto.request.*;
import com.example.TrustWallet.dto.response.CreateAccountResponse;
import com.example.TrustWallet.dto.response.GetBalanceResponse;
import com.example.TrustWallet.dto.response.InitializeTransactionResponse;
import com.example.TrustWallet.dto.response.TransactionResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;


@Service
@AllArgsConstructor
public class TrustWalletServiceImpl implements WalletService{
    private WalletRepository walletRepository;
    private UserService userService;
    private ModelMapper mapper;
    private PaymentService  paystackPaymentService;
    private PaymentService  monifyPaymentService;
    private TransactionService transactionService;
    @Override
    public InitializeTransactionResponse initializeTransaction(CreatePaymentInitializeRequest request)  {
        InitializeTransaction req = mapper.map(request,InitializeTransaction.class);
        ValidateRequest<CreatePaymentInitializeRequest> validate = new ValidateRequest<>();
        List<String> fieldNames = validate.checkIfFieldsAreNull(request);
        validationCheck(request, fieldNames);
        User user = userService.getUserPhoneNumber(request.getAccountNumber());
        Wallet wallet = walletRepository.findByUser(user);
        req.setUser(user);
        Transaction transaction = createTransaction(req, wallet);
        req.setReferenceId(transaction.getId());
        InitializeTransactionResponse res = InitializeTransactionResponse(req);
        if(res.getMessage() != null)transactionService.updateTransactionStatus(new UpdateTransactionRequest(PaymentStatus.CANCELED,transaction.getId()));
        return res;
    }

    private void validationCheck(CreatePaymentInitializeRequest request, List<String> fieldNames) {
        checkFieldNames(fieldNames);
        validateRequest(request.getPaymentMedium(),PaymentMedium.class.getDeclaredFields(),new InvalidPaymentMediumException("Invalid payment medium"));
        validateRequest(request.getCurrency(), Currency.class.getDeclaredFields(),new InvalidCurrencyException("Invalid currency"));
        if(request.getAmount() < 10)throw new InvalidAmountException("Invalid amount");
    }

    private Transaction createTransaction(InitializeTransaction request, Wallet wallet) {
        CreateTransactionRequest req = new CreateTransactionRequest();
        req.setWalletId(wallet.getId());
        req.setPaymentType(PaymentType.CREDIT);
        req.setDescription(request.getDescription());
        req.setAmount(request.getAmount());
        req.setPaymentStatus(PaymentStatus.PROCESSING);
        return transactionService.createTransaction(req);
    }

    private InitializeTransactionResponse InitializeTransactionResponse(InitializeTransaction request) {
        if(request.getPaymentMedium().toUpperCase().equals(PaymentMedium.PAYSTACK.name())) {
            PaymentServiceRequest<PaystackTransactionRequest> request1 = Mapper.createInitializeTransactionRequest(request);
            return  paystackPaymentService.InitializeTransaction(request1);
        }
        else {
        PaymentServiceRequest<MonifyInitializeRequest> request1 = Mapper.createInitializeMonifyRequest(request);
            return monifyPaymentService.InitializeTransaction(request1);
        }
    }

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {
        ValidateRequest<CreateAccountRequest> validate = new ValidateRequest<>();
        List<String> fieldNames = validate.checkIfFieldsAreNull(createAccountRequest);
        checkFieldNames(fieldNames);
        CreateUserRequest userRequest = mapper.map(createAccountRequest,CreateUserRequest.class);
        User user = userService.createUser(userRequest);
        Wallet wallet =Mapper.mapWallet(createAccountRequest, user);
        walletRepository.save(wallet);
        return new CreateAccountResponse(wallet.getAccountNumber());

    }



    private static void checkFieldNames(List<String> fieldNames) {
        if (!fieldNames.isEmpty()) {
            String fields  = String.join(",", fieldNames);
            throw new InvalidRequestException(fields+" cant be empty");
        }
    }
    private void updateWalletBalance(Long id, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new WalletExistException("Wallet doesnt exist"));
        wallet.setAmount(wallet.getAmount().add(amount));
        walletRepository.save(wallet);
    }

    @Override
    public List<TransactionResponse> GetTransactions(String account_number) {
        Optional<Wallet> wallet = walletRepository.findByAccountNumber(account_number);
        return transactionService.GetAllByWalletId(wallet.orElseThrow(()->new WalletExistException("No wallet for account number provided")));
    }

    private void validateRequest(String paymentMedium, Field[]fields,RuntimeException e) {
        Arrays.stream(fields).filter(x->x.getName().equalsIgnoreCase(paymentMedium))
                .findAny().orElseThrow(()->e);
    }
    @Override
    public void updateTransactions(String reference,PaymentStatus value){
        Transaction transactions = transactionService.findById(reference);
        UpdateTransactionRequest transactionRequest = new UpdateTransactionRequest();
        transactionRequest.setId(transactions.getId());
        if(value.equals(PaymentStatus.SUCCESSFUL)) {
            transactionRequest.setStatus(value);
            updateWalletBalance(transactions.getWallet().getId(),transactions.getAmount());
        }else {
            transactionRequest.setStatus(value);
        }
        transactionService.updateTransactionStatus(transactionRequest);
    }

    @Override
    public GetBalanceResponse getBalance(String number) {
        Wallet wallet = walletRepository.findByAccountNumber(number)
                .orElseThrow(()->new WalletExistException("Wallet doesn't exist"));
        return new GetBalanceResponse(wallet.getAmount());
    }


}

