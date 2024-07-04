package com.example.TrustWallet.services;

import com.example.TrustWallet.dto.request.InitializeTransaction;
import com.example.TrustWallet.dto.request.PaymentServiceRequest;
import com.example.TrustWallet.dto.response.InitializeTransactionResponse;
import org.hibernate.type.descriptor.java.ObjectJavaType;

import java.util.Map;

public interface PaymentService {
    InitializeTransactionResponse InitializeTransaction(PaymentServiceRequest<?> body);
}
