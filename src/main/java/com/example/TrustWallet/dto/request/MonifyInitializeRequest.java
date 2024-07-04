package com.example.TrustWallet.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class MonifyInitializeRequest {
    private BigDecimal amount;
    private String customerName;
    private String customerEmail;
    private String paymentDescription;
    private String paymentReference;
    private String currencyCode;
    private String contractCode;

}

