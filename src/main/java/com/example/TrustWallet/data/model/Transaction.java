package com.example.TrustWallet.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private BigDecimal amount;
    private String description;
    private PaymentType paymentType;
//    private String reference;
    @ManyToOne(fetch = FetchType.EAGER)
    private Wallet wallet;
    @Enumerated
    private PaymentStatus status ;
    private LocalDate createdAt= LocalDate.now();

}
