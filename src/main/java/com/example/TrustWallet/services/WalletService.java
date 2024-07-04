package com.example.TrustWallet.services;

import com.example.TrustWallet.data.model.Transaction;
import com.example.TrustWallet.dto.request.CreateAccountRequest;
import com.example.TrustWallet.dto.request.InitializeTransaction;
import com.example.TrustWallet.dto.response.CreateAccountResponse;
import com.example.TrustWallet.dto.response.InitializeTransactionResponse;

import java.util.List;

public interface WalletService {

    InitializeTransactionResponse initializeTransaction(InitializeTransaction request);

    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest);

    List<Transaction> GetTransactions(String account_number);
}
