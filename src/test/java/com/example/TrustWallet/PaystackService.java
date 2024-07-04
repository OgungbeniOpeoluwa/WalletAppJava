package com.example.TrustWallet;

import com.example.TrustWallet.dto.request.PaymentServiceRequest;
import com.example.TrustWallet.dto.request.PaystackTransactionRequest;
import com.example.TrustWallet.dto.response.InitializeTransactionResponse;
import com.example.TrustWallet.services.PaystackPaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PaystackService {
    @Autowired
    PaystackPaymentService paymentService;

    @Test
    public void testInitializeTransaction(){
        PaymentServiceRequest<PaystackTransactionRequest> request = new PaymentServiceRequest<>();
        PaystackTransactionRequest request1 = new PaystackTransactionRequest();
        request1.setAmount(BigDecimal.valueOf(1000));
        request1.setEmail("opeoluwa@gmail.com");
        request.setT(request1);
        InitializeTransactionResponse res = paymentService.InitializeTransaction(request);
        System.out.println(res.getUrl());
        assertThat(res.getUrl()).isNotNull();
    }
}
