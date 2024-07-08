package com.example.TrustWallet.controller;

import com.example.TrustWallet.Exception.TrustWalletException;
import com.example.TrustWallet.dto.request.CreateAccountRequest;
import com.example.TrustWallet.dto.request.CreatePaymentInitializeRequest;
import com.example.TrustWallet.dto.request.InitializeTransaction;
import com.example.TrustWallet.dto.response.ApiResponse;
import com.example.TrustWallet.dto.response.CreateAccountResponse;
import com.example.TrustWallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest accountRequest){
        try{
            return new ResponseEntity<>(new ApiResponse(true,walletService.createAccount(accountRequest)), HttpStatus.CREATED);
        }catch (TrustWalletException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/initialize-transaction")
    public ResponseEntity<?> initializeTransaction(@RequestBody CreatePaymentInitializeRequest request){
        System.out.println(request.getPaymentMedium());
        try{
            return new ResponseEntity<>(new ApiResponse(true,walletService.initializeTransaction(request)), HttpStatus.CREATED);
        }catch (TrustWalletException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @GetMapping("/transactions/{account}")
    public  ResponseEntity<?> getAllTransactions(@PathVariable String account){
        try{
            return new ResponseEntity<>(new ApiResponse(true,walletService.GetTransactions(account)),HttpStatus.OK);
        }catch (TrustWalletException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/balance/{account}")
    public ResponseEntity<?> getBalance(@PathVariable String account){
        try {
            return new ResponseEntity<>(new ApiResponse(true,walletService.getBalance(account)),HttpStatus.OK);
        }catch (TrustWalletException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
