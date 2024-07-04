package com.example.TrustWallet.config;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Getter
public class BaseConfig {
    @Value("${PAYSTACK_SECRET_KEY}")
    private String paystackSecretKey;
    @Value("${PAYSTACK_BASE_URL}")
    private String paystackBaseUrl;

    @Value("${CONTRACT_CODE}")
    private String contractCode;
    @Value("${MONNIFY_SECRET_KEY}")
    private String monifySecretKey;
    @Value("${MONNIFY_BASE_URL}")
    private String monnifyBaseUrl;
    @Value("${MONNIFY_API_KEY}")
    private String monnifyApiKey;

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
