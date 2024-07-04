package com.example.TrustWallet.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class PaystackTransactionResponse {
    private boolean status;
    private String message;
    private Data data;

}
