package com.example.mobileappserver.controller;

import com.example.mobileappserver.MobileAppServerApplication;
import com.example.mobileappserver.model.*;
import com.example.mobileappserver.repository.*;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MobileAppServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WorkerControllerIntTest {

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
        userTaskRepository.deleteById(testUserTask1.getId());
        userTaskRepository.deleteById(testUserTask2.getId());
        taskRepository.deleteById(testTask1.getId());
        taskRepository.deleteById(testTask2.getId());
        organizationUnitRepository.deleteById(testOrganizationUnit.getId());
        taskTypeRepository.deleteById(testTaskType.getId());
        userRepository.deleteById(testUser.getId());
    }

    @Test

    void requestForRedirectTask() {
        this.restTemplate
                .getForObject("http://localhost:" + port + "/worker/redirectRequest?task_id="
                        + testUserTask1.getId(), void.class);

        Usertask usertask = userTaskRepository.findById(testUserTask1.getId()).get();
        Assert.assertEquals(true, usertask.getReqRedirect());
    }

    @Test
    void getTasksForWorker() {
        List<Usertask> response = this.restTemplate
                .getForObject("http://localhost:" + port + "/worker/getTasksForWorker?id=" + testUser.getId(), List.class);
        assertEquals(2, response.size());
    }

    @Test
    void changeTaskToFinished() {
        Usertask response = this.restTemplate
                .getForObject("http://localhost:" + port + "/worker/finishingTask?id=" + testUserTask1.getId(), Usertask.class);
        assertEquals(true, response.getFinished());
    }

    @Test
    void sendNotification() {
        String response = this.restTemplate
                .getForObject("http://localhost:" + port + "/worker/task-finished-notification?id=" + testUserTask1.getId(),
                        String.class);
        Assert.assertTrue(!response.isEmpty());
    }

    @Test
    void changeDeviceToken() {
        final String deviceToken = "xcnvdsinvr4";
        this.restTemplate
                .getForEntity("http://localhost:" + port + "/worker/change-device-token?id="
                        + testUser.getId() + "&deviceToken=" + deviceToken, void.class);
        Assert.assertEquals(deviceToken, userRepository.findById(testUser.getId()).get().getDeviceToken());

    }
}
