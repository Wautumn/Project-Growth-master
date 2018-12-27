package com.org.growth.controller;

import com.org.growth.Service.FeedbackService;
import com.org.growth.Service.UserService;
import com.org.growth.entity.Feedback;
import com.org.growth.entity.useful.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/Feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService=new FeedbackService();

    @Autowired
    UserService userService=new UserService();

    /*
    提交反馈
     */
    @RequestMapping(value="/submitFeedback", method = RequestMethod.POST)
    public RespBean submitFeedback(@RequestBody String content, @RequestParam(value = "userid")long userid) {
        int result=feedbackService.addFeedback(userid,content);
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
        return feedbackService.getMyFeedback(userid);

    }

    /*
    管理员查看所有反馈
    */
    @RequestMapping(value = "/getAllFeedback",method = RequestMethod.GET)
    public List getOwnFeedback(){
        return feedbackService.getFeedback();
    }

    /*
    管理员查看未处理的反馈
     */
    @RequestMapping(value = "/getUnhandledFeedback",method = RequestMethod.GET)
    public List getunhandled(){
        return feedbackService.getUnhandled();
    }





}
