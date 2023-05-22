package com.example.mobileappserver.controller;

import com.example.mobileappserver.MobileAppServerApplication;
import com.example.mobileappserver.model.*;
import com.example.mobileappserver.repository.*;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.List;

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

        taskRepository.deleteById(updatedTask.getId());
        taskTypeRepository.deleteById(testTaskType.getId());
    }

    @Test
    void deleteTask() {
        taskTypeRepository.save(testTaskType);
        taskRepository.save(testTask1);

        this.restTemplate
                .getForObject("http://localhost:" + port + "/admin/deleteTask?id=" + testTask1.getId(), void.class);
        Assert.assertEquals(false, taskRepository.findById(testTask1.getId()).isPresent());

        taskTypeRepository.deleteById(testTaskType.getId());
    }


    @Test
    void deleteWorker() {
        userRepository.save(testUser);

        this.restTemplate
                .getForObject("http://localhost:" + port + "/admin/deleteUser?id=" + testUser.getId(), void.class);
        Assert.assertEquals(false, userRepository.findById(testUser.getId()).isPresent());
    }


    @Test
    void deleteOrganizationUnit() {
        organizationUnitRepository.save(testOrganizationUnit);

        this.restTemplate
                .getForObject("http://localhost:" + port +
                        "/admin/deleteOrganizationUnit?id=" + testOrganizationUnit.getId(), void.class);
        Assert.assertEquals(false, organizationUnitRepository
                .findById(testOrganizationUnit.getId()).isPresent());
    }

    @Test
    void deleteUserTask() {
        organizationUnitRepository.save(testOrganizationUnit);
        userRepository.save(testUser);
        taskTypeRepository.save(testTaskType);
        taskRepository.save(testTask1);
        userTaskRepository.save(testUserTask1);

        this.restTemplate
                .getForObject("http://localhost:" + port +
                        "/admin/delete-user-task?id=" + testUserTask1.getId(), void.class);
        Assert.assertEquals(false, userTaskRepository
                .findById(testUserTask1.getId()).isPresent());

        taskRepository.deleteById(testTask1.getId());
        taskTypeRepository.deleteById(testTaskType.getId());
        userRepository.deleteById(testUser.getId());
        organizationUnitRepository.deleteById(testOrganizationUnit.getId());
    }

    @Test
    void newTaskNotification() {
        userRepository.save(testUser);
        taskTypeRepository.save(testTaskType);
        taskRepository.save(testTask1);

        String response = this.restTemplate
                .getForObject("http://localhost:" + port +
                        "/admin/new-task-notification?id=" + testUserTask1.getId()
                        + "&taskId=" + testTask1.getId(), String.class);
        Assert.assertTrue(!response.isEmpty());

        taskRepository.deleteById(testTask1.getId());
        taskTypeRepository.deleteById(testTaskType.getId());
        userRepository.deleteById(testUser.getId());

    }

    @Test
    void listWorkers() {
        userRepository.save(testUser);

        List<User> response = this.restTemplate
                .getForObject("http://localhost:" + port +
                        "/admin/list-all-workers", List.class);
        Assert.assertTrue(response.size() >= 1);

        userRepository.deleteById(testUser.getId());
    }

    @Test
    void listTaskTypes() {
        taskTypeRepository.save(testTaskType);

        List<Tasktype> response = this.restTemplate
                .getForObject("http://localhost:" + port +
                        "/admin/list-all-task-types", List.class);
        Assert.assertTrue(response.size() >= 1);

        taskTypeRepository.deleteById(testTaskType.getId());
    }

    @Test
    void overviewOnUserTasks() {
        organizationUnitRepository.save(testOrganizationUnit);
        userRepository.save(testUser);
        taskTypeRepository.save(testTaskType);
        taskRepository.save(testTask1);
        userTaskRepository.save(testUserTask1);

        List<Usertask> response = this.restTemplate
                .getForObject("http://localhost:" + port +
                        "/admin/overview-user-tasks", List.class);
        Assert.assertTrue(response.size() >= 1);

        userTaskRepository.deleteById((testUserTask1.getId()));
        taskRepository.deleteById(testTask1.getId());
        taskTypeRepository.deleteById(testTaskType.getId());
        userRepository.deleteById(testUser.getId());
        organizationUnitRepository.deleteById(testOrganizationUnit.getId());
    }
}