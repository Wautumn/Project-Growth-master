package com.org.growth.Service;

import com.org.growth.DAO.UserDAO;
import com.org.growth.entity.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class UserService implements UserDAO {
    @Resource
    private  MongoTemplate mongoTemplate1;

    private static MongoTemplate mongoTemplate;

    /*
    登陆
     */

    @PostConstruct
    void init(){
        mongoTemplate = mongoTemplate1;
    }
    @Override
    public long logIn(String username, String password){
        mongoTemplate = mongoTemplate1;
        try{
            Query query = Query.query(Criteria.where("username").is(username));
            User user = mongoTemplate.findOne(query, User.class);
            String pass = user.getPassword();
            if( password.equals(pass) ){
                return user.getId();
            }
            else{
                return -1;
            }
        } catch (Exception e){
            return -1;
        }

    }

    /*
    注册
     */

    @Override
    public long signUp(String username, String password, String email, String userFace, int tomatoLength, String music){
        mongoTemplate = mongoTemplate1;
        try {
            Query old = Query.query(Criteria.where("username").is(username));
            User exist= mongoTemplate.findOne(old, User.class);
            if( exist != null){
                return -1;
            }
            else{
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                user.setUserface(userFace);
                user.setTomatoLength(tomatoLength);
                //user.setMusic(music);

                Query query = Query.query(Criteria.where("id").exists(true));
                long cnt = mongoTemplate.count(query, User.class);
                user.setId( cnt+1 );

                mongoTemplate.insert(user);
                return user.getId();
            }
        } catch (Exception e){
              return -1;
        }

    }

    /*
    修改昵称
     */

    @Override
    public String changeUsername(long UserId, String username){
        mongoTemplate = mongoTemplate1;
        try{
            Query query = Query.query(Criteria.where("id").is(UserId));
            Update update= new Update().set("username", username);
            mongoTemplate.updateFirst(query, update, User.class);
            return username;
        } catch (Exception e){
            return "error";
        }
    }

    /*
    修改密码
     */

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword){
        mongoTemplate = mongoTemplate1;
        try{
            Query query = Query.query(Criteria.where("username").is(username));
            User user = mongoTemplate.findOne(query, User.class);
            if( user.getPassword().equals(oldPassword) ){
                Update update= new Update().set("password", newPassword);
                mongoTemplate.updateFirst(query, update, User.class);
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e){
            return false;
        }
    }

    /*
    修改邮箱
     */

    @Override
    public String changeEmail(long UserId, String email){
        mongoTemplate = mongoTemplate1;
        try{
            Query query = Query.query(Criteria.where("id").is(UserId));
            Update update= new Update().set("email", email);
            mongoTemplate.updateFirst(query, update, User.class);
            return email;
        } catch (Exception e){
            return "error";
        }
    }

    /*
    修改头像
     */

    @Override
    public String changeUserFace(long UserId, String userFace){
        mongoTemplate = mongoTemplate1;
        try{
            Query query = Query.query(Criteria.where("id").is(UserId));
            Update update= new Update().set("userFace", userFace);
            mongoTemplate.updateFirst(query, update, User.class);
            return userFace;
        } catch (Exception e){
            return "error";
        }
    }

    /*
    得到一周的番茄数
     */

    static User findById(long userId){
        Criteria criteria = new Criteria();
        criteria.and("id").is(userId);
        Query query = Query.query(criteria);
        return  mongoTemplate.findOne(query,User.class);
    }

    public User findByUserId(long userId){
        Criteria criteria = new Criteria();
        criteria.and("id").is(userId);
        Query query = Query.query(criteria);
        return  mongoTemplate.findOne(query,User.class);
    }

    @Override
    public int getTomatoWeeklyCount(Long userId){
        //mongoTemplate = mongoTemplate1;
        Query query=Query.query(Criteria.where("id").is(userId));
        User user=mongoTemplate.findOne(query, User.class);
        int count=user.getTomatoweekly();
        return count;
    }

    /*
    分享番茄，周番茄数量减一
     */

    public int TomatoCountReduceOne(Long userId){
        mongoTemplate = mongoTemplate1;
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

    /*
    修改番茄时长
     */
    @Override

    public int changeTomatoLength(long userId, int tomatoLength){
        mongoTemplate = mongoTemplate1;
        try{
            Query query = Query.query(Criteria.where("id").is(userId));
            Update update= new Update().set("tomatoLength", tomatoLength);
            mongoTemplate.updateFirst(query, update, User.class);
            return tomatoLength;
        } catch (Exception e){
            return -1;
        }

    }

    /*
    修改音乐
    @Override
    public boolean changeMusic(long userId, String music) {
        mongoTemplate = mongoTemplate1;
        try{
            Query query = Query.query(Criteria.where("id").is(userId));
            Update update= new Update().set("music", music);
            mongoTemplate.updateFirst(query, update, User.class);
            return true;
        } catch (Exception e){
            return false;
        }

    }
    */

}
