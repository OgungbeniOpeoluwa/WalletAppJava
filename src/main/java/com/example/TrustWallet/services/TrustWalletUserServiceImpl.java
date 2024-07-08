package com.example.TrustWallet.services;

import com.example.TrustWallet.data.model.User;
import com.example.TrustWallet.data.repository.UserRepository;
import com.example.TrustWallet.dto.request.CreateUserRequest;
import com.example.TrustWallet.Exception.UserExistException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TrustWalletUserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper mapper;

    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        Optional<User> user = userRepository.findByEmail(createUserRequest.getEmail());
        user.ifPresent((x)->{throw new UserExistException("user exist");});
        if (getUserByPhoneNumber(createUserRequest.getPhoneNumber()))throw new UserExistException("User exist");
        User user1 = mapper.map(createUserRequest,User.class);
        userRepository.save(user1);
        return user1;
    }

    @Override
    public User getUserPhoneNumber(String number) {
        return userRepository.findByPhonenumber(number)
                .orElseThrow(()->new UserExistException("user doesn't exist"));
    }

    public Boolean getUserByPhoneNumber(String number){
        Optional<User> user = userRepository.findByPhonenumber(number);
        return user.isPresent();
    }
}
