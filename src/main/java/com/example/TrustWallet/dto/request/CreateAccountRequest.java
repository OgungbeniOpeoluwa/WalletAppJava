package com.example.TrustWallet.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateAccountRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
}
