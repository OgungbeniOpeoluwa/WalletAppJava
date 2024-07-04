package com.example.TrustWallet.dto.request;

import com.example.TrustWallet.data.model.User;

import java.math.BigDecimal;

public class CreatePaymentInitializeRequest {
    private BigDecimal amount;
    private String currency;
    private String accountNumber;
    private String description;
    private String paymentMedium;
    private User user;
    private String referenceId;
}
