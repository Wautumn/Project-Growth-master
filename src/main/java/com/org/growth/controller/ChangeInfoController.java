package com.org.growth.controller;

import com.org.growth.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChangeInfoController {
    @Autowired
    UserService userService = new UserService();

    @ResponseBody
    @GetMapping(value = "/changeUsername ")
    public boolean changeUsername(@RequestParam(value = "userId") long userId, @RequestParam(value = "username") int username){
        return userService.changeTomatoLength(userId, username);
    }

    @ResponseBody
    @GetMapping(value = "/changePassword ")
    public boolean changePassword(@RequestParam(value = "userId") long userId, @RequestParam(value = "password") int password){
        return userService.changeTomatoLength(userId, password);
    }

    @ResponseBody
    @GetMapping(value = "/changeEmail ")
    public boolean changeEmail(@RequestParam(value = "userId") long userId, @RequestParam(value = "email") int email){
        return userService.changeTomatoLength(userId, email);
    }

    @ResponseBody
    @GetMapping(value = "/changeUserFace ")
    public boolean changeUserFace(@RequestParam(value = "userId") long userId, @RequestParam(value = "userFace") int userFace){
        return userService.changeTomatoLength(userId, userFace);
    }

}














