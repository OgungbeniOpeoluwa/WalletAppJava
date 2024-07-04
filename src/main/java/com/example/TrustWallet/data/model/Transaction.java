package com.example.TrustWallet.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private BigDecimal amount;
    private String description;
    private String recipientName;
    private PaymentType paymentType;
    @ManyToOne
    private Wallet wallet;
    @Enumerated
    private PaymentStatus status ;

}
