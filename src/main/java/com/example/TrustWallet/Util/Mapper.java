package com.example.TrustWallet.Util;

import com.example.TrustWallet.config.BaseConfig;
import com.example.TrustWallet.dto.request.InitializeTransaction;
import com.example.TrustWallet.dto.request.MonifyInitializeRequest;
import com.example.TrustWallet.dto.request.PaymentServiceRequest;
import com.example.TrustWallet.dto.request.PaystackTransactionRequest;

import java.util.Arrays;


public class Mapper {
    public static PaymentServiceRequest<PaystackTransactionRequest> createInitializeTransactionRequest(InitializeTransaction req){
        PaymentServiceRequest<PaystackTransactionRequest> request = new PaymentServiceRequest<>();
        PaystackTransactionRequest transactionRequest = new PaystackTransactionRequest();
        transactionRequest.setEmail(req.getUser().getEmail());
        transactionRequest.setAmount(req.getAmount());
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

}
