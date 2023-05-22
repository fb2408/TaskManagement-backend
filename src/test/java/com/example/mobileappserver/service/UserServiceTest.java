package com.example.mobileappserver.service;

import com.example.mobileappserver.model.User;
import com.example.mobileappserver.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository);
    }

    @Test
    void deleteWorker() {
        final int id = 10;
        underTest.deleteWorker(id);
        verify(userRepository).deleteById(id);
    }

    @Test
    void addWorker() {
        final User testUser = new User("Mate Ivic", "1234", "mate@gmail.com",
                true, "123456", "M", false, 5, 3, false);
        underTest.addWorker(testUser);
        verify(userRepository).save(testUser);
    }

    @Test
    void updateWorkerThrowsException() {
        final User testUser = new User("Mate Ivic", "1234", "mate@gmail.com",
                true, "123456", "M", false, 5, 3, false);

        Exception exception = assertThrows(Exception.class, () -> {
            underTest.updateWorker(testUser);
        });
        assertEquals("User doesn't exist", exception.getMessage());
    }

    @Test
    void updateWorkerSuccess() throws Exception {
        final User testUser = new User("Mate Ivic", "1234", "mate@gmail.com",
                true, "123456", "M", false, 5, 3, false);

        when(userRepository.findById(null)).thenReturn(Optional.of(testUser));
        underTest.updateWorker(testUser);
        verify(userRepository).save(testUser);
    }

    @Test
    void loginThrowsException() {
        final String email = "mate@gmail.com";

        Exception exception = assertThrows(Exception.class, () -> {
            underTest.login(email);
        });
        assertEquals("Wrong password!", exception.getMessage());
    }

    @Test
    void loginSuccess() throws Exception {
        final String email = "mate@gmail.com";
        final User testUser = new User("Mate Ivic", "1234", "mate@gmail.com",
                true, "123456", "M", false, 5, 3, false);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));
        User actualUser = underTest.login(email);
        Assert.assertEquals(testUser, actualUser);
    }

    @Test
    void changeDeviceTokenThrowsException() {
        final String id = "15";
        final String deviceToken = "12345iisdbc";

        Exception exception = assertThrows(Exception.class, () -> {
            underTest.changeDeviceToken(id, deviceToken);
        });
        assertEquals("Could not find user by id", exception.getMessage());
    }

    @Test
    void changeDeviceTokenSuccess() throws Exception {
        final String id = "15";
        final String deviceToken = "12345iisdbc";

        final User testUser = new User("Mate Ivic", "1234", "mate@gmail.com",
                true, "123456", "M", false, 5, 3, false);

        when(userRepository.findById(Integer.parseInt(id))).thenReturn(Optional.of(testUser));
        underTest.changeDeviceToken(id, deviceToken);
        verify(userRepository).save(testUser);
    }

    @Test
    void findThrowsException() {
        final String id = "15";

        Exception exception = assertThrows(Exception.class, () -> {
            underTest.find(id);
        });
        assertEquals("Could not find user", exception.getMessage());
    }

    @Test
    void findSuccess() throws Exception {
        final String id = "15";

        final User testUser = new User("Mate Ivic", "1234", "mate@gmail.com",
                true, "123456", "M", false, 5, 3, false);

        when(userRepository.findById(Integer.parseInt(id))).thenReturn(Optional.of(testUser));
        User actualUser = underTest.find(id);
        Assert.assertEquals(testUser, actualUser);
    }

    @Test
    void findAllWorkers() {
        underTest.findAllWorkers();
        verify(userRepository).findByIsAdmin(false);
    }
}
