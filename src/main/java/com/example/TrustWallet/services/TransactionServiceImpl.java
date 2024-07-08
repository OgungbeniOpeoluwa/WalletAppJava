package com.example.TrustWallet.services;

import com.example.TrustWallet.Exception.TransactionExistException;
import com.example.TrustWallet.data.model.Transaction;
import com.example.TrustWallet.data.model.Wallet;
import com.example.TrustWallet.data.repository.TransactionRepository;
import com.example.TrustWallet.dto.request.CreateTransactionRequest;
import com.example.TrustWallet.dto.request.UpdateTransactionRequest;
import com.example.TrustWallet.dto.response.TransactionResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private TransactionRepository repository;
    private ModelMapper mapper;

    @Override
    public Transaction createTransaction(CreateTransactionRequest request) {
        Transaction transaction = mapper.map(request,Transaction.class);
        transaction = repository.save(transaction);
        return transaction;
    }

    @Override
    public Transaction findById(String id) {
        return repository.findById(id).orElseThrow(()->new TransactionExistException("transaction with provided id doesn't exist"));
    }

    @Override
    public Transaction updateTransactionStatus(UpdateTransactionRequest updateTransactionRequest) {
        Transaction transaction = findById(updateTransactionRequest.getId());
        if (updateTransactionRequest.getStatus() != null)transaction.setStatus(updateTransactionRequest.getStatus());
        transaction = repository.save(transaction);
        return transaction;
    }

    @Override
    public List<TransactionResponse> GetAllByWalletId(Wallet walletId) {
        List<Transaction> transactions =  repository.findByWallet(walletId);
       return transactions.stream().map((x)->mapper.map(x, TransactionResponse.class))
                .toList();
    }

}
