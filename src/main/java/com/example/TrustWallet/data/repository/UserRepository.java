package com.example.TrustWallet.data.repository;

import com.example.TrustWallet.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhonenumber(String number);
}
