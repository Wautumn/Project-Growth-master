package com.org.growth.controller;

import com.org.growth.Service.UserService;
import com.org.growth.entity.useful.RespBean;
import com.org.growth.entity.User;
import com.org.growth.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService = new UserService();

    @ResponseBody
    @PostMapping (value = "/logIn")
    public List logIn(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password){
        return userService.logIn(username, password);
    }

    @ResponseBody
    @PostMapping(value = "/signUp")
    public long signUp(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, @RequestParam(value = "email") String email){
        return userService.signUp(username, password, email);
    }

    @ResponseBody
    @GetMapping(value = "/changeUsername ")
    public String changeUsername(@RequestParam(value = "userId") long userId, @RequestParam(value = "username") String username){
        return userService.changeUsername(userId, username);
    }

    @ResponseBody
    @PostMapping(value = "/changePassword ")
    public boolean changePassword(@RequestParam(value = "userId") long userId, @RequestParam(value = "oldPassword") String oldPassword, @RequestParam(value = "newPassword") String newPassword){
        return userService.changePassword(userId, oldPassword, newPassword);
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

    @ResponseBody
    @GetMapping(value = "/changeTomatoLength ")
    public int changeTomatoLength(@RequestParam(value = "userId") long userId, @RequestParam(value = "tomatoLength") int tomatoLength){
        return userService.changeTomatoLength(userId, tomatoLength);
    }

    @ResponseBody
    @GetMapping(value = "/changeDayGoal ")
    public int changeDayGoal(@RequestParam(value = "userId") long userId, @RequestParam(value = "dayGoal") int dayGoal){
        return userService.changeDayGoal(userId, dayGoal);
    }

    @ResponseBody
    @GetMapping(value = "/changeWeekGoal ")
    public int changeWeekGoal(@RequestParam(value = "userId") long userId, @RequestParam(value = "weekGoal") int weekGoal){
        return userService.changeWeekGoal(userId, weekGoal);
    }

    @ResponseBody
    @GetMapping(value = "/changeMonthGoal ")
    public int changeMonthGoal(@RequestParam(value = "userId") long userId, @RequestParam(value = "monthGoal") int monthGoal){
        return userService.changeMonthGoal(userId, monthGoal);
    }

    /*
     @ResponseBody
    @GetMapping(value = "/changeTomatoLength ")
    public int changeTomatoLength(@RequestParam(value = "userId") long userId, @RequestParam(value = "tomatoLength") int tomatoLength){
        return userService.changeTomatoLength(userId, tomatoLength);
    }

    /*
    @ResponseBody
    @GetMapping(value = "/changeMusic ")
    public boolean changeMusic(@RequestParam(value = "userId") long userId, @RequestParam(value = "music") String music){
        return userService.changeMusic(userId, music);
    }

     */

    @RequestMapping(value = "/share")
    public RespBean shareTomato() {
        User user = Util.getCurrentUser();
        Long userId = user.getId();
        int count = userService.getTomatoWeeklyCount(userId);
        if (count < 1) {
            //无法分享
            return new RespBean("error", "没有足够的番茄");
        } else {
            userService.TomatoCountReduceOne(userId);//数量减一
            //唤起分享接口
            return new RespBean("success", "分享成功！");
        }
    }

    @RequestMapping(value = "/getWeeklyTomatoCount",method= RequestMethod.GET)
    public int getWeeklyTomatoCount(@RequestParam(value = "userid")long userId)
    {
        int count=0;
        count = userService.getTomatoWeeklyCount(userId);
        return count;
    }
}
