package com.example.TrustWallet.Util.validation;

import com.example.TrustWallet.Exception.InvalidPaymentMediumException;
import com.example.TrustWallet.data.model.PaymentMedium;

import java.util.Arrays;

public class validate {

    private static void validatePaymentMedium(String paymentMedium){
        Arrays.stream(PaymentMedium.class.getDeclaredFields())
                .filter(x->x.getName().equals(paymentMedium.toUpperCase()))
                .findAny().orElseThrow(()->new InvalidPaymentMediumException("Payment medium doesnt exist"));

    }
}
