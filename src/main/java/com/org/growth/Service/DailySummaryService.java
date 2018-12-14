 package com.org.growth.Service;

import com.org.growth.DAO.SummaryDao;
import com.org.growth.entity.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

     class Result{
            private String content;
            private String time;
            private int selfRating;

            Result(){};
            Result(String content, String time, int selfRating){
                this.content = content;
                this.time = time.substring(0,10);
                this.selfRating =selfRating;
            };

         public String getTime() {
             return time;
         }

         public int getSelfRating() {
             return selfRating;
         }

         public String getContent() {
             return content;
         }

         public void setTime(String time) {
             this.time = time;
         }

         public void setSelfRating(int selfRating) {
             this.selfRating = selfRating;
         }

         public void setContent(String content) {
             this.content = content;
         }

     }

     @Override
     public List<Summary> querySummaryByYear(long userId, String year) {
        List resultList = new ArrayList();
        //query all summary
        Criteria criteria = new Criteria();
        criteria.and("userId").is(userId);
        Query query = Query.query(criteria);
        query.with(new Sort(Sort.Direction.ASC, "time"));
        String startOfYearStr = year + "-01-01 00:00:00";
        String endOfYearStr = year + "-12-31 23:59:59";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date startOfYear = null;
         Date endOfYear = null;
        try {
            startOfYear= sdf.parse(startOfYearStr);
            endOfYear =sdf.parse(endOfYearStr);
         } catch (ParseException e) {
             return  null;
         }
        query.addCriteria(Criteria.where("time").lte(endOfYear).gte(startOfYear));
        List queryResult = mongoTemplate.find(query, Summary.class);
        Iterator resultIterator = queryResult.iterator();
        Summary summary;
        do {
            summary = (Summary)resultIterator.next();
            Result result = new Result(summary.getContent(), sdf.format(summary.getTime()), summary.getSelfRating());
            resultList.add(result);
        }while(resultIterator.hasNext());
        return resultList;
     }
 }
