package com.org.growth.controller;

import com.org.growth.Service.UserService;
import com.org.growth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LogInController {
    @Autowired
    UserService userService = new UserService();

    @ResponseBody
    @PostMapping (value = "/logIn")
    public List logIn(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password){
        return userService.logIn(username, password);
    }

}














