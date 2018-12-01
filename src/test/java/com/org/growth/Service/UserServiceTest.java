package com.org.growth.Service;
import com.org.growth.entity.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import javax.annotation.Resource;

import static org.junit.Assert.*;

public class UserServiceTest {

    private long userId;
    private int tomatoLength;
    private String music;

    @Resource
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    User user;

    @Before
    public void setUp() throws Exception {
        userId = 111;
        tomatoLength = 30;
        music = "1";
    }

    @After
    public void tearDown() throws Exception {
        Query query = Query.query(Criteria.where("userId").is(userId));
        mongoTemplate.remove(query, User.class);
    }

    @Test
    public void getTomatoWeeklyCount() {
    }

    @Test
    public void tomatoCountReduceOne() {
    }

    @Test
    public void changeTomatoLength() {
        if( userService.changeTomatoLength(111L, 50) )
            System.out.println("Test succeed");
        else
            System.out.println("Test fail");
    }

    @Test
    public void changeMusic() {
        if( userService.changeMusic(111L, "2") )
            System.out.println("Test succeed");
        else
            System.out.println("Test fail");
    }


}