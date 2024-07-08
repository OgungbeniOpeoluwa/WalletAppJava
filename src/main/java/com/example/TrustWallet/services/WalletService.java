package com.example.TrustWallet.services;

import com.example.TrustWallet.data.model.PaymentMedium;
import com.example.TrustWallet.data.model.PaymentStatus;
import com.example.TrustWallet.data.model.PaymentType;
import com.example.TrustWallet.data.model.Transaction;
import com.example.TrustWallet.dto.request.CreateAccountRequest;
import com.example.TrustWallet.dto.request.CreatePaymentInitializeRequest;
import com.example.TrustWallet.dto.response.CreateAccountResponse;
import com.example.TrustWallet.dto.response.GetBalanceResponse;
import com.example.TrustWallet.dto.response.InitializeTransactionResponse;
import com.example.TrustWallet.dto.response.TransactionResponse;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.List;

public interface WalletService {

    InitializeTransactionResponse initializeTransaction(CreatePaymentInitializeRequest request);

    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest);

    List<TransactionResponse> GetTransactions(String account_number);

    void updateTransactions(String reference,PaymentStatus status);

    GetBalanceResponse getBalance(String number);
}
