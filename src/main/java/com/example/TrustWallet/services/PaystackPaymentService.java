package com.example.TrustWallet.services;

import com.example.TrustWallet.Exception.BadRequestException;
import com.example.TrustWallet.Util.Common;
import com.example.TrustWallet.Util.HttpRequest;
import com.example.TrustWallet.config.BaseConfig;
import com.example.TrustWallet.dto.request.PaymentServiceRequest;
import com.example.TrustWallet.dto.response.InitializeTransactionResponse;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaystackPaymentService implements PaymentService{
    private BaseConfig baseConfig;
    private HttpRequest request;
    private Common common;
    @Override
    public InitializeTransactionResponse InitializeTransaction(PaymentServiceRequest<?> body) {
        JsonNode requests = null;
        JsonNode data;
        String key = "Bearer "+ baseConfig.getPaystackSecretKey();
        String url = baseConfig.getPaystackBaseUrl()+"/transaction/initialize";
        try {
          requests = request.sendHttpRequest(body.getT(),url,key);
          data = requests.get("data");
          if(data != null){
              return new InitializeTransactionResponse(data.get(common.authorizationUrl).asText(),
                      null, data.get(common.reference).asText());
          }
        }catch (BadRequestException exception){
            assert requests != null;
            return new InitializeTransactionResponse(null,requests.get("message").asText(),null);
        }
        return null;
    }
}
