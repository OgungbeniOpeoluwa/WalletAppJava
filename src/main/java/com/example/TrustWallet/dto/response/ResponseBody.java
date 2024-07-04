package com.example.TrustWallet.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseBody {
    private String transactionReference;
    private String paymentReference;
    private String checkoutUrl;
}
