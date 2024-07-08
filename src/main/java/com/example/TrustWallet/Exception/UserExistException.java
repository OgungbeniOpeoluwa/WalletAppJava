package com.example.TrustWallet.Exception;

import com.example.TrustWallet.Exception.TrustWalletException;
import com.example.TrustWallet.TrustWalletApplication;
import lombok.Getter;
import lombok.Setter;


public class UserExistException extends TrustWalletException {
    public UserExistException(String message) {
        super(message);
    }
}
