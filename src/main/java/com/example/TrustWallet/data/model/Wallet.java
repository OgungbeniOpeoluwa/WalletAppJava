package com.example.TrustWallet.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal amount = BigDecimal.valueOf(0);
    @OneToOne
    private User user;
    private String accountNumber;
    private String password;


}
