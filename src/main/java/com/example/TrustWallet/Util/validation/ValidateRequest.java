package com.example.TrustWallet.Util.validation;

import com.example.TrustWallet.Exception.InvalidPaymentMediumException;
import com.example.TrustWallet.Exception.InvalidRequestException;
import com.example.TrustWallet.data.model.PaymentMedium;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ValidateRequest<T> {
    public List<String> checkIfFieldsAreNull(T createAccountRequest) {
        return Arrays.stream(createAccountRequest.getClass().getDeclaredFields()).filter(x-> {
                    x.setAccessible(true);
                    try {
                        return   x.get(createAccountRequest) == null;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).map(Field::getName).toList();
    }

}
