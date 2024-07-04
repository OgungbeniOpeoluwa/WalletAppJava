package com.example.TrustWallet.controller;

import com.example.TrustWallet.dto.request.CreateAccountRequest;
import com.example.TrustWallet.dto.request.CreateTransactionRequest;
import com.example.TrustWallet.dto.request.InitializeTransaction;
import com.example.TrustWallet.dto.response.CreateAccountResponse;
import com.example.TrustWallet.dto.response.InitializeTransactionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;


    @Test

    public void testCreateAccount() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setLastname("opeoluwa");
        request.setFirstName("shola");
        request.setPhoneNumber("07898883774");
        request.setEmail("ope@gmail.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet/create")
                .content(mapper.writeValueAsBytes(request)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).
                andExpect(status().is2xxSuccessful());

    }

    @Test
    public void testInitializeTransaction()throws Exception{
//        CreateAccountRequest request = new CreateAccountRequest();
//        request.setLastname("opeoluwa");
//        request.setFirstName("shola");
//        request.setPhoneNumber("07898883774");
//        request.setEmail("ope@gmail.com");
        InitializeTransaction req = new InitializeTransaction();
        req.setAmount(BigDecimal.valueOf(1000));
        req.setDescription("my hair");
        req.setAccountNumber("07898883774");
        req.setPaymentMedium("PAYSTACK");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet/initialize-transaction")
                        .content(mapper.writeValueAsBytes(req))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).
                andExpect(status().is2xxSuccessful());
    }
}
