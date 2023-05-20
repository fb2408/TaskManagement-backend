package com.example.mobileappserver.service;

import com.example.mobileappserver.repository.TaskTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskTypeServiceTest {

    @Mock
    private TaskTypeRepository taskTypeRepository;
    private TaskTypeService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TaskTypeService(taskTypeRepository);
    }

    @Test
    void getAllTaskTypes() {
        underTest.getAllTaskTypes();
        Mockito.verify(taskTypeRepository).findAll();
    }
}