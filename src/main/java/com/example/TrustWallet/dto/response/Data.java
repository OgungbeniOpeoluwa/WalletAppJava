package com.example.TrustWallet.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Data {
    private String authorization_url;
    private String  access_code;
    private  String  reference;
}
