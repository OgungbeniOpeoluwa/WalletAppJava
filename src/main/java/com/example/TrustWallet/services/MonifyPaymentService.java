package com.example.TrustWallet.services;

import com.example.TrustWallet.Exception.BadRequestException;
import com.example.TrustWallet.Util.Common;
import com.example.TrustWallet.Util.HttpRequest;
import com.example.TrustWallet.Util.Utility;
import com.example.TrustWallet.config.BaseConfig;
import com.example.TrustWallet.dto.request.PaymentServiceRequest;
import com.example.TrustWallet.dto.response.InitializeTransactionResponse;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
@AllArgsConstructor
public class MonifyPaymentService implements PaymentService{
    private BaseConfig baseConfig;
    private HttpRequest httpRequest;
    private Common common;
    @Override
    public InitializeTransactionResponse InitializeTransaction(PaymentServiceRequest<?> body) {
        JsonNode request = null;
        setContractCode(body);
        String url = baseConfig.getMonnifyBaseUrl()+"/transactions/init-transaction";
        String apiKey = baseConfig.getMonnifyApiKey() +":"+baseConfig.getMonifySecretKey();
        String key ="Basic "+Utility.convertToBase64(apiKey);
        try {
             request =httpRequest.sendHttpRequest(body.getT(),url,key);
             JsonNode data = request.get(common.responseBody);
            System.out.println(data);
             assert  data != null;
             return new InitializeTransactionResponse(data.get(common.checkoutUrl).asText(),
                     null,data.get(common.paymentReference).asText());
        }catch (BadRequestException exception){
            assert request != null;
            return new InitializeTransactionResponse(null,
                    request.get(common.responseMessage).asText(),null);
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
