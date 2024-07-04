package com.example.TrustWallet.services;

import com.example.TrustWallet.data.model.User;
import com.example.TrustWallet.dto.request.CreateUserRequest;

public interface UserService {
    User createUser(CreateUserRequest createUserRequest);

    User getUserPhoneNumber(String number);
}
