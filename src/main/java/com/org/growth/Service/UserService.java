package com.org.growth.Service;


import com.org.growth.DAO.UserDAO;
import com.org.growth.entity.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;



import javax.annotation.Resource;


@Component
public class UserService implements UserDAO {
    @Resource
    private static MongoTemplate mongoTemplate;

    /*
    得到一周的番茄数
     */
    static User findById(long userId){
        Criteria criteria = new Criteria();
        criteria.and("id").is(userId);
        Query query = Query.query(criteria);
        return  mongoTemplate.findOne(query,User.class);
    }
    @Override
    public int getTomatoWeeklyCount(Long userId){
        Query query=Query.query(Criteria.where("id").is(userId));
        User user=mongoTemplate.findOne(query, User.class);
        int count=user.getTomatoweekly();
        return count;
    }

    /*
    分享番茄，周番茄数量减一
     */
    public int TomatoCountReduceOne(Long userId){
        Query query=Query.query(Criteria.where("id").is(userId));
        User user=mongoTemplate.findOne(query, User.class);
        int count=user.getTomatoweekly();
        if(count<1)
        {
            return -1;
        }
        else {
            count--;
           // Query query=Query.query(Criteria.where("id").is(userId));
            user.setTomatoweekly(count);
            Update update=new Update();
            update.addToSet("tomatoWeekly",count);
            mongoTemplate.upsert(query,update,User.class);

        }
        return 1;

    }


}
