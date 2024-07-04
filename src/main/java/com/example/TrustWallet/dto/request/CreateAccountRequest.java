package com.example.TrustWallet.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateAccountRequest {
    private String firstName;
    private String lastname;
    private String email;
    private String phoneNumber;
}
