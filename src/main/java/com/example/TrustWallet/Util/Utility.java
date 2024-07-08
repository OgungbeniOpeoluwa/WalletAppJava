package com.example.TrustWallet.Util;

import com.example.TrustWallet.data.model.PaymentMedium;
import com.example.TrustWallet.data.model.PaymentType;
import com.example.TrustWallet.data.model.User;
import com.example.TrustWallet.dto.request.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Utility {
    public static String convertToBase64(String value){
        return Base64.getEncoder().encodeToString(value.getBytes());

    }

}
