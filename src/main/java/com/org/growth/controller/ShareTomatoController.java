package com.org.growth.controller;
import com.org.growth.Service.UserService;
import com.org.growth.entity.RespBean;
import com.org.growth.entity.User;
import com.org.growth.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
public class ShareTomatoController {

    @Autowired
    UserService userService=new UserService();

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
