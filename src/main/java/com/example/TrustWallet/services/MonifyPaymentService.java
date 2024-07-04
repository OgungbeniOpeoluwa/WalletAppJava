package com.example.TrustWallet.services;

import com.example.TrustWallet.Exception.BadRequestException;
import com.example.TrustWallet.Util.HttpRequest;
import com.example.TrustWallet.Util.Utility;
import com.example.TrustWallet.config.BaseConfig;
import com.example.TrustWallet.dto.request.InitializeTransaction;
import com.example.TrustWallet.dto.request.MonifyInitializeRequest;
import com.example.TrustWallet.dto.request.PaymentServiceRequest;
import com.example.TrustWallet.dto.response.InitializeTransactionResponse;
import com.example.TrustWallet.dto.response.MonifyInitializeTransactionResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
@AllArgsConstructor
public class MonifyPaymentService implements PaymentService{
    private BaseConfig baseConfig;
    private HttpRequest httpRequest;
    @Override
    public InitializeTransactionResponse InitializeTransaction(PaymentServiceRequest<?> body) {
        MonifyInitializeTransactionResponse request = new MonifyInitializeTransactionResponse();
        setContractCode(body);
        String url = baseConfig.getMonnifyBaseUrl()+"/transactions/init-transaction";
        String apiKey = baseConfig.getMonnifyApiKey() +":"+baseConfig.getMonifySecretKey();
        String key ="Basic "+Utility.convertToBase64(apiKey);
        try {
             request = (MonifyInitializeTransactionResponse)httpRequest.sendHttpRequest(body.getT(),url,key,request);
             return new InitializeTransactionResponse(request.getResponseBody().getCheckoutUrl(),null);
        }catch (BadRequestException exception){
            return new InitializeTransactionResponse(request.getResponseBody().getCheckoutUrl(),request.getResponseMessage());
        }
    }

    private void setContractCode(PaymentServiceRequest<?> body){
        try {
          Field field = body.getT().getClass().getDeclaredField("contractCode");
          field.setAccessible(true);
          field.set(body.getT(),baseConfig.getContractCode());

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
