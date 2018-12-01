package com.org.growth.controller;

import com.org.growth.Service.UserService;
import com.org.growth.entity.User;
import com.org.growth.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ShareTomatoController {

    @Autowired
    UserService userService=new UserService();

    @RequestMapping(value = "/share")
    public int shareTomato(){
        User user= Util.getCurrentUser();
        Long userId=user.getId();
        int count=userService.getTomatoWeeklyCount(userId);

        if(count<1){
            //无法分享
        }else{
            userService.TomatoCountReduceOne(userId);//数量减一
            //唤起分享接口

        }

    }

}
