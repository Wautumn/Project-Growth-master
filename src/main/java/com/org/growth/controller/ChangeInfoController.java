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
    public String changeUsername(@RequestParam(value = "userId") long userId, @RequestParam(value = "username") String username){
        return userService.changeUsername(userId, username);
    }

    @ResponseBody
    @GetMapping(value = "/changePassword ")
    public boolean changePassword(@RequestParam(value = "username") String username, @RequestParam(value = "oldPassword") String oldPassword, @RequestParam(value = "newPassword") String newPassword){
        return userService.changePassword(username, oldPassword, newPassword);
    }

    @ResponseBody
    @GetMapping(value = "/changeEmail ")
    public String changeEmail(@RequestParam(value = "userId") long userId, @RequestParam(value = "email") String email){
        return userService.changeEmail(userId, email);
    }

    @ResponseBody
    @GetMapping(value = "/changeUserFace ")
    public String changeUserFace(@RequestParam(value = "userId") long userId, @RequestParam(value = "userFace") String userFace){
        return userService.changeUserFace(userId, userFace);
    }

}














