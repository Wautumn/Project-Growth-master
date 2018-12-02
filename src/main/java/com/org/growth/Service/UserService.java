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
    private MongoTemplate mongoTemplate;

    /*
    登陆
     */
    @Override
    public boolean logIn(long UserId, String password){
        try{
            Query query = Query.query(Criteria.where("id").is(UserId));
            User user = mongoTemplate.findOne(query, User.class);
            String pass = user.getPassword();
            if( password.equals(pass) ){
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
    注册
     */
    @Override
    public long signUp(String username, String password, String email, String userFace, int tomatoLength, String music){
        try {
            User user = new User();

            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setUserface(userFace);
            user.setMusic(music);
            user.setTomatoLength(tomatoLength);

            Query query = Query.query(Criteria.where("id").exists(true));
            long cnt = mongoTemplate.count(query, User.class);
            user.setId( cnt+1 );

            mongoTemplate.insert(user);
            return user.getId();
        } catch (Exception e){
            return -1;
        }

    }

    /*
    修改昵称
     */
    @Override
    public boolean changeUsername(long UserId, String username){
        try{
            Query query = Query.query(Criteria.where("id").is(UserId));
            Update update= new Update().set("username", username);
            mongoTemplate.updateFirst(query, update, User.class);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /*
    修改密码
     */
    @Override
    public boolean changePassword(long UserId, String password){
        try{
            Query query = Query.query(Criteria.where("id").is(UserId));
            Update update= new Update().set("password", password);
            mongoTemplate.updateFirst(query, update, User.class);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /*
    修改邮箱
     */
    @Override
    public boolean changeEmail(long UserId, String email){
        try{
            Query query = Query.query(Criteria.where("id").is(UserId));
            Update update= new Update().set("email", email);
            mongoTemplate.updateFirst(query, update, User.class);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /*
    修改头像
     */
    @Override
    public boolean changeUserFace(long UserId, String userFace){
        try{
            Query query = Query.query(Criteria.where("id").is(UserId));
            Update update= new Update().set("userFace", userFace);
            mongoTemplate.updateFirst(query, update, User.class);
            return true;
        } catch (Exception e){
            return false;
        }
    }

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

    /*
    修改番茄时长
     */
    @Override
    public boolean changeTomatoLength(long userId, int tomatoLength){
        try{
            Query query = Query.query(Criteria.where("id").is(userId));
            Update update= new Update().set("tomatoLength", tomatoLength);
            mongoTemplate.updateFirst(query, update, User.class);
            return true;
        } catch (Exception e){
            return false;
        }

    }

    /*
    修改音乐
     */
    @Override
    public boolean changeMusic(long userId, String music) {
        try{
            Query query = Query.query(Criteria.where("id").is(userId));
            Update update= new Update().set("music", music);
            mongoTemplate.updateFirst(query, update, User.class);
            return true;
        } catch (Exception e){
            return false;
        }

    }

}
