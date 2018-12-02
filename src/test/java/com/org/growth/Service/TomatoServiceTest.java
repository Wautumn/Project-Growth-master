package com.org.growth.Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class TomatoServiceTest {
    @Autowired
    TomatoService tomatoService;

    Date time;
    long userId;
    @Before
    public void setUp() throws Exception {
        userId = 1;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void viewHistory() {
        if (tomatoService.viewHistory(userId,10,1) != null){
            System.out.println("Test succeed");
        }else {
            System.out.println("Test Failed");
        }
    }

    @Test
    public void saveStartTomato() {
        if (tomatoService.saveStartTomato(userId)){
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

    @Test
    public void saveEndTomato() {
        if(tomatoService.saveEndTomato(userId, false))
            System.out.println("Test Succeed");
        else
            System.out.println("Test Failed");
    }

    @Test
    public void saveEndTomato1() {
        if(tomatoService.saveEndTomato(userId, true,"Rua"))
            System.out.println("Test Succeed");
        else
            System.out.println("Test Failed");
    }
}