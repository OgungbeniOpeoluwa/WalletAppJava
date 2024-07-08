package com.example.TrustWallet.dto.response;

import com.example.TrustWallet.data.model.PaymentStatus;
import com.example.TrustWallet.data.model.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class TransactionResponse {
    private String id;
    private BigDecimal amount;
    private String description;
    private PaymentType paymentType;
    private String reference;
    private PaymentStatus status ;
}
