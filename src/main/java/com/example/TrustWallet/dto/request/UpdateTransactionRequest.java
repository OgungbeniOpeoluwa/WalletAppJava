package com.example.TrustWallet.dto.request;

import com.example.TrustWallet.data.model.PaymentStatus;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransactionRequest {
    private PaymentStatus status;
//    private String reference;
    private  String id;
}
