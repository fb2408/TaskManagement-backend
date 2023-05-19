package com.example.mobileappserver.controller;

import com.example.mobileappserver.model.User;
import com.example.mobileappserver.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private static Log log = LogFactory.getLog(LoginController.class);

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody User login(@RequestParam("email") String email){
        System.out.println(email);

        User user = null;
        try {
            user =  userService.login(email);
        } catch (Exception e) {
            log.info("[USER LOGIN] user tries to login with wrong password!");
            return null;
        }
        if(user.getActive()) {
            log.info("[USER LOGIN] user with id:" + user.getId() + " successfully logged in");
            return user;
        } else {
            log.info("[USER LOGIN] inactive user tries to login");
            return null;
        }
    }
}
