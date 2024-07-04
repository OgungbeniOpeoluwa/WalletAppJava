package com.example.TrustWallet.Exception;

public class TransactionExistException extends TrustWalletException{
    public TransactionExistException(String message) {
        super(message);
    }
}
