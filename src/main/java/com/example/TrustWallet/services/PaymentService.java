package com.example.TrustWallet.services;

import com.example.TrustWallet.dto.request.PaymentServiceRequest;
import com.example.TrustWallet.dto.response.InitializeTransactionResponse;

public interface PaymentService {
    InitializeTransactionResponse InitializeTransaction(PaymentServiceRequest<?> body);
}
