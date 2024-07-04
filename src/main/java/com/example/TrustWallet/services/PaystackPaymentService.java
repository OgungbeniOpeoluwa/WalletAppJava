package com.example.TrustWallet.services;

import com.example.TrustWallet.Exception.BadRequestException;
import com.example.TrustWallet.Util.HttpRequest;
import com.example.TrustWallet.config.BaseConfig;
import com.example.TrustWallet.dto.request.InitializeTransaction;
import com.example.TrustWallet.dto.request.PaymentServiceRequest;
import com.example.TrustWallet.dto.request.PaystackTransactionRequest;
import com.example.TrustWallet.dto.response.InitializeTransactionResponse;
import com.example.TrustWallet.dto.response.PaystackTransactionResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaystackPaymentService implements PaymentService{
    private BaseConfig baseConfig;
    private HttpRequest request;
    @Override
    public InitializeTransactionResponse InitializeTransaction(PaymentServiceRequest<?> body) {
        PaystackTransactionResponse requests = new PaystackTransactionResponse();
        String key = "Bearer "+ baseConfig.getPaystackSecretKey();
        String url = baseConfig.getPaystackBaseUrl()+"/transaction/initialize";
        try {
            requests = (PaystackTransactionResponse)request.sendHttpRequest(body.getT(),url,key,requests);
            return new InitializeTransactionResponse(requests.getData().getAuthorization_url(),null);
        }catch (BadRequestException exception){
            return new InitializeTransactionResponse(null,requests.getMessage());
        }
    }
}
