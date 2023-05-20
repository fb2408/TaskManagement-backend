package com.example.mobileappserver.controller;

import com.example.mobileappserver.model.User;
import com.example.mobileappserver.repository.UserRepository;
import com.example.mobileappserver.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class LoginControllerTest {
    private final UserRepository userRepository;

    LoginControllerTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Test
//    void login() {
//        LoginController loginController = new
//        loginController.login("nepostojecimail@gmail.com");
//        Assert.assertEquals(user, null);
//
//    }
}