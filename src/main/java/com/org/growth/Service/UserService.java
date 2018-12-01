package com.org.growth.Service;


import com.org.growth.DAO.UserDAO;
import com.org.growth.entity.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;


@Component
public class UserService implements UserDAO {
    @Resource
    private MongoTemplate mongoTemplate;

    /*
    得到一周的番茄数
     */
    @Override
    public int getTomatoWeeklyCount(Long userId){
        Query query=Query.query(Criteria.where("id").is(userId));
        User user=mongoTemplate.findOne(query, User.class);
        int count=user.getTomatoweekly();
        return count;
    }


}
