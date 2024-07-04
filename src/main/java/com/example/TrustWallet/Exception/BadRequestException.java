package com.example.TrustWallet.Exception;

import org.springframework.http.HttpStatusCode;

public class BadRequestException extends TrustWalletException{

    public BadRequestException(String message) {
        super(message);
    }
}
