package com.org.growth.controller;

import com.org.growth.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LogInController {
    @Autowired
    UserService userService = new UserService();

    @ResponseBody
    @PostMapping (value = "/logIn")
    public long logIn(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password){
        return userService.logIn(username, password);
    }

}














