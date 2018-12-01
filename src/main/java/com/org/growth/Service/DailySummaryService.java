 package com.org.growth.Service;

import com.org.growth.DAO.SummaryDao;
import com.org.growth.entity.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class DailySummaryService implements SummaryDao {

    @Resource
    private MongoTemplate mongoTemplate;


    @Override
    public boolean saveSummary(long userid, String content, Date time) {
        try {
            Summary dailySummary = new Summary();
            dailySummary.setUserId(userid);
            dailySummary.setContent(content);
            dailySummary.setTime(time);

            mongoTemplate.insert(dailySummary);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
