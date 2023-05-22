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
    private final User testUser = new User("Ivo Ivic", "1234", "ivo@gmail.com",
            true, "123456", "M", false, 5, 3, false);
    private final Tasktype testTaskType = new Tasktype("Svakodnevni poslovi");
    private final OrganizationUnit testOrganizationUnit = new OrganizationUnit("Aria bar Olympia Sky",
            LocalTime.of(9,0), LocalTime.of(0,0),
            null);
    private final Task testTask1 = new Task("Nabaviti Coca Colu", 2, testTaskType);
    private final Usertask testUserTask1 = new Usertask(false, false, testUser, testOrganizationUnit, testTask1);



    @Test
    void addTask() {
        taskTypeRepository.save(testTaskType);
        ResponseEntity<Task> response = this.restTemplate
                .postForEntity("http://localhost:" + port + "/admin/add-task",
                        testTask1,
                        Task.class);
        Assert.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        Task insertedTask = taskRepository.findByName(testTask1.getName());
        taskRepository.delete(insertedTask);
        taskTypeRepository.deleteById(testTaskType.getId());
    }

    @Test
    void updateTask() {
        taskTypeRepository.save(testTaskType);
        taskRepository.save(testTask1);
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
