package com.org.growth.Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TomatoServiceTest {
    @Autowired
    TomatoService tomatoService;

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