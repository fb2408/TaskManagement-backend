package com.example.mobileappserver.service;


import com.example.mobileappserver.model.OrganizationUnit;
import com.example.mobileappserver.model.Usertask;
import com.example.mobileappserver.repository.OrganizationUnitRepository;
import com.example.mobileappserver.repository.TaskRepository;
import com.example.mobileappserver.repository.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTaskService {

    @Autowired
    private final UserTaskRepository userTaskRepository;
    private final TaskRepository taskRepository;


    public UserTaskService(UserTaskRepository userTaskRepository,
                           TaskRepository taskRepository) {
        this.userTaskRepository = userTaskRepository;
        this.taskRepository = taskRepository;
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
            throw new Exception("Task already required for redirecting.");
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

    public List<Usertask> findByUser(Integer id) {
        return userTaskRepository.findAllByUserId(id);
    }

    public void update(Usertask userTask) {
        taskRepository.save(userTask.getTask());
        System.out.println(userTask.getId());
        Usertask userTaskChange = userTaskRepository.findById(userTask.getId()).orElseThrow(()-> new ExpressionException("..."));
        userTaskChange.setFinished(userTask.getFinished());
        userTaskRepository.save(userTaskChange);
    }

    public List<Usertask> findByOrganizationUnit(int parseInt) {
        return userTaskRepository.findByOrganizationUnitId(parseInt);
    }

}
