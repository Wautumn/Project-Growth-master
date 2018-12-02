package com.org.growth.Service;

import com.org.growth.entity.Summary;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.util.Date;

import static org.junit.Assert.*;

public class DailySummaryServiceTest {

    private long userId;
    private String content;
    private Date time;

    @Resource
    private MongoTemplate mongoTemplate;
    @Autowired
    private DailySummaryService dailySummaryService;
    @Autowired
    Summary summary;

    @Before
    public void setUp() throws Exception {
        userId = 111;
        content = "Rua";
        time = new Date();
    }

    @After
    public void tearDown() throws Exception {
        Query query = Query.query(Criteria.where("time").is(time));
        mongoTemplate.remove(query, Summary.class);
    }

    @Test
    public void saveDailySummary() {
        if (dailySummaryService.saveSummary(userId, content, time,4))
            System.out.println("Test succeed");
        else
            System.out.println("Test failed");
    }
}