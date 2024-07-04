package com.example.TrustWallet.dto.request;

import lombok.Getter;
import lombok.Setter;


public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
}
