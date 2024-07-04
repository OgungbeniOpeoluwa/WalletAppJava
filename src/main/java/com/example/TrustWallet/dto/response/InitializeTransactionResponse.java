package com.example.TrustWallet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class InitializeTransactionResponse {
    private String url;
    private String message;

}
