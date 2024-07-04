package com.example.TrustWallet.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentServiceRequest<T>{
    private  T t;
}
