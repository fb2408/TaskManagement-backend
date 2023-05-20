package com.example.mobileappserver.repository;

import com.example.mobileappserver.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserTaskRepositoryTest {

    @Autowired
    private UserTaskRepository userTaskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private OrganizationUnitRepository organizationUnitRepository;

    @Test
    void CheckIfDeletesByUserIdAndTaskIdExistingUserTask() {
        User testUser = new User("Mate Ivic", "1234", "mate@gmail.com",
                true, "123456", "M", false, 5, 3, false);
        userRepository.save(testUser);

        Tasktype testTaskType = new Tasktype("Nabava pića");
        taskTypeRepository.save(testTaskType);

        OrganizationUnit testOrganizationUnit = new OrganizationUnit("Aria bar",
                LocalTime.of(9,0), LocalTime.of(0,0),
                null);
        organizationUnitRepository.save(testOrganizationUnit);

        Task testTask = new Task("Nabaviti Coca Colu", 2, testTaskType);
        taskRepository.save(testTask);

        Usertask testUserTask = new Usertask(false, false, testUser, null, testTask);


        userTaskRepository.save(testUserTask);
        userTaskRepository.deleteByUserIdAndTaskId(testUser.getId(), testTask.getId());

        assertEquals(userTaskRepository.existsById(testUserTask.getId()), false);

    }
}