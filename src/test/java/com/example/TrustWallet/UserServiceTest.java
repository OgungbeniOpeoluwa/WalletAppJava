package com.example.TrustWallet;

import com.example.TrustWallet.data.model.User;
import com.example.TrustWallet.dto.request.CreateUserRequest;
import com.example.TrustWallet.Exception.UserExistException;
import com.example.TrustWallet.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser(){
        CreateUserRequest createUserRequest  = new CreateUserRequest();
        createUserRequest.setEmail("shola@gmail.com");
        createUserRequest.setLastName("shola");
        createUserRequest.setFirstName("titi");
        createUserRequest.setPhoneNumber("07066221008");
        User user = userService.createUser(createUserRequest);
        assertThat(user).isNotNull();

    }
    @Test
    @Sql(scripts = {"/scripts/insert.sql"})
    public void testThatUserWithTheSamePhoneNumberCantRegister(){
        CreateUserRequest createUserRequest  = new CreateUserRequest();
        createUserRequest.setEmail("shola2@gmail.com");
        createUserRequest.setLastName("shola");
        createUserRequest.setFirstName("titi");
        createUserRequest.setPhoneNumber("07066221008");
        assertThrows(UserExistException.class,()->userService.createUser(createUserRequest));
    }
    @Test
    @Sql(scripts = {"/scripts/insert.sql"})
    public void testGetUserByPhoneNumber(){
        User user = userService.getUserPhoneNumber("07066221008");
        assertThat(user).isNotNull();
    }
}
