package com.org.growth.Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

public class TomatoServiceTest {
    @Autowired
    TomatoService tomatoService;

    Date time;
    long userId;
    @Before
    public void setUp() throws Exception {
        time = new Date();
        userId = 1;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void saveStartTomato() {
        if ( tomatoService.saveStartTomato(userId, time)){
            System.out.println("Test succeed");
        }else {
            System.out.println("Test Failed");
        }
    }

    @Test
    public void saveBreakTomato() {
        if(tomatoService.saveBreakTomato(userId))
            System.out.println("Test Succeed");
        else
            System.out.println("Test Failed");
    }
}