package com.org.growth.controller;

import com.org.growth.Service.FeedbackService;
import com.org.growth.entity.Feedback;
import com.org.growth.entity.RespBean;
import com.org.growth.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.Date;

@RestController
@RequestMapping("/Feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService=new FeedbackService();

    @RequestMapping(value="/submitFeedback", method = RequestMethod.POST)
    public RespBean submitFeedback(@RequestBody String content, @RequestParam(value = "userid")long userid) {
        Feedback feedback = new Feedback();
        feedback.setContent(content);
        feedback.setUserid(userid);
        feedback.setTime(new Date());
        feedback.setUserid(2l);
        int result=feedbackService.addFeedback(feedback);
        if(result==1) return new RespBean("success","提交成功");
        else return new RespBean("failure","提交失败");

    }




}
