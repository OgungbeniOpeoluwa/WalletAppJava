package com.example.TrustWallet.controller;

import com.example.TrustWallet.config.BaseConfig;
import com.example.TrustWallet.data.model.PaymentMedium;
import com.example.TrustWallet.services.TransactionHashUtil;
import com.example.TrustWallet.services.WalletService;
import com.example.TrustWallet.services.WebhookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api/v1")
public class Webhook {
    @Autowired
    private WebhookService webhookRequest;
    @Autowired
    private BaseConfig baseConfig;

    @PostMapping("/webhook")
    public ResponseEntity<?> paystackWebhook(HttpServletRequest request) {
        try {
            String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Enumeration<String> data = request.getHeaderNames();
            while (data.hasMoreElements()) {
                String value = data.nextElement();
                if (value.equals("x-paystack-signature")) {
                    return paystackCheck(requestBody,request);
                } else if (value.equals("monnify-signature")) {
                    return monnifyCheck(request, requestBody);
                }
            }

        }catch (Exception e) {
            return new ResponseEntity<>("failed to perform", HttpStatus.BAD_REQUEST);
        }
        return null;

    }

    private ResponseEntity<Object> monnifyCheck(HttpServletRequest request, String requestBody) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        String hash = TransactionHashUtil.calculateHMAC512TransactionHash(requestBody, baseConfig.getMonifySecretKey());
        if (hash.equals(request.getHeader("monnify-signature"))) {
            webhookRequest.webhook(requestBody);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> paystackCheck(String requestBody,HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        String hash = TransactionHashUtil.calculateHMAC512TransactionHash(requestBody, baseConfig.getPaystackSecretKey());
        if (hash.equals(request.getHeader("x-paystack-signature"))) {
            webhookRequest.webhook(requestBody);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
