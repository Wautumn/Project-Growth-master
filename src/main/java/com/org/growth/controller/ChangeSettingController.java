package com.org.growth.controller;

import com.org.growth.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChangeSettingController {
    @Autowired
    UserService userService = new UserService();

    @ResponseBody
    @GetMapping(value = "/changeTomatoLength ")
    public boolean changeTomatoLength(@RequestParam(value = "userId") long userId, @RequestParam(value = "tomatoLength") int tomatoLength){
        return userService.changeTomatoLength(userId, tomatoLength);
    }

    /*
    @ResponseBody
    @GetMapping(value = "/changeMusic ")
    public boolean changeMusic(@RequestParam(value = "userId") long userId, @RequestParam(value = "music") String music){
        return userService.changeMusic(userId, music);
    }
    */

}




