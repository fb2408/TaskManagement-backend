package com.example.mobileappserver.controller;

import com.example.mobileappserver.MobileAppServerApplication;
import com.example.mobileappserver.model.User;
import com.example.mobileappserver.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.junit.Assert.assertEquals;


@SpringBootTest(classes = MobileAppServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerIntTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;

    @Test
    void loginSuccess() {
        final User testUser = new User("Mate Ivić", "12345", "mateivic@gmail.com",
                true, "124fbgrg", "M", true, 5, 3, false);
        userRepository.save(testUser);

        final String testEmail = "mateivic@gmail.com";
        assertEquals(
                this.restTemplate
                        .getForObject("http://localhost:" + port + "/login?email=" + testEmail, User.class)
                        .getEmail(),
                        testEmail);

        userRepository.delete(testUser);
    }

    @Test
    void loginFailed() {
        final String testEmail = "nepostojeci@gmail.com";
        assertEquals(
                this.restTemplate
                        .getForObject("http://localhost:" + port + "/login?email=" + testEmail, User.class),
                null);
    }

    @Test
    void loginInactiveUser() {
        final User testUser = new User("Mate Ivić", "12345", "mateivic@gmail.com",
                true, "124fbgrg", "M", false, 5, 3, false);
        userRepository.save(testUser);

        final String testEmail = "mateivic@gmail.com";
        assertEquals(
                this.restTemplate
                        .getForObject("http://localhost:" + port + "/login?email=" + testEmail, User.class),
                null);

        userRepository.delete(testUser);
    }

}