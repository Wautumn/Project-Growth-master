package com.org.growth.DAO;

import com.org.growth.entity.Feedback;

import java.util.List;

public interface FeedbackDAO {
    int addFeedback(long userid,String content);

    void deleteFeedback(long feedBackId);

    List<Feedback> getFeedback();//get 10 feedbacks to show,pagedesign

    List<Feedback> getUnhandled();

    List<Feedback> getMyFeedback(long userId);

    int handleFeedback(long id,String answer);




}
