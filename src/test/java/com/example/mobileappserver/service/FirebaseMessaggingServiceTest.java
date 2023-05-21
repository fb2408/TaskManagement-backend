package com.example.mobileappserver.service;

import com.example.mobileappserver.model.Note;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class FirebaseMessaggingServiceTest {

    @Mock
    private FirebaseMessaging firebaseMessaging;
    private FirebaseMessaggingService underTest;

    @BeforeEach
    void setUp() {
        underTest = new FirebaseMessaggingService(firebaseMessaging);
    }

    @Test
    void sendNotification() throws FirebaseMessagingException {
        final Note note = new Note("Naslov", "Ovo je mjesto za poruku", new HashMap<>(), null);

        underTest.sendNotification(note);
        Mockito.verify(firebaseMessaging).send(any());
    }

    @Test
    void sendNotificationToOne() throws FirebaseMessagingException {
        final String deviceToken = "12345dsinfois";
        final Note note = new Note("Naslov", "Ovo je mjesto za poruku", new HashMap<>(), null);

        underTest.sendNotificationToOne(note, deviceToken);
        Mockito.verify(firebaseMessaging).send(any());
    }
}