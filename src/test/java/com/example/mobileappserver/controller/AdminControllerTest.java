package com.example.mobileappserver.controller;

import com.example.mobileappserver.MobileAppServerApplication;
import com.example.mobileappserver.model.*;
import com.example.mobileappserver.repository.*;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MobileAppServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private OrganizationUnitRepository organizationUnitRepository;
    @Autowired
    private UserTaskRepository userTaskRepository;

    //OBJECTS FOR TESTING
    private final User testUser = new User("Mate Ivic", "1234", "mate@gmail.com",
            true, "123456", "M", false, 5, 3, false);
    private final Tasktype testTaskType = new Tasktype("Svakodnevni poslovi");
    private final OrganizationUnit testOrganizationUnit = new OrganizationUnit("Aria bar",
            LocalTime.of(9,0), LocalTime.of(0,0),
            null);
    private final Task testTask1 = new Task("Nabaviti Coca Colu", 2, testTaskType);
    private final Task testTask2 = new Task("Baciti smeće", 3, testTaskType);
    private final Usertask testUserTask1 = new Usertask(false, false, testUser, testOrganizationUnit, testTask1);
    private final Usertask testUserTask2 = new Usertask(false, false, testUser, testOrganizationUnit, testTask2);

    @BeforeEach
    void setUp() {
        userRepository.save(testUser);
        taskTypeRepository.save(testTaskType);
        organizationUnitRepository.save(testOrganizationUnit);
        taskRepository.save(testTask1);
        taskRepository.save(testTask2);
        userTaskRepository.save(testUserTask1);
        userTaskRepository.save(testUserTask2);
    }

    @AfterEach
    void tearDown() {
        userTaskRepository.deleteAll();
        userTaskRepository.deleteAll();
        taskRepository.deleteAll();
        taskRepository.deleteAll();
        organizationUnitRepository.deleteAll();
        taskTypeRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void addTask() {
        final Task reqBodyTask = new Task("Donijeti stolove", 2, testTaskType);
        ResponseEntity<Task> response = this.restTemplate
                .postForEntity("http://localhost:" + port + "/admin/add-task", reqBodyTask, Task.class);
        Assert.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    void updateTask() {
        Task updatedTask = new Task();
        updatedTask.setId(testTask1.getId());
        updatedTask.setTaskType(testTask1.getTaskType());
        updatedTask.setName("Novo ime");
        updatedTask.setLevel(testTask1.getLevel());

        this.restTemplate
                .postForObject("http://localhost:" + port + "/admin/update-task", updatedTask, void.class);
        Assert.assertEquals("Novo ime", taskRepository.findById(updatedTask.getId()).get().getName());
    }

    @Test
    void deleteTask() {
    }

    @Test
    void addWorker() {
    }

    @Test
    void updateWorker() {
    }

    @Test
    void deleteWorker() {
    }

    @Test
    void addOrganizationUnit() {
    }

    @Test
    void updateOrganizationUnit() {
    }

    @Test
    void deleteOrganizationUnit() {
    }

    @Test
    void addUserTask() {
    }

    @Test
    void deleteUserTask() {
    }

    @Test
    void newTaskNotification() {
    }

    @Test
    void listWorkers() {
    }

    @Test
    void listTaskTypes() {
    }

    @Test
    void overviewOnUserTasks() {
    }
}