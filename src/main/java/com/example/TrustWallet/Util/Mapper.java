package com.example.TrustWallet.Util;

import com.example.TrustWallet.config.BaseConfig;
import com.example.TrustWallet.data.model.User;
import com.example.TrustWallet.data.model.Wallet;
import com.example.TrustWallet.dto.request.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Arrays;


public class Mapper {
    public static PaymentServiceRequest<PaystackTransactionRequest> createInitializeTransactionRequest(InitializeTransaction req){
        int amount =req.getAmount().intValue()*100;
        PaymentServiceRequest<PaystackTransactionRequest> request = new PaymentServiceRequest<>();
        PaystackTransactionRequest transactionRequest = new PaystackTransactionRequest();
        transactionRequest.setEmail(req.getUser().getEmail());
        transactionRequest.setAmount(BigDecimal.valueOf(amount));
        transactionRequest.setReference(req.getReferenceId());
        request.setT(transactionRequest);
        return request;
    }

    public static PaymentServiceRequest<MonifyInitializeRequest> createInitializeMonifyRequest(InitializeTransaction req){
        PaymentServiceRequest<MonifyInitializeRequest> request = new PaymentServiceRequest<>();
        MonifyInitializeRequest transactionRequest = new MonifyInitializeRequest();
        transactionRequest.setPaymentDescription(req.getDescription());
        transactionRequest.setAmount(req.getAmount());
        transactionRequest.setCustomerEmail(req.getUser().getEmail());
        transactionRequest.setCurrencyCode(req.getCurrency());
        transactionRequest.setCustomerName(req.getUser().getFirstName());
        transactionRequest.setPaymentReference(req.getReferenceId());
        request.setT(transactionRequest);
        System.out.println(transactionRequest);
        return request;
    }

    public static Wallet mapWallet(CreateAccountRequest createAccountRequest, User user) {
        Wallet wallet = new Wallet();
        wallet.setAccountNumber(user.getPhonenumber());
        wallet.setPassword(createAccountRequest.getPassword());
        wallet.setUser(user);
        return wallet;
    }

    public static JsonNode parse(String value) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(value);
    }

}
