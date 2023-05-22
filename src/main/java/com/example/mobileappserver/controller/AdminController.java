package com.example.mobileappserver.controller;

import com.example.mobileappserver.model.*;
import com.example.mobileappserver.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @RequestMapping(value = "/get-user", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody User getUser(@RequestParam("name") String name) {
        System.out.println("Find user by name " + name);
        User u = userService.findByName(name);
        System.out.println(u.getName());
        return u;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void addWorker(@RequestBody User user) {
        userService.addWorker(user);
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getWorkingYears());
    }

    @RequestMapping(value = "/update-user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateWorker(@RequestBody User user, @RequestParam("name") String orgUnitName) throws Exception {
        List<Usertask> usertask = userTaskService.findByUser(user.getId());
        OrganizationUnit changeOrgUnit = organizationUnitService.findByName(orgUnitName);
        for (Usertask usertask1 : usertask) {
            System.out.println(changeOrgUnit.getName());
            usertask1.setOrganizationUnit(changeOrgUnit);
            userTaskService.update(usertask1);
        }
        userService.updateWorker(user);

    }

    @RequestMapping(value = "/delete-user", method = RequestMethod.GET)
    public void deleteWorker(@RequestParam("id") String userId) {
        List<Usertask> usertaskList = userTaskService.findByUser(Integer.parseInt(userId));
        usertaskList.forEach(ut ->  userTaskService.deleteUserTask(ut.getId()));
        userService.deleteWorker(Integer.parseInt(userId));
    }

    @RequestMapping(value = "/add-organization-unit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationUnit> addOrganizationUnit(@RequestBody OrganizationUnit organizationunit) {
        System.out.println("Try to add unit with name " + organizationunit.getName());
        try {
            organizationUnitService.addOrganizationUnit(organizationunit);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/update-organization-unit", method = RequestMethod.POST)
    public void updateOrganizationUnit(@RequestBody OrganizationUnit organizationunit) throws Exception {
        System.out.println("Try to update organization unit with id" + organizationunit.getId());
        System.out.println(organizationunit.getName());
        System.out.println(organizationunit.getOpenTime());
        organizationUnitService.updateOrganizationUnit(organizationunit);

    }

    @RequestMapping(value = "/delete-organization-unit", method = RequestMethod.GET)
    public void deleteOrganizationUnit(@RequestParam("id") String orgUnitId) {
        System.out.println("Try to delete org unit with id " + orgUnitId);
        List<Usertask> usertaskList = userTaskService.findByOrganizationUnit(Integer.parseInt(orgUnitId));
        usertaskList.forEach(ut ->  userTaskService.deleteUserTask(ut.getId()));
        organizationUnitService.deleteOrganizationUnit(Integer.parseInt(orgUnitId));

    }

    @RequestMapping(value = "get-org-unit-by-username", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody OrganizationUnit getOrgUnitByUserName(@RequestParam("id") String id) {
        return userTaskService.findByUser(Integer.parseInt(id)).get(0).getOrganizationUnit();
    }

    @RequestMapping(value = "/addUserTask", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usertask> addUserTask(@RequestBody Usertask userTask) {
        User u = userService.findByName(userTask.getUser().getName());
        OrganizationUnit ou = userTaskService.findByUser(u.getId()).get(0).getOrganizationUnit();

        Usertask userTaskChange = new Usertask();
        userTaskChange.setTask(userTask.getTask());
        userTaskChange.setUser(u);
        userTaskChange.setOrganizationUnit(ou);
        userTaskChange.setFinished(userTask.getFinished());
        userTaskChange.setReqRedirect(false);

        try {
            taskService.addTask(userTask.getTask());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        userTaskService.addUserTask(userTaskChange);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/update-user-task", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUserTask(@RequestBody Usertask userTask) {
        System.out.println(userTask.getFinished());
        userTaskService.update(userTask);

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


    @RequestMapping(value="/overview-user-tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Usertask> overviewOnUserTasks() {
        List<Usertask> userTaskList = userTaskService.find();
        return userTaskList;
    }

    @RequestMapping(value="/list-units", method = RequestMethod.GET)
    public @ResponseBody List<OrganizationUnit> listUnits() {
        List<OrganizationUnit> organizationUnitList = organizationUnitService.listAll();
        return organizationUnitList;
    }







}
