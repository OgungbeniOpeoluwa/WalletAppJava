package com.example.TrustWallet.Util;

import com.example.TrustWallet.Exception.BadRequestException;
import com.example.TrustWallet.dto.request.MonifyInitializeRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class HttpRequest {
    public <T> Object sendHttpRequest(Object body, String url, String key, T response){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.set("Authorization",key);
        HttpEntity<?> entity = new HttpEntity<>(body,header);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity <?> request = restTemplate.postForEntity(url,entity,response.getClass());
        System.out.println(request.getBody());
        if(!request.getStatusCode().is2xxSuccessful()){
            throw new BadRequestException(request.getStatusCode().toString());
        }
        return request.getBody();

    }
}
