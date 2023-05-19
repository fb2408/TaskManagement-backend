package com.example.mobileappserver.controller;

import com.example.mobileappserver.model.*;
import com.example.mobileappserver.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private static Log log = LogFactory.getLog(LoginController.class);

    private final UserTaskService userTaskService;

    private final UserService userService;

    private final FirebaseMessaggingService firebaseMessaggingService;

    private final TaskService taskService;

    private final TaskTypeService taskTypeService;

    private final OrganizationUnitService organizationUnitService;

    public AdminController(UserTaskService userTaskService, UserService userService, FirebaseMessaggingService firebaseMessaggingService, TaskService taskService, TaskTypeService taskTypeService, OrganizationUnitService organizationUnitService) {
        this.userTaskService = userTaskService;
        this.userService = userService;
        this.firebaseMessaggingService = firebaseMessaggingService;
        this.taskService = taskService;
        this.taskTypeService = taskTypeService;
        this.organizationUnitService = organizationUnitService;
    }

    @RequestMapping(value = "/add-task", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        try {
            taskService.addTask(task);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        System.out.println(task.getId());
        System.out.println(task.getName());
        System.out.println(task.getLevel());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/update-task", method = RequestMethod.POST)
    public void updateTask(@RequestBody Task task) throws Exception {
        taskService.updateTask(task);

    }

    @RequestMapping(value = "/deleteTask", method = RequestMethod.GET)
    public void deleteTask(@RequestParam("id") String taskId) {
        taskService.deleteTask(Integer.parseInt(taskId));
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void addWorker(@RequestBody User user) {
        userService.addWorker(user);
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getWorkingYears());
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    public void updateWorker(@RequestBody User user) throws Exception {
        userService.updateWorker(user);

    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public void deleteWorker(@RequestParam("id") String workerid) {
        userService.deleteWorker(Integer.parseInt(workerid));
    }

    @RequestMapping(value = "/addOrganizationUnit", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void addOrganizationUnit(@RequestBody OrganizationUnit organizationunit) {
        organizationUnitService.addOrganizationUnit(organizationunit);
        System.out.println(organizationunit.getId());
        System.out.println(organizationunit.getName());
        System.out.println(organizationunit.getCloseTime());
    }

    @RequestMapping(value = "/updateOrganizationUnit", method = RequestMethod.GET)
    public void updateOrganizationUnit(@RequestBody OrganizationUnit organizationunit) throws Exception {
        organizationUnitService.updateOrganizationUnit(organizationunit);

    }

    @RequestMapping(value = "/deleteOrganizationUnit", method = RequestMethod.GET)
    public void deleteOrganizationUnit(@RequestParam("id") String orgUnitId) {
        organizationUnitService.deleteOrganizationUnit(Integer.parseInt(orgUnitId));
    }

    @RequestMapping(value = "/addUserTask", method = RequestMethod.POST)
    public void addUserTask(@RequestBody Usertask userTask) {
//        userTaskService.addUserTask(userTask);
//        String id = String.valueOf(userTask.getUser().getId());
        String id = "1";
//        String taskId = String.valueOf(userTask.getTask().getId());
        String taskId = "1";
        System.out.println("Yeah");
    }

    @RequestMapping(value = "/delete-user-task", method = RequestMethod.GET)
    public void deleteUserTask(@RequestParam("id") String id) {
        userTaskService.deleteUserTask(Integer.parseInt(id));
        log.info("[DELETED TASK] Successfully deleted user task with id: " + id);
    }

    @RequestMapping(value = "/new-task-notification")
    @ResponseBody
    public String newTaskNotification(@RequestParam("id") String id, @RequestParam("taskId") String taskId) throws Exception {
        User user = userService.find(id);
        Task task = taskService.find(taskId);
        Note note = new Note();
        note.setSubject("You got a new task!!!");
        note.setContent(task.getName());
        note.setData(new HashMap<>());
        return firebaseMessaggingService.sendNotificationToOne(note, user.getDeviceToken());
    }

    @RequestMapping(value="/list-all-workers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<User> listWorkers() {
        List<User> userList = userService.findAllWorkers();
        return userList;
    }

    @RequestMapping(value = "/list-all-task-types", method = RequestMethod.GET)
    public @ResponseBody List<Tasktype> listTaskTypes() {
        List<Tasktype> tasktypeList = taskTypeService.getAllTaskTypes();
        for (Tasktype sout : tasktypeList) {
            System.out.println(sout.getTaskTypeName());
        }
        return tasktypeList;
    }

//    @RequestMapping(value = "/list-unfinished-tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody List<Task> listUnDistributedTasks() {
//
//    }


    @RequestMapping(value="/overview-user-tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Usertask> overviewOnUserTasks() {
        List<Usertask> userTaskList = userTaskService.find();
        return userTaskList;
    }



//    @GetMapping(value = "/task-distribution", produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody DistributionDto taskDistribution() {
//        List<User> workers = userService.findAllWorkers();
//        workers.forEach(w -> System.out.println(w.getName()));
//        List<Task> taskList = taskService.listAll();
//        List<OrganizationUnit> organizationUnitList = organizationUnitService.listAll();
//        DistributionDto distributionDto = new DistributionDto();
//        distributionDto.setTaskList(taskList);
//        distributionDto.setWorkerList(workers);
//        distributionDto.setOrganizationUnitList(organizationUnitList);
//        return distributionDto;
//    }




}
