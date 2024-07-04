package com.example.TrustWallet.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaystackWebhookReference {
    private String event;
    private Data data;
}
