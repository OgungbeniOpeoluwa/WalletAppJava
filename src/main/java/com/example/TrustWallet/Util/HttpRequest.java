package com.example.TrustWallet.Util;

import com.example.TrustWallet.Exception.BadRequestException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class HttpRequest {
    public JsonNode sendHttpRequest(Object body, String url, String key){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.set("Authorization",key);
        HttpEntity<?> entity = new HttpEntity<>(body,header);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity <JsonNode> request = restTemplate.postForEntity(url,entity, JsonNode.class);
        System.out.println(request.getBody());
        if(!request.getStatusCode().is2xxSuccessful()){
            throw new BadRequestException(request.getStatusCode().toString());
        }
        return request.getBody();

    }
}
