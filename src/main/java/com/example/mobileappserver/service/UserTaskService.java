package com.example.mobileappserver.service;

import com.example.mobileappserver.model.Usertask;
import com.example.mobileappserver.repository.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTaskService {

    @Autowired
    private final UserTaskRepository userTaskRepository;

    public UserTaskService(UserTaskRepository userTaskRepository) {
        this.userTaskRepository = userTaskRepository;
    }

    public Usertask taskFinished(Integer id) throws Exception {
        Usertask userTask = userTaskRepository.findById(id).orElseThrow(() -> new Exception("Task not found, something went wrong :("));

        if(userTask.getFinished()) {
            System.out.println("Task already finished!");
            throw new Exception("Task already finished!");
        } else {
            userTask.setFinished(true);
            userTaskRepository.save(userTask);
            System.out.println("Task succesfully change to finished!!");
        }
        return userTask;
    }

    public List<Usertask> getTasksForWorker(Integer id) {
        return userTaskRepository.findAllByUserId(id);
    }

    public void requestForRedirectingTask(Integer id) throws Exception {
        Usertask task = userTaskRepository.findById(id).orElseThrow(()  -> new Exception("Task doesnt exsist"));
        if(task.getReqRedirect()) {
            System.out.println("Task already required for redirecting.");
            new Exception("Task already required for redirecting.");
        } else {
            task.setReqRedirect(true);
            userTaskRepository.save(task);
            System.out.println("Task succesfully requestd for redirecting!");
        }
    }

    public void addUserTask(Usertask userTask) {
        userTaskRepository.save(userTask);
    }

    public List<Usertask> find() {
        return userTaskRepository.findAll();
    }

    public void deleteUserTask(int userTaskId) {
        userTaskRepository.deleteById(userTaskId);
    }

    public Usertask getUserTask(int userTaskId) throws Exception {
        return userTaskRepository.findById(userTaskId).orElseThrow(() ->  new Exception("User task not found"));

    }
}
