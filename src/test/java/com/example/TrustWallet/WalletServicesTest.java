package com.example.TrustWallet;

import com.example.TrustWallet.Exception.InvalidAmountException;
import com.example.TrustWallet.Exception.InvalidCurrencyException;
import com.example.TrustWallet.Exception.InvalidPaymentMediumException;
import com.example.TrustWallet.dto.request.CreateAccountRequest;
import com.example.TrustWallet.dto.request.CreatePaymentInitializeRequest;
import com.example.TrustWallet.Exception.UserExistException;
import com.example.TrustWallet.dto.response.GetBalanceResponse;
import com.example.TrustWallet.dto.response.InitializeTransactionResponse;
import com.example.TrustWallet.dto.response.TransactionResponse;
import com.example.TrustWallet.services.TransactionService;
import com.example.TrustWallet.services.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

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
    public void testCreateAccountThrowsExceptionIfUserAlreadyExist() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setEmail("opeoluwa@gmail.com");
        createAccountRequest.setFirstName("opeoluwa");
        createAccountRequest.setPhoneNumber("07066221008");
        createAccountRequest.setLastName("shola");
        assertThrows(UserExistException.class, () -> walletService.createAccount(createAccountRequest));

    }

    @Test
    public void testThatInvalidPaymentMediumThrowsErrorWhenEntered() {
        CreatePaymentInitializeRequest request = new CreatePaymentInitializeRequest();
        request.setPaymentMedium("Kuda");
        request.setAmount(1000);
        request.setDescription("Payment for hair");
        request.setCurrency("NGN");
        request.setAccountNumber("7066221008");
        assertThrows(InvalidPaymentMediumException.class, () -> walletService.initializeTransaction(request));
    }

    @Test
    public void testThatInvalidCurrencyThrowsErrorWhenEntered() {
        CreatePaymentInitializeRequest request = new CreatePaymentInitializeRequest();
        request.setPaymentMedium("paystack");
        request.setAmount(1000);
        request.setDescription("Payment for hair");
        request.setCurrency("NGNs");
        assertThrows(InvalidCurrencyException.class, () -> walletService.initializeTransaction(request));

    }

    @Test
    @Sql(scripts = {"/scripts/insert.sql"})
    public void testInitializeTransactionUsingPaystackPaymentService() throws NoSuchFieldException {
        CreatePaymentInitializeRequest request = new CreatePaymentInitializeRequest();
        request.setPaymentMedium("paystack");
        request.setAmount(1000);
        request.setDescription("Payment for hair");
        request.setCurrency("NGN");
        request.setAccountNumber("07066221008");
        InitializeTransactionResponse res = walletService.initializeTransaction(request);
        assertThat(res.getUrl()).isNotNull();

    }

    @Test
    public void testThatIfAmountIsLesserThan10ThrowAnException() {
        CreatePaymentInitializeRequest request = new CreatePaymentInitializeRequest();
        request.setPaymentMedium("paystack");
        request.setAmount(5);
        request.setDescription("Payment for hair");
        request.setCurrency("NGN");
        request.setAccountNumber("07066221908");
        assertThrows(InvalidAmountException.class, () -> walletService.initializeTransaction(request));

    }

    @Test
    public void testThatIfAccountNumberDoesntExistThrowsAnException() {
        CreatePaymentInitializeRequest request = new CreatePaymentInitializeRequest();
        request.setPaymentMedium("paystack");
        request.setAmount(10);
        request.setDescription("Payment for hair");
        request.setCurrency("NGN");
        request.setAccountNumber("07066221908");
        assertThrows(UserExistException.class, () -> walletService.initializeTransaction(request));
    }

    @Test
    @Sql(scripts = {"/scripts/insert.sql"})
    public void testInitializeTransactionUsingMonifyPaymentService() throws NoSuchFieldException {
        CreatePaymentInitializeRequest request = new CreatePaymentInitializeRequest();
        request.setPaymentMedium("monnify");
        request.setAmount(1000);
        request.setDescription("Payment for hair");
        request.setCurrency("NGN");
        request.setAccountNumber("07066221008");
        InitializeTransactionResponse res = walletService.initializeTransaction(request);
        assertThat(res.getUrl()).isNotNull();
    }

    @Test
    @Sql(scripts = {"/scripts/insert.sql"})
    public void testThatTransactionIsCreatedWhenPaymentIsInitialized() throws NoSuchFieldException {
        CreatePaymentInitializeRequest request = new CreatePaymentInitializeRequest();
        request.setPaymentMedium("monnify");
        request.setAmount(1000);
        request.setDescription("Payment for hair");
        request.setCurrency("NGN");
        request.setAccountNumber("07066221008");
        InitializeTransactionResponse res = walletService.initializeTransaction(request);
        List<TransactionResponse> transactionList = walletService.GetTransactions("07066221008");
        assertThat(transactionList.size()).isEqualTo(1);
    }
@Test
@Sql(scripts = {"/scripts/insert.sql"})
    public void testGetBalance() {
        GetBalanceResponse response = walletService.getBalance("07066221008");
        assertThat(response).isNotNull();
        assertThat(response.getBalance()).isEqualTo(1000.00);


    }
}
