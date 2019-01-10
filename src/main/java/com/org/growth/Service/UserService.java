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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;





@Component
public class
UserService implements UserDAO {
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
    public List logIn(String username, String password){
        mongoTemplate = mongoTemplate1;
        List list = new ArrayList();
        try{
            Query query = Query.query(Criteria.where("username").is(username));
            User user = mongoTemplate.findOne(query, User.class);

            String encoded = user.getPassword();
            final Base64.Decoder decoder = Base64.getDecoder();
            String pass = new String(decoder.decode(encoded), "UTF-8");

            if( password.equals(pass) ){

                list.add(user.getId().toString());
                list.add(user.getUsername());
                list.add(user.getEmail());
                list.add(user.getUserface());
                String tomatoLength = String.valueOf(user.getTomatoLength());
                list.add(tomatoLength);
                String dayGoal = String.valueOf(user.getDayGoal());
                list.add(dayGoal);
                String weekGoal = String.valueOf(user.getWeekGoal());
                list.add(weekGoal);
                String monthGoal = String.valueOf(user.getMonthGoal());
                list.add(monthGoal);

                return list;
            }
            else{
                list.add("Wrong password!");
                return list;
            }
        } catch (Exception e){
            list.add("No user!");
            return list;
        }
    }

    /*
    注册
     */

    @Override
    public long signUp(String username, String password, String email){
        mongoTemplate = mongoTemplate1;
        try {
            Query old = Query.query(Criteria.where("username").is(username));
            User exist= mongoTemplate.findOne(old, User.class);
            if( exist != null){
                return -1;
            }
            else{

                final Base64.Encoder encoder = Base64.getEncoder(); //加密
                final String text = password;
                final byte[] textByte = text.getBytes("UTF-8");
                final String encoded = encoder.encodeToString(textByte);

                User user = new User();
                user.setUsername(username);
                user.setPassword(encoded);
                user.setEmail(email);
                user.setUserface(null);
                user.setTomatoLength(30);
                user.setDayGoal(0);
                user.setWeekGoal(0);
                user.setMonthGoal(0);
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
        Query old = Query.query(Criteria.where("username").is(username));
        User exist= mongoTemplate.findOne(old, User.class);
        if( exist != null){
            return "User exist! Please change another username.";
        }
        else {
            try {
                Query query = Query.query(Criteria.where("id").is(UserId));
                Update update = new Update().set("username", username);
                mongoTemplate.updateFirst(query, update, User.class);
                return username;
            } catch (Exception e) {
                return "error";
            }
        }
    }

    /*
    修改密码
     */

    @Override
    public boolean changePassword(long userId, String oldPassword, String newPassword){
        mongoTemplate = mongoTemplate1;
        try{
            Query query = Query.query(Criteria.where("id").is(userId));
            User user = mongoTemplate.findOne(query,User.class);

            String encoded = user.getPassword();
            final Base64.Decoder decoder = Base64.getDecoder();
            String pass = new String(decoder.decode(encoded), "UTF-8");

            if( pass.equals(oldPassword) ){

                final Base64.Encoder encoder = Base64.getEncoder(); //加密
                final String text = newPassword;
                final byte[] textByte = text.getBytes("UTF-8");
                final String result = encoder.encodeToString(textByte);

                Update update= new Update().set("password", result);
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
 /*
    得到一周的番茄数
     */

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
    修改番茄日目标
     */
    @Override
    public int changeDayGoal(long userId, int dayGoal){
        mongoTemplate = mongoTemplate1;
        try{
            Query query = Query.query(Criteria.where("id").is(userId));
            Update update= new Update().set("dayGoal", dayGoal);
            mongoTemplate.updateFirst(query, update, User.class);
            return dayGoal;
        } catch (Exception e){
            return -1;
        }

    }

    /*
    修改番茄周目标
     */
    @Override
    public int changeWeekGoal(long userId, int weekGoal){
        mongoTemplate = mongoTemplate1;
        try{
            Query query = Query.query(Criteria.where("id").is(userId));
            Update update= new Update().set("weekGoal", weekGoal);
            mongoTemplate.updateFirst(query, update, User.class);
            return weekGoal;
        } catch (Exception e){
            return -1;
        }

    }

    /*
    修改番茄月目标
     */
    @Override
    public int changeMonthGoal(long userId, int monthGoal){
        mongoTemplate = mongoTemplate1;
        try{
            Query query = Query.query(Criteria.where("id").is(userId));
            Update update= new Update().set("monthGoal", monthGoal);
            mongoTemplate.updateFirst(query, update, User.class);
            return monthGoal;
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
