package com.example.mobileappserver.service;

import com.example.mobileappserver.model.*;
import com.example.mobileappserver.repository.OrganizationUnitRepository;
import com.example.mobileappserver.repository.UserTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTaskServiceTest {

    @Mock
    private UserTaskRepository userTaskRepository;

    private UserTaskService underTest;

//    @BeforeEach
//    void setUp() {
//        underTest = new UserTaskService(userTaskRepository);
//    }


    @Test
    void taskFinishedThrowsException() {
        final int id = 10;

        Exception exception = assertThrows(Exception.class, () -> {
            underTest.taskFinished(id);
        });
        assertEquals("Task not found, something went wrong :(", exception.getMessage());
    }

    @Test
    void taskAlreadyFinished() {
        final int id = 10;
        final User testUser = new User("Mate Ivic", "1234", "mate@gmail.com",
                true, "123456", "M", false, 5, 3, false);

        final Tasktype testTaskType = new Tasktype("Nabava pića");

        final Task testTask = new Task("Nabaviti Coca Colu", 2, testTaskType);

        final Usertask testUserTask = new Usertask(true, false, testUser, null, testTask);

        when(userTaskRepository.findById(id)).thenReturn(Optional.of(testUserTask));
        Exception exception = assertThrows(Exception.class, () -> {
            underTest.taskFinished(id);
        });
        assertEquals("Task already finished!", exception.getMessage());
    }

    @Test
    void taskFinished() throws Exception {
        final int id = 10;
        final User testUser = new User("Mate Ivic", "1234", "mate@gmail.com",
                true, "123456", "M", false, 5, 3, false);

        final Tasktype testTaskType = new Tasktype("Nabava pića");

        final Task testTask = new Task("Nabaviti Coca Colu", 2, testTaskType);

        final Usertask testUserTask = new Usertask(false, false, testUser, null, testTask);

        when(userTaskRepository.findById(id)).thenReturn(Optional.of(testUserTask));
        underTest.taskFinished(id);
        verify(userTaskRepository).save(testUserTask);
    }

    @Test
    void getTasksForWorker() {
        final int id = 1;
        underTest.getTasksForWorker(id);
        verify(userTaskRepository).findAllByUserId(id);
    }

    @Test
    void requestForRedirectingTaskThrowsException() {
        final int id = 10;

        Exception exception = assertThrows(Exception.class, () -> {
            underTest.requestForRedirectingTask(id);
        });
        assertEquals("Task doesnt exsist", exception.getMessage());
    }

    @Test
    void requestForRedirectingTaskAlreadyRequired() {
        final int id = 10;
        final User testUser = new User("Mate Ivic", "1234", "mate@gmail.com",
                true, "123456", "M", false, 5, 3, false);

        final Tasktype testTaskType = new Tasktype("Nabava pića");

        final Task testTask = new Task("Nabaviti Coca Colu", 2, testTaskType);

        final Usertask testUserTask = new Usertask(false, true, testUser, null, testTask);

        when(userTaskRepository.findById(id)).thenReturn(Optional.of(testUserTask));
        Exception exception = assertThrows(Exception.class, () -> {
            underTest.requestForRedirectingTask(id);
        });
        assertEquals("Task already required for redirecting.", exception.getMessage());
    }

    @Test
    void requestForRedirectingTaskSuccessfulyFinished() throws Exception {
        final int id = 10;
        final User testUser = new User("Mate Ivic", "1234", "mate@gmail.com",
                true, "123456", "M", false, 5, 3, false);

        final Tasktype testTaskType = new Tasktype("Nabava pića");

        final Task testTask = new Task("Nabaviti Coca Colu", 2, testTaskType);

        final Usertask testUserTask = new Usertask(false, false, testUser, null, testTask);

        when(userTaskRepository.findById(id)).thenReturn(Optional.of(testUserTask));
        underTest.requestForRedirectingTask(id);
        verify(userTaskRepository).save(testUserTask);
    }

    @Test
    void addUserTask() {
        final User testUser = new User("Mate Ivic", "1234", "mate@gmail.com",
                true, "123456", "M", false, 5, 3, false);

        final Tasktype testTaskType = new Tasktype("Nabava pića");

        final Task testTask = new Task("Nabaviti Coca Colu", 2, testTaskType);

        final Usertask testUserTask = new Usertask(false, false, testUser, null, testTask);

        underTest.addUserTask(testUserTask);

        verify(userTaskRepository).save(testUserTask);
    }

    @Test
    void find() {
        underTest.find();
        verify(userTaskRepository).findAll();
    }

    @Test
    void deleteUserTask() {
        final int id = 26;
        underTest.deleteUserTask(id);
        verify(userTaskRepository).deleteById(id);
    }

    @Test
    void getUserTaskSuccess() throws Exception {
        final User testUser = new User("Mate Ivic", "1234", "mate@gmail.com",
                true, "123456", "M", false, 5, 3, false);

        final Tasktype testTaskType = new Tasktype("Nabava pića");

        final Task testTask = new Task("Nabaviti Coca Colu", 2, testTaskType);

        final Usertask testUserTask = new Usertask(false, false, testUser, null, testTask);
        testTask.setId(20);

        when(userTaskRepository.findById(1)).thenReturn(Optional.of(testUserTask));

        underTest.getUserTask(1);

        verify(userTaskRepository).findById(1);
    }
}
