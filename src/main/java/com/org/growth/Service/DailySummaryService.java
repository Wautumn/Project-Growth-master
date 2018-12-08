 package com.org.growth.Service;

import com.org.growth.DAO.SummaryDao;
import com.org.growth.entity.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

 /***
  * @author Ruabick
  * save and read dailysummary
  */
@Component
public class DailySummaryService implements SummaryDao {

    @Resource
    private MongoTemplate mongoTemplate;


    @Override
    public boolean saveSummary(long userId, String content, Date time,int selfRating) {
        try {
            //isExisted
            Criteria criteria = new Criteria();
            criteria.and("userId").is(userId);
            criteria.and("time").is(time);
            Query query = Query.query(criteria);
            Summary summary = mongoTemplate.findOne(query,Summary.class);
            if (summary != null)
                return false;

            Summary dailySummary = new Summary();
            dailySummary.setUserId(userId);
            dailySummary.setContent(content);
            dailySummary.setTime(time);
            dailySummary.setSelfRating(selfRating);

            mongoTemplate.insert(dailySummary);
            return true;
        } catch (Exception e){
            return false;
        }
    }

     @Override
     public List readSummary(long userId, Date time) {
        List list = new ArrayList();
        try {
            Criteria criteria = new Criteria();
            criteria.and("userId").is(userId);
            criteria.and("time").is(time);
            Query query = Query.query(criteria);
            Summary summary = mongoTemplate.findOne(query,Summary.class);
            if(summary!=null){
            list.add(true);
            list.add(summary);
            return list;
            }
            else {
                list.add(false);
                list.add(null);
                return list;
            }
        }
        catch (Exception e) {
            list.add(false);
            list.add(null);
            return list;
        }
     }

     @Override
     public boolean modifySummary(long userId, String content, Date time, int selfRating) {
         try {
             Criteria criteria = new Criteria();
             criteria.and("userId").is(userId);
             criteria.and("time").is(time);
             Query query = Query.query(criteria);
             Update update = new Update();
             update.set("content", content);
             update.set("selfrating",selfRating);

             mongoTemplate.updateFirst(query,update,Summary.class);
             return true;
         }
         catch (Exception e){
             return false;
         }
     }
 }
