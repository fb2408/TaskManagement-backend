package com.example.mobileappserver.controller;

import com.example.mobileappserver.model.Note;
import com.example.mobileappserver.model.Usertask;
import com.example.mobileappserver.service.FirebaseMessaggingService;
import com.example.mobileappserver.service.UserTaskService;
import com.example.mobileappserver.service.UserService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/worker")
public class WorkerController {

    private static Log log = LogFactory.getLog(LoginController.class);

    private final FirebaseMessaggingService firebaseMessaggingService;

    private final UserTaskService userTaskService;

    private final UserService userService;

    public WorkerController(FirebaseMessaggingService firebaseMessaggingService, UserTaskService userTaskService, UserService userService) {
        this.firebaseMessaggingService = firebaseMessaggingService;
        this.userTaskService = userTaskService;
        this.userService = userService;
    }

    @RequestMapping(value = "/redirectRequest", method = RequestMethod.GET)
    public void requestForRedirectTask(@RequestParam("task_id") String taskId) throws Exception {
        log.info("[USER REQUEST] user want to redirect task with id = " + taskId);
        Integer id = Integer.parseInt(taskId);
        userTaskService.requestForRedirectingTask(id);
    }

    @RequestMapping(value = "/getTasksForWorker", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Usertask> getTasksForWorker(@RequestParam("id") String workerId) {
        log.info("[USER REQUEST] user with id = " + workerId + " wants to get own tasks");
        Integer id = Integer.parseInt(workerId);
        return userTaskService.getTasksForWorker(id);
    }

    @RequestMapping(value = "/finishingTask", method = RequestMethod.GET)
    public @ResponseBody Usertask changeTaskToFinished(@RequestParam("id") String taskId)throws Exception{
        log.info("[USER REQUEST] user finished task with id = " + taskId);
        Integer id = Integer.parseInt(taskId);
        Usertask usertask =userTaskService.taskFinished(id);
        return usertask;
    }

    @RequestMapping("/task-finished-notification")
    @ResponseBody
    public String sendNotification(@RequestParam("id") String userTaskId) throws FirebaseMessagingException {
        try {
            Usertask currentUserTask = userTaskService.getUserTask(Integer.parseInt(userTaskId));
            Note note = new Note();
            note.setSubject("Task finished!");
            note.setContent(currentUserTask.getUser().getName() + " je " + currentUserTask.getTask().getName());
            note.setData(new HashMap<>());
            return firebaseMessaggingService.sendNotification(note);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @RequestMapping("/change-device-token")
    public void changeDeviceToken(@RequestParam("id") String id, @RequestParam("deviceToken") String deviceToken) throws Exception {
        log.info("[USER REQUEST] user want to update device token to new value " + deviceToken);
        userService.changeDeviceToken(id, deviceToken);
        System.out.println(deviceToken);

    }

}
