package com.org.growth.Event;

import com.org.growth.entity.User;
import org.springframework.context.ApplicationEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;

public class NextWeekEvent extends ApplicationEvent {
    @Resource
    MongoTemplate mongoTemplate;

    public NextWeekEvent(Object source){
        super(source);
    }

    public void printMsg(String msg){
        Update update = new Update();
        update.set("tomatoWeekly",0);
        mongoTemplate.updateMulti(new Query(),update, User.class);
    }
}
