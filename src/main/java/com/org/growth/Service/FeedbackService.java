package com.org.growth.Service;

import com.org.growth.DAO.FeedbackDAO;
import com.org.growth.entity.Feedback;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class FeedbackService implements FeedbackDAO {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public Feedback getFeedbackById(Long feedBackId){
        Query query = Query.query(Criteria.where("id").is(feedBackId));
        return mongoTemplate.findOne(query,Feedback.class);
    }
    @Override
    public void addFeedback(Feedback feedback){

        mongoTemplate.insert(feedback);

    }

    @Override
    public void deleteFeedback(Long feedBackId){
        Query query = Query.query(Criteria.where("id").is(feedBackId));
        mongoTemplate.remove(query,Feedback.class);
    }

    @Override//get 10 feedbacks to show,pagedesign
    public List<Feedback> getFeedback(int page){

        return null;
    }

    @Override
    public List<Feedback> getMyFeedback(int userId){

        Query query = Query.query(Criteria.where("userid").is(userId));
        return mongoTemplate.find(query,Feedback.class);

    }
}
