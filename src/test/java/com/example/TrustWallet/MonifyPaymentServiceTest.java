package com.example.TrustWallet;

import com.example.TrustWallet.config.BaseConfig;
import com.example.TrustWallet.dto.request.MonifyInitializeRequest;
import com.example.TrustWallet.dto.request.PaymentServiceRequest;
import com.example.TrustWallet.dto.response.InitializeTransactionResponse;
import com.example.TrustWallet.services.MonifyPaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.BIG_DECIMAL;

@SpringBootTest
public class MonifyPaymentServiceTest {
    @Autowired
    MonifyPaymentService monifyPaymentService;
    @Autowired
    private BaseConfig baseConfig;
    @Test
    public void testInitializeTransaction(){
        PaymentServiceRequest<MonifyInitializeRequest> request = new PaymentServiceRequest<>();
        MonifyInitializeRequest body = new MonifyInitializeRequest();
        body.setAmount(BigDecimal.valueOf(1000));
        body.setCustomerEmail("sholq@gmail.com");
        body.setCustomerName("opeoluwa");
        body.setCurrencyCode("NGN");
        body.setPaymentDescription("my hair payment");
        body.setPaymentReference("7f46a2c79b63ci4e688dbf987c5d99b2b99");
        body.setContractCode(baseConfig.getContractCode());
        request.setT(body);
        InitializeTransactionResponse res = monifyPaymentService.InitializeTransaction(request);
        System.out.println(res.getUrl());
        assertThat(res.getUrl()).isNotNull();
    }
}
