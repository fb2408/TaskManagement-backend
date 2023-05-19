package com.example.mobileappserver.service;

import com.example.mobileappserver.model.Task;
import com.example.mobileappserver.model.Usertask;
import com.example.mobileappserver.repository.TaskRepository;
import com.example.mobileappserver.repository.UserTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final UserTaskRepository userTaskRepository;
    private final TaskRepository taskRepository;

    public TaskService(UserTaskRepository userTaskRepository,
                       TaskRepository taskRepository) {
        this.userTaskRepository = userTaskRepository;
        this.taskRepository = taskRepository;
    }

    public void taskFinished(Integer taskId) throws Exception {
            Usertask task = userTaskRepository.findById(taskId).orElseThrow(()  -> new Exception("Task doesnt exsist"));
            if(task.getFinished()) {
                throw new Exception("Task already solved!!!");
            } else {
                task.setFinished(true);
                userTaskRepository.save(task);
            }
    }

    public void addTask(Task task) throws Exception {

        Task t = taskRepository.findByName(task.getName());
        if(t != null) {
            throw new Exception("Task with that name already exsist!");
        } else {
            taskRepository.save(task);
        }


    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }

    public void updateTask(Task task) throws Exception {
        Task changeTask = taskRepository.findById(task.getId()).orElseThrow(() -> new Exception("Task doesnt exsist!"));
        changeTask.setName(task.getName());
        changeTask.setLevel(task.getLevel());
        changeTask.setTaskType(task.getTaskType());
        taskRepository.save(changeTask);
    }

    public Task find(String taskId) throws Exception {
        return taskRepository.findById(Integer.parseInt(taskId)).orElseThrow(() -> new Exception("Could not find task"));
    }

    public List<Task> listAll() {
        return taskRepository.findAll();
    }
}
