package com.example.TrustWallet.controller;

import com.example.TrustWallet.dto.request.CreateAccountRequest;
import com.example.TrustWallet.dto.request.CreatePaymentInitializeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.jdbc.Sql;
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
        request.setLastName("opeoluwa");
        request.setFirstName("shola");
        request.setPhoneNumber("07898883774");
        request.setEmail("ope@gmail.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet/create")
                .content(mapper.writeValueAsBytes(request)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).
                andExpect(status().is2xxSuccessful());

    }

    @Test
    @Sql(scripts = {"/scripts/insert.sql"})
    public void testInitializeTransaction()throws Exception{
        CreatePaymentInitializeRequest req = new CreatePaymentInitializeRequest();
        req.setAmount(1000);
        req.setDescription("my hair");
        req.setAccountNumber("07066221008");
        req.setPaymentMedium("PAYSTACK");
        req.setCurrency("NGN");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet/initialize-transaction")
                        .content(mapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).
                andExpect(status().is2xxSuccessful());
    }

    @Test
    @Sql(scripts = {"/scripts/insert.sql"})
    public void testGetBalance() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wallet/balance/07066221008")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}
