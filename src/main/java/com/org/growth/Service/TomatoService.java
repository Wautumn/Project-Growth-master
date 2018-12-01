package com.org.growth.Service;

import com.org.growth.DAO.HistoryDao;
import com.org.growth.entity.History;
import com.org.growth.entity.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class TomatoService implements HistoryDao {
    @Resource
    private MongoTemplate mongoTemplate;

    Date startTime, endTime;



    @Override
    public boolean saveStartTomato(long userId) {
        try {
            this.startTime = new Date();
            History history = new History();
            history.setStartTime(startTime);
            history.setUserId(userId);
            Query query = new Query(Criteria.where("userId").is(userId));
            User user = mongoTemplate.findOne(query,User.class);
            history.setTomatoLength(user.getTomatoLength());
            history.setTaskId(-1);
            mongoTemplate.insert(history);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean saveBreakTomato(long userId) {
        try{
            endTime = new Date();
            Update update = new Update();
            Query query = Query.query(Criteria.where("userId").is(userId));
            update.set("endtime", endTime);
            update.set("status", -1);
            mongoTemplate.updateFirst(query,update,History.class);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
