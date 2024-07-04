package com.example.TrustWallet.controller;

import com.example.TrustWallet.Exception.TrustWalletException;
import com.example.TrustWallet.dto.request.CreateAccountRequest;
import com.example.TrustWallet.dto.request.InitializeTransaction;
import com.example.TrustWallet.dto.response.CreateAccountResponse;
import com.example.TrustWallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest accountRequest){
        try{
            return new ResponseEntity<>(walletService.createAccount(accountRequest), HttpStatus.CREATED);
        }catch (TrustWalletException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/initialize-transaction")
    public ResponseEntity<?> initializeTransaction(@RequestBody InitializeTransaction request){
        try{
            return new ResponseEntity<>(walletService.initializeTransaction(request), HttpStatus.CREATED);
        }catch (TrustWalletException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
