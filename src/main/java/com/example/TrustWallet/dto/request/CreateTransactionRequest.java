package com.example.TrustWallet.dto.request;

import com.example.TrustWallet.data.model.PaymentStatus;
import com.example.TrustWallet.data.model.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequest {
    private Long walletId;
    private String description;
    private BigDecimal Amount;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
}
