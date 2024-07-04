package com.example.TrustWallet;

import com.example.TrustWallet.Exception.InvalidAmountException;
import com.example.TrustWallet.Exception.InvalidCurrencyException;
import com.example.TrustWallet.Exception.InvalidPaymentMediumException;
import com.example.TrustWallet.data.model.Transaction;
import com.example.TrustWallet.dto.request.CreateAccountRequest;
import com.example.TrustWallet.dto.request.InitializeTransaction;
import com.example.TrustWallet.dto.request.UserExistException;
import com.example.TrustWallet.dto.response.InitializeTransactionResponse;
import com.example.TrustWallet.services.TransactionService;
import com.example.TrustWallet.services.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class WalletServicesTest {
    @Autowired
    WalletService walletService;
    @Autowired
    TransactionService transactionService;
    @Test
    @Sql(scripts = {"/scripts/insert.sql"})
    public  void testCreateAccountThrowsExceptionIfUserAlreadyExist(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setEmail("opeoluwa@gmail.com");
        createAccountRequest.setFirstName("opeoluwa");
        createAccountRequest.setPhoneNumber("07066221008");
        createAccountRequest.setLastname("shola");
        assertThrows(UserExistException.class,()->walletService.createAccount(createAccountRequest));

    }
    @Test
    public void testThatInvalidPaymentMediumThrowsErrorWhenEntered(){
        InitializeTransaction request = new InitializeTransaction();
        request.setPaymentMedium("Kuda");
        request.setAmount(BigDecimal.valueOf(1000));
        request.setDescription("Payment for hair");
        request.setCurrency("NGN");
        request.setAccountNumber("7066221008");
        assertThrows(InvalidPaymentMediumException.class,()-> walletService.initializeTransaction(request));
    }
    @Test
    public void testThatInvalidCurrencyThrowsErrorWhenEntered(){
        InitializeTransaction request = new InitializeTransaction();
        request.setPaymentMedium("paystack");
        request.setAmount(BigDecimal.valueOf(1000));
        request.setDescription("Payment for hair");
        request.setCurrency("NGNs");
        assertThrows(InvalidCurrencyException.class,()-> walletService.initializeTransaction(request));

    }

    @Test
    @Sql(scripts = {"/scripts/insert.sql"})
    public void testInitializeTransactionUsingPaystackPaymentService() throws NoSuchFieldException {
        InitializeTransaction request = new InitializeTransaction();
        request.setPaymentMedium("paystack");
        request.setAmount(BigDecimal.valueOf(1000));
        request.setDescription("Payment for hair");
        request.setCurrency("NGN");
        request.setAccountNumber("07066221008");
        InitializeTransactionResponse res = walletService.initializeTransaction(request);
        assertThat(res.getUrl()).isNotNull();

    }
    @Test
    public void testThatIfAmountIsLesserThan10ThrowAnException(){
        InitializeTransaction request = new InitializeTransaction();
        request.setPaymentMedium("paystack");
        request.setAmount(BigDecimal.valueOf(5));
        request.setDescription("Payment for hair");
        request.setCurrency("NGN");
        request.setAccountNumber("07066221908");
       assertThrows(InvalidAmountException.class,()->walletService.initializeTransaction(request));

    }
    @Test
    public void testThatIfAccountNumberDoesntExistThrowsAnException(){
        InitializeTransaction request = new InitializeTransaction();
        request.setPaymentMedium("paystack");
        request.setAmount(BigDecimal.valueOf(10));
        request.setDescription("Payment for hair");
        request.setCurrency("NGN");
        request.setAccountNumber("07066221908");
        assertThrows(UserExistException.class,()->walletService.initializeTransaction(request));
    }
    @Test
    @Sql(scripts = {"/scripts/insert.sql"})
    public void  testInitializeTransactionUsingMonifyPaymentService() throws NoSuchFieldException {
        InitializeTransaction request = new InitializeTransaction();
        request.setPaymentMedium("monnify");
        request.setAmount(BigDecimal.valueOf(1000));
        request.setDescription("Payment for hair");
        request.setCurrency("NGN");
        request.setAccountNumber("07066221008");
        InitializeTransactionResponse res = walletService.initializeTransaction(request);
        assertThat(res.getUrl()).isNotNull();
    }
    @Test
    @Sql(scripts = {"/scripts/insert.sql"})
    public void testThatTransactionIsCreatedWhenPaymentIsInitialized() throws NoSuchFieldException {
        InitializeTransaction request = new InitializeTransaction();
        request.setPaymentMedium("monnify");
        request.setAmount(BigDecimal.valueOf(1000));
        request.setDescription("Payment for hair");
        request.setCurrency("NGN");
        request.setAccountNumber("07066221008");
        InitializeTransactionResponse res = walletService.initializeTransaction(request);
        List<Transaction> transactionList = walletService.GetTransactions("07066221008");
        assertThat(transactionList.size()).isEqualTo(1);
    }

}
