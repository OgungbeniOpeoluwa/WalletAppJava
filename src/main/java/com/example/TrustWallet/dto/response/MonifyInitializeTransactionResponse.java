package com.example.TrustWallet.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MonifyInitializeTransactionResponse {
    private  boolean  requestSuccessful;
    private String   responseMessage;
    private String     responseCode;
    private ResponseBody responseBody;

}
