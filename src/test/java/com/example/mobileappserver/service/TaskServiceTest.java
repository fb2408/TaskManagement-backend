
        package com.example.mobileappserver.service;

        import com.example.mobileappserver.model.Task;
        import com.example.mobileappserver.model.Tasktype;
        import com.example.mobileappserver.model.User;
        import com.example.mobileappserver.model.Usertask;
        import com.example.mobileappserver.repository.TaskRepository;
        import com.example.mobileappserver.repository.UserTaskRepository;
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
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserTaskRepository userTaskRepository;
    private TaskService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TaskService(userTaskRepository, taskRepository);
    }

    @Test
    void taskFinishedThrowsException() {
        Integer taskId = 16;

        Exception exception = assertThrows(Exception.class, () -> {
            underTest.taskFinished(taskId);
        });
        assertEquals("Task doesn't exist", exception.getMessage());
    }

    @Test
    void taskAlreadySolved() {
        Integer taskId = 16;
        final User testUser = new User("Mate Ivic", "1234", "mate@gmail.com",
                true, "123456", "M", false, 5, 3, false);

        final Tasktype testTaskType = new Tasktype("Nabava pića");

        final Task testTask = new Task("Nabaviti Coca Colu", 2, testTaskType);

        final Usertask testUserTask = new Usertask(true, false, testUser, null, testTask);

        when(userTaskRepository.findById(taskId)).thenReturn(Optional.of(testUserTask));
        Exception exception = assertThrows(Exception.class, () -> {
            underTest.taskFinished(taskId);
        });
        assertEquals("Task already solved!!!", exception.getMessage());
    }

    @Test
    void taskFinishedSuccessful() throws Exception {
        Integer taskId = 16;
        final User testUser = new User("Mate Ivic", "1234", "mate@gmail.com",
                true, "123456", "M", false, 5, 3, false);

        final Tasktype testTaskType = new Tasktype("Nabava pića");

        final Task testTask = new Task("Nabaviti Coca Colu", 2, testTaskType);

        final Usertask testUserTask = new Usertask(false, false, testUser, null, testTask);

        when(userTaskRepository.findById(taskId)).thenReturn(Optional.of(testUserTask));
        underTest.taskFinished(taskId);
        verify(userTaskRepository).save(testUserTask);
    }

    @Test
    void addingTaskWhichAlreadyExists() {
        final Tasktype testTaskType = new Tasktype("Nabava pića");

        final Task testTask = new Task("Nabaviti Coca Colu", 2, testTaskType);

        when(taskRepository.findByName(testTask.getName())).thenReturn(testTask);
        Exception exception = assertThrows(Exception.class, () -> {
            underTest.addTask(testTask);
        });
        assertEquals("Task with that name already exsist!", exception.getMessage());
    }

    @Test
    void addingNewTaskSuccessfuly() throws Exception {
        final Tasktype testTaskType = new Tasktype("Nabava pića");

        final Task testTask = new Task("Nabaviti Coca Colu", 2, testTaskType);

        when(taskRepository.findByName(testTask.getName())).thenReturn(null);
        underTest.addTask(testTask);
        verify(taskRepository).save(testTask);
    }

    @Test
    void deleteTask() {
        final int id = 12;
        underTest.deleteTask(id);
        verify(taskRepository).deleteById(id);
    }

    @Test
    void updateTaskThrowsException() {
        Tasktype testTaskType = new Tasktype("Nabava pića");
        Task testTask = new Task("Nabaviti Coca Colu", 2, testTaskType);

        Exception exception = assertThrows(Exception.class, () -> {
            underTest.updateTask(testTask);
        });
        assertEquals("Task doesn't exist!", exception.getMessage());
    }

    @Test
    void updateTaskSuccess() throws Exception {
        Tasktype testTaskType = new Tasktype("Nabava pića");
        Task testTask = new Task("Nabaviti Coca Colu", 2, testTaskType);

        when(taskRepository.findById(null)).thenReturn(Optional.of(testTask));
        underTest.updateTask(testTask);
        verify(taskRepository).save(testTask);
    }

    @Test
    void findThrowsException() {
        final String taskId = "12";
        Tasktype testTaskType = new Tasktype("Nabava pića");
        Task testTask = new Task("Nabaviti Coca Colu", 2, testTaskType);

        Exception exception = assertThrows(Exception.class, () -> {
            underTest.find(taskId);
        });
        assertEquals("Could not find task", exception.getMessage());
    }

    @Test
    void findSuccess() throws Exception {
        final String taskId = "12";
        Tasktype testTaskType = new Tasktype("Nabava pića");
        Task testTask = new Task("Nabaviti Coca Colu", 2, testTaskType);

        when(taskRepository.findById(Integer.parseInt(taskId))).thenReturn(Optional.of(testTask));
        Task actualTask = underTest.find(taskId);
        Assert.assertEquals(testTask, actualTask);
    }

    @Test
    void listAll() {
        underTest.listAll();
        verify(taskRepository).findAll();
    }
}
