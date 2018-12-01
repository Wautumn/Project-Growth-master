package com.org.growth.controller;

import com.org.growth.Service.FeedbackService;
import com.org.growth.entity.Feedback;
import com.org.growth.entity.RespBean;
import com.org.growth.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.Date;

@Controller
@RequestMapping("/Feedback")
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService=new FeedbackService();



    @RequestMapping(value="/submitFeedback", method = RequestMethod.POST)
    public RespBean submitFeedback(String feedbackContent){
        Feedback feedback=new Feedback();
        feedback.setContent(feedbackContent);
        feedback.setTime(new Date());
        feedback.setUserid(Util.getCurrentUser().getId());
        feedbackService.addFeedback(feedback);
        return new RespBean("success","提交成功");

    }
}
