package com.example.TrustWallet.dto.request;

import com.example.TrustWallet.data.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UpdateTransactionRequest {
    private PaymentStatus status;
    private  String id;
}
