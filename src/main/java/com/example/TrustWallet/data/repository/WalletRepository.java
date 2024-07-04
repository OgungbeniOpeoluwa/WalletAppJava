package com.example.TrustWallet.data.repository;

import com.example.TrustWallet.data.model.User;
import com.example.TrustWallet.data.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository  extends JpaRepository<Wallet,Long> {
    Wallet findByUser(User user);

    Optional<Wallet> findByAccountNumber(String accountNumber);
}
