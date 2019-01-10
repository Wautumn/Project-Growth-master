package com.org.growth.Service;

import com.org.growth.DAO.FeedbackDAO;
import com.org.growth.entity.Feedback;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
public class FeedbackService implements FeedbackDAO {
    @Resource
    private MongoTemplate mongoTemplate;


    @Override
    public int addFeedback(long userid,String content,String title){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Feedback feedback=new Feedback();
        feedback.setUserid(userid);
        feedback.setContent(content);
        feedback.setTitle(title);
        feedback.setStatus(0);
        feedback.setAnswer(null);
        feedback.setTime(formatter.format(new Date()));
        mongoTemplate.insert(feedback);
        return 1;

    }

    @Override//get 10 feedbacks to show,pagedesign
    public List<Feedback> getFeedback(){

        List<Feedback> feedbacks=mongoTemplate.findAll(Feedback.class);
        for(Feedback feedback:feedbacks){
          Authorfeedback authorfeedback=new Authorfeedback();

        }
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
        System.out.println(feedback.getId());
        Update update=Update.update("status",1);
        update.addToSet("answer",answer);
        mongoTemplate.updateFirst(query,update,Feedback.class);
        return 1;
    }
    class Authorfeedback{
        long id;
        String name;
        String title;
        String content;
        String date;
        String answer;
        int status;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getName() {
            return name;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getDate() {
            return date;
        }

        public int getStatus() {
            return status;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public List<Authorfeedback> getauthorFeedback(){

        List<Feedback> feedbacks=mongoTemplate.findAll(Feedback.class);
        List<Authorfeedback> authorfeedbacks=new LinkedList<>();
        for(Feedback feedback:feedbacks){
            Authorfeedback authorfeedback=new Authorfeedback();
            authorfeedback.setId(feedback.getId());
            authorfeedback.setContent(feedback.getContent());
            authorfeedback.setDate(feedback.getTime());
            authorfeedback.setName(UserService.findById(feedback.getUserid()).getUsername());
            authorfeedback.setTitle(feedback.getTitle());
            authorfeedback.setStatus(feedback.getStatus());
            if(feedback.getAnswer()!=null)authorfeedback.setAnswer(feedback.getAnswer());
            authorfeedbacks.add(authorfeedback);


        }
        return authorfeedbacks;
    }


}
