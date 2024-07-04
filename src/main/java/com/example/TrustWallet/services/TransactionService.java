package com.example.TrustWallet.services;

import com.example.TrustWallet.data.model.Transaction;
import com.example.TrustWallet.data.model.Wallet;
import com.example.TrustWallet.dto.request.CreateTransactionRequest;
import com.example.TrustWallet.dto.request.UpdateTransactionRequest;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(CreateTransactionRequest request);

    Transaction findById(String id);

    Transaction updateTransactionStatus(UpdateTransactionRequest updateTransactionRequest);

    List<Transaction> GetAllByWalletId(Wallet wallet);
}