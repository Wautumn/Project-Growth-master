package com.org.growth.Service;

import com.org.growth.DAO.HistoryDao;
import com.org.growth.entity.History;
import com.org.growth.entity.Task;
import com.org.growth.entity.User;
import org.springframework.data.domain.*;
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
    public Page<History> viewHistory(long userId, int size, int page) {

        Sort sort = new Sort(Sort.Direction.DESC, "starttime");
        Pageable pageable = new PageRequest(page-1, size, sort);
        Query query = Query.query(Criteria.where("userId").is(userId));
        java.util.List<History> items = mongoTemplate.find(query.with(pageable), History.class);
        long total = mongoTemplate.count(query, History.class);
        return new PageImpl(items, pageable, total);

    }

    @Override
    public boolean saveStartTomato(long userId) {
        try {
            this.startTime = new Date();
            History history = new History();
            history.setStartTime(startTime);
            history.setUserId(userId);
            Query query = new Query(Criteria.where("_id").is(userId));
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
            Criteria criteria = new Criteria();
            criteria.and("userId").is(userId);
            criteria.and("starttime").is(startTime);
            Query query = Query.query(criteria);
            update.set("endtime", endTime);
            update.set("status", -1);
            mongoTemplate.updateFirst(query,update, History.class);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean saveEndTomato(long userId, boolean needAssociation) {
        return saveEndTomato(userId, needAssociation, "");
    }

    @Override
    public boolean saveEndTomato(long userId, boolean needAssociation, String taskName){
        try{
            endTime = new Date();

            //query
            Criteria criteria = new Criteria();
            criteria.and("userId").is(userId);
            criteria.and("starttime").is(startTime);
            Query query = Query.query(criteria);
            //update
            Update update = new Update();
            update.set("endtime", endTime);
            update.set("status", 1);
            mongoTemplate.updateFirst(query,update, History.class);

            if(needAssociation){
                //query
                Criteria criteria1 = new Criteria();
                criteria1.and("userId").is(userId);
                criteria1.and("name").is(taskName);
                Query query1 = Query.query(criteria1);
                Task list = mongoTemplate.findOne(query1, Task.class);

                //update
                Update update3 = new Update();
                update3.set("taskid", list.getId());
                mongoTemplate.updateFirst(query, update3, History.class);

                //task exists or not
                if (list != null){
                    //task exists
                    int tomatoCompleted = list.getTomatoCompleted() + 1;
                    int expectedTomato = list.getExpectedTomato();
                    //undone
                    if (tomatoCompleted < expectedTomato){
                        Update update1 = new Update();
                        update1.set("tomatoCompleted", tomatoCompleted);
                        update1.set("status", 1);
                        mongoTemplate.updateFirst(query1,update1, Task.class);
                    }
                    else{
                        Update update2 = new Update();
                        update2.set("tomatoCompleted", tomatoCompleted);
                        update2.set("status", 2);
                        mongoTemplate.updateFirst(query1,update2, Task.class);
                    }
                }
                else{
                    //task doesn't exist
                    Task list1 = new Task();
                    list1.setUserId(userId);
                    list1.setName(taskName);
                    list1.setDescription(null);
                    list1.setExpectedTomato(-1);
                    list1.setTomatoCompleted(1);
                    list1.setDeadline(null);
                    list1.setRemindTime(null);
                    list1.setFinishedTime(new Date());
                    list1.setStatus(2);
                    mongoTemplate.save(list1);
                }
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
