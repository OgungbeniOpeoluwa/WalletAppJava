package com.example.TrustWallet.services;

import com.example.TrustWallet.Util.Mapper;
import com.example.TrustWallet.data.model.PaymentStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

@EnableAsync
@AllArgsConstructor
@Service
public class WebhookService {
    private WalletService walletService;
    @Async
    public void webhook(String request) throws JsonProcessingException {
        JsonNode value = Mapper.parse(request);
        Iterator<Map.Entry<String,JsonNode>> events = value.fields();
        while (events.hasNext()){
            Map.Entry<String, JsonNode> data = events.next();
            String field = data.getKey();
            String values = data.getValue().asText();
            if(field.equals("eventType")) {
                handleEventMonnify(value, values);
                break;
            }
            else if (field.equals("event")) {
                handleEvent(value, values);
                break;
            }

        }
    }

    private void handleEventMonnify(JsonNode value, String values) {
        JsonNode eventData = value.get("eventData");
        if (values.equals("SUCCESSFUL_TRANSACTION")) {
            walletService.updateTransactions(eventData.get("paymentReference").asText(),PaymentStatus.SUCCESSFUL);
            return;
        }
        walletService.updateTransactions(eventData.get("paymentReference").asText(),PaymentStatus.FAILED);
    }

    private void handleEvent(JsonNode value, String values) {
        JsonNode datas = value.get("data");
        if(datas != null) {
            if (values.equals("charge.success")) {
                walletService.updateTransactions(datas.get("reference").asText(),PaymentStatus.SUCCESSFUL);
                return;
            }
            walletService.updateTransactions(datas.get("reference").asText(), PaymentStatus.FAILED);
        }
    }

}
