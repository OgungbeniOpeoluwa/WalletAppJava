package com.example.TrustWallet.dto.request;

import com.example.TrustWallet.data.model.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class CreatePaymentInitializeRequest {
    private int amount;
    private String currency;
    private String accountNumber;
    private String description;
    private String paymentMedium;
}
