package com.example.TrustWallet.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class PaystackTransactionRequest {
    private BigDecimal amount;
    private String  email;
    private  String reference;
}
