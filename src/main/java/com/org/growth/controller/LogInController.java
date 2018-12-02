package com.org.growth.controller;

import com.org.growth.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LogInController {
    @Autowired
    UserService userService = new UserService();

    @ResponseBody
    @GetMapping(value = "/logIn")
    public boolean logIn(@RequestParam(value = "userId") long userId, @RequestParam(value = "password") String password){
        return userService.logIn(userId, password);
    }

}














