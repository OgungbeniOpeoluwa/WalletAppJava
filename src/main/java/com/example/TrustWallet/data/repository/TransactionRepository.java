package com.example.TrustWallet.data.repository;

import com.example.TrustWallet.data.model.Transaction;
import com.example.TrustWallet.data.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,String> {
    List<Transaction> findByWallet(Wallet wallet);

//    Optional<Transaction> findByReference(String reference);
}
