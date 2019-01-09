package com.org.growth.DAO;

import com.org.growth.entity.Feedback;

import java.util.List;

public interface FeedbackDAO {
    int addFeedback(long userid,String content,String title);//user add feedback

    List<Feedback> getFeedback();//getallfeedbacks

    List<Feedback> getUnhandled();//status 0

    List<Feedback> getMyFeedback(long userId);//user feedbacks

    int handleFeedback(long userid,String answer,String date);




}
