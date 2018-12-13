package com.org.growth.controller;

import com.org.growth.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SignUpController {
    @Autowired
    UserService userService = new UserService();

    @ResponseBody
    @GetMapping(value = "/signUp")
    public long signUp(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, @RequestParam(value = "email") String email){
        return userService.signUp(username, password, email);
    }
}




















