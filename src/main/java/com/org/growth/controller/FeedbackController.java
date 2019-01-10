package com.org.growth.controller;

import com.org.growth.Service.FeedbackService;
import com.org.growth.Service.UserService;
import com.org.growth.entity.Feedback;
import com.org.growth.entity.useful.RespBean;
import javafx.scene.input.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@RestController
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService=new FeedbackService();

    @Autowired
    UserService userService=new UserService();

    /*
    提交反馈
     */
    @RequestMapping(value="/submitFeedback", method = RequestMethod.POST)
    public RespBean submitFeedback(@RequestParam String content, @RequestParam(value = "userid")long userid,@RequestParam String title) {
        int result=feedbackService.addFeedback(userid,content,title);
        if(result==1) return new RespBean("success","提交成功");
        else return new RespBean("failure","提交失败");

    }

    /*
    用户查看自己的反馈
     */
    @RequestMapping(value = "/getMyFeedback",method = RequestMethod.GET)
    public List getOwnFeedback(@RequestParam long userid){
        List<Feedback> feedbacks=new LinkedList<>();
        if(userService.findByUserId(userid)==null) return feedbacks;
        feedbacks=feedbackService.getMyFeedback(userid);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Feedback feedback:feedbacks){

        }
        return feedbackService.getMyFeedback(userid);

    }

    /*
    管理员查看所有反馈
    */
    @RequestMapping(value = "/getAllFeedback",method = RequestMethod.GET)
    public List getOwnFeedback(){

        return feedbackService.getauthorFeedback();
    }



    /*
    handle feedback
     */
    @RequestMapping(value = "/handleFeedback",method = RequestMethod.POST)
    public RespBean handleFeedback(@RequestParam long id,@RequestParam String answer){
       int result=feedbackService.handleFeedback(id,answer);
       if(result==1) return new RespBean("success","处理成功");
       else return new RespBean("failure","失败");

    }









}
