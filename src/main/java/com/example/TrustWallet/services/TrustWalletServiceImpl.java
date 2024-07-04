package com.example.TrustWallet.services;

import com.example.TrustWallet.Exception.InvalidAmountException;
import com.example.TrustWallet.Exception.InvalidCurrencyException;
import com.example.TrustWallet.Exception.InvalidPaymentMediumException;
import com.example.TrustWallet.Exception.WalletExistException;
import com.example.TrustWallet.Util.HttpRequest;
import com.example.TrustWallet.Util.Mapper;
import com.example.TrustWallet.Util.Utility;
import com.example.TrustWallet.config.BaseConfig;
import com.example.TrustWallet.data.model.*;
import com.example.TrustWallet.data.repository.WalletRepository;
import com.example.TrustWallet.dto.request.*;
import com.example.TrustWallet.dto.response.CreateAccountResponse;
import com.example.TrustWallet.dto.response.InitializeTransactionResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    public InitializeTransactionResponse initializeTransaction(InitializeTransaction request)  {
        validateRequest(request.getPaymentMedium(),PaymentMedium.class.getDeclaredFields(),new InvalidPaymentMediumException("Invalid payment medium"));
        validateRequest(request.getCurrency(), Currency.class.getDeclaredFields(),new InvalidCurrencyException("Invalid currency"));
        if(request.getAmount().intValue() < 10)throw new InvalidAmountException("Invalid amount");
        User user = userService.getUserPhoneNumber(request.getAccountNumber());
        Wallet wallet = walletRepository.findByUser(user);
        request.setUser(user);
        Transaction transaction = createTransaction(request, wallet);
        request.setReferenceId(transaction.getId());
        InitializeTransactionResponse res = InitializeTransactionResponse(request);
        if(res.getMessage() != null)transactionService.updateTransactionStatus(new UpdateTransactionRequest(PaymentStatus.CANCELED,transaction.getId()));
;        return res;
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
        CreateUserRequest userRequest = mapper.map(createAccountRequest,CreateUserRequest.class);
        User user = userService.createUser(userRequest);
        Wallet wallet = new Wallet();
        wallet.setAccountNumber(user.getPhonenumber());
        wallet.setUser(user);
        walletRepository.save(wallet);
        return new CreateAccountResponse(wallet.getAccountNumber());

    }

    @Override
    public List<Transaction> GetTransactions(String account_number) {
        Optional<Wallet> wallet = walletRepository.findByAccountNumber(account_number);
        return transactionService.GetAllByWalletId(wallet.orElseThrow(()->new WalletExistException("No wallet for account number provided")));
    }

    private void validateRequest(String paymentMedium, Field[]fields,RuntimeException e) {
        Arrays.stream(fields).filter(x->x.getName().equals(paymentMedium.toUpperCase()))
                .findAny().orElseThrow(()->e);
    }


}

