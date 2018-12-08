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
        if( (userService.signUp("newUser", "newPass", "newEmail", "newFace", 32, "1" ) > 0) )
            System.out.println("Test succeed");
        else
            System.out.println("Test fail");
    }

    @Test
    public void logIn() {
        if( userService.logIn(111L, "pass") )
            System.out.println("Test succeed");
        else
            System.out.println("Test fail");
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

    @Test
    public void changeUsername() {
        if( userService.changeUsername(111L, "zzz") )
            System.out.println("Test succeed");
        else
            System.out.println("Test fail");
    }

    @Test
    public void changePassword() {
        if( userService.changePassword(111L, "word") )
            System.out.println("Test succeed");
        else
            System.out.println("Test fail");
    }

    @Test
    public void changeEmail() {
        if( userService.changeEmail(111L, "emailAfter") )
            System.out.println("Test succeed");
        else
            System.out.println("Test fail");
    }

    @Test
    public void changeUserFace() {
        if( userService.changeUserFace(111L, "faceAfter") )
            System.out.println("Test succeed");
        else
            System.out.println("Test fail");
    }

}