package com.org.growth.DAO;

import com.org.growth.entity.Feedback;

import java.util.List;

public interface FeedbackDAO {
    void addFeedback(Feedback feedback);

    void deleteFeedback(Long feedBackId);

    List<Feedback> getFeedback(int page);//get 10 feedbacks to show,pagedesign

    Feedback getFeedbackById(Long feedBackId);

    List<Feedback> getMyFeedback(int userId);




}
