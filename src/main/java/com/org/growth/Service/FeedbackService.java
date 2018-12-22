package com.org.growth.Service;

import com.org.growth.DAO.FeedbackDAO;
import com.org.growth.entity.Feedback;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class FeedbackService implements FeedbackDAO {
    @Resource
    private MongoTemplate mongoTemplate;


    @Override
    public int addFeedback(long userid,String content){
        Feedback feedback=new Feedback();
        feedback.setUserid(userid);
        feedback.setContent(content);
        feedback.setStatus(0);
        feedback.setTime(new Date());
        mongoTemplate.insert(feedback);
        return 1;

    }
    @Override
    public void deleteFeedback(long feedBackId){
        Query query = Query.query(Criteria.where("id").is(feedBackId));
        mongoTemplate.remove(query,Feedback.class);
    }

    @Override//get 10 feedbacks to show,pagedesign
    public List<Feedback> getFeedback(){
        List<Feedback> feedbacks=mongoTemplate.findAll(Feedback.class);
        return feedbacks;
    }

    @Override
    public List<Feedback> getUnhandled(){
        Query query=Query.query(Criteria.where("status").is(0));
        return mongoTemplate.find(query,Feedback.class);
    }

    @Override
    public List<Feedback> getMyFeedback(long userId){
        Query query = Query.query(Criteria.where("userid").is(userId));
        return mongoTemplate.find(query,Feedback.class);
    }

    @Override
    public int handleFeedback(long id,String answer){
        Query query=Query.query(Criteria.where("id").is(id));
        Feedback feedback=mongoTemplate.findOne(query,Feedback.class);
        if(feedback==null) return 0;
        feedback.setAnswer(answer);
        feedback.setStatus(1);
        return 1;
    }
}
