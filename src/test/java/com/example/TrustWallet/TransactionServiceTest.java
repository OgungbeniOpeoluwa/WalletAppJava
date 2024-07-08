package com.example.TrustWallet;

import com.example.TrustWallet.data.model.PaymentStatus;
import com.example.TrustWallet.data.model.PaymentType;
import com.example.TrustWallet.data.model.Transaction;
import com.example.TrustWallet.dto.request.CreateTransactionRequest;
import com.example.TrustWallet.dto.request.UpdateTransactionRequest;
import com.example.TrustWallet.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;
    @Test
    @Sql(scripts = {"/scripts/insert.sql"})
    public void TestCreateTransaction(){
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAmount(BigDecimal.valueOf(1000));
        request.setDescription("hair money");
        request.setWalletId(201L);
        request.setPaymentType(PaymentType.CREDIT);
        Transaction transaction = transactionService.createTransaction(request);
        assertThat(transaction.getId()).isNotNull();
    }
    @Test
    @Sql(scripts = {"/scripts/insert.sql"})
    public void testFindTransactionById(){
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAmount(BigDecimal.valueOf(1000));
        request.setDescription("hair money");
        request.setWalletId(201L);
        request.setPaymentType(PaymentType.CREDIT);
        String  id = transactionService.createTransaction(request).getId();
        Transaction transaction = transactionService.findById(id);
        assertThat(transaction.getId()).isEqualTo(id);


    }
    @Test
    @Sql(scripts = {"/scripts/insert.sql"})
    public void testUpdateTransactionStatus(){
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAmount(BigDecimal.valueOf(1000));
        request.setDescription("hair money");
        request.setWalletId(201L);
        request.setPaymentType(PaymentType.CREDIT);
        Transaction  transaction = transactionService.createTransaction(request);
        transaction = transactionService.updateTransactionStatus(new UpdateTransactionRequest(PaymentStatus.SUCCESSFUL,transaction.getId()));
        assertThat(transaction.getStatus()).isEqualTo(PaymentStatus.SUCCESSFUL);

    }
    @Test
    @Sql(scripts = {"/scripts/insert.sql"})
    public void testGetByReference(){
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAmount(BigDecimal.valueOf(1000));
        request.setDescription("hair money");
        request.setWalletId(201L);
        request.setPaymentType(PaymentType.CREDIT);
        request.setReference("4532tedoty");
        Transaction  transaction = transactionService.createTransaction(request);
        Transaction req = transactionService.findById(transaction.getId());
        assertThat(req.getId()).isEqualTo(transaction.getId());

    }
}
