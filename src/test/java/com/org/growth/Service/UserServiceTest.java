package com.org.growth.Service;
import com.org.growth.entity.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    private long userId;
    private int tomatoLength;
    private String music;
    private String username;
    private String password;
    private String email;
    private String userFace;

    @Resource
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserService userService;
    User user;

    @Before
    public void setUp() throws Exception {
        userId = 111L;
        tomatoLength = 30;
        music = "0";
        username = "yyy";
        password = "pass";
        email = "emailBefore";
        userFace = "faceBefore";
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
    public void signUp() {
        if( (userService.signUp("1234567", "1234567", "email1234567" ) > 0) )
            System.out.println("Test succeed");
        else
            System.out.println("Test fail");
    }

    @Test
    public void logIn() {

            System.out.println(userService.logIn("1234567", "1234567"));

    }

    @Test
    public void changeTomatoLength() {
        if( userService.changeTomatoLength(8L, 30) != -1 )
            System.out.println("Test succeed");
        else
            System.out.println("Test fail");
    }

    @Test
    public void changeDayGoal() {
        if( userService.changeDayGoal(11L, 2) != -1 )
            System.out.println("Test succeed");
        else
            System.out.println("Test fail");
    }

    @Test
    public void changeWeekGoal() {
        if( userService.changeWeekGoal(11L, 5) != -1 )
            System.out.println("Test succeed");
        else
            System.out.println("Test fail");
    }

    @Test
    public void changeMonthGoal() {
        if( userService.changeMonthGoal(11L, 8) != -1 )
            System.out.println("Test succeed");
        else
            System.out.println("Test fail");
    }


    /*
    @Test
    public void changeMusic() {
        if( userService.changeMusic(111L, "2") )
            System.out.println("Test succeed");
        else
            System.out.println("Test fail");
    }
    */

    @Test
    public void changeUsername() {
        if( userService.changeUsername(8, "123").equals("error") )
            System.out.println("Test fail");
        else
            System.out.println("Test succeed");

    }

    @Test
    public void changePassword() {
        if( userService.changePassword(8, "word2", "123") )
            System.out.println("Test succeed");
        else
            System.out.println("Test fail");
    }

    @Test
    public void changeEmail() {
        if( userService.changeEmail(8, "emailAfter").equals("error") )
            System.out.println("Test fail");
        else
            System.out.println("Test succeed");

    }

    @Test
    public void changeUserFace() {
        if( userService.changeUserFace(8, "faceChanged").equals("error") )
            System.out.println("Test fail");
        else
            System.out.println("Test succeed");

    }

}