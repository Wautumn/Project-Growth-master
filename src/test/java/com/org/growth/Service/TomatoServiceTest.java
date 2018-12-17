package com.org.growth.Service;

import com.org.growth.entity.TaskTree;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TomatoServiceTest {
    @Autowired
    TomatoService tomatoService;
@Resource
    MongoTemplate mongoTemplate;
    Date time;
    long userId;
    @Before
    public void setUp() throws Exception {
        userId = 100;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void viewHistory() {
        if (tomatoService.viewHistory(100,10,1) != null){
            System.out.println(tomatoService.viewHistory(100,10,1));
        }else {
            System.out.println("Test Failed");
        }
    }

    @Test
    public void viewMonthHistory() {
        if (tomatoService.viewMonthHistory(2,"2018") != null){
            System.out.println(tomatoService.viewMonthHistory(2,"2018"));
        }else {
            System.out.println("Test Failed");
        }
    }

    /*@Test
    public void viewHistoryStatus() {
        if (tomatoService.viewHistoryStatus(userId,10,1, -1) != null){
            System.out.println(tomatoService.viewHistoryStatus(userId,10,1, -1));
        }else {
            System.out.println("Test Failed");
        }
    }*/

    @Test
    public void myOwnTest() {
        Criteria criteria = new Criteria();
        criteria.and("parenttaskid").is(3);
        Query query = Query.query(criteria);
        Update update = new Update();
        update.set("sontaskname","fasd");
        mongoTemplate.updateFirst(query,update, TaskTree.class);
    }
}