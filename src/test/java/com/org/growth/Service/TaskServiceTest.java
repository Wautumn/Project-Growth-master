package com.org.growth.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {

    @Autowired
    TaskService taskService;
    @Test
    public void addTask() {
        List result = taskService.addTask(2,"aa好","hhhhhhhh",10, new Date(), new Date(),new Date());
        Iterator iterator = result.iterator();
        Object integer = iterator.next();
        System.out.println(integer);
        Integer i1=1;
        Integer i2=0;
        if((Integer)integer == i1)
            System.out.println("Test success");
        else if ((Integer)integer == i2)
            System.out.println("Task exists!");
        else
            System.out.println("Test Failed!");
    }

    @Test
    public void addSubTask() {
        int result = taskService.addSubTask(3, "rua");
        if(result == 1)
            System.out.println("Test success");
        else if (result == 0)
            System.out.println("Task exists!");
        else
            System.out.println("Test Failed!");
    }

    @Test
    public void startTask() {
        if (taskService.startTask(2, "你啊啊好", new Date())){
            System.out.println("Test Succeed");
        }
        else {
            System.out.println("Test Failed!");
        }
    }

    @Test
    public void breakTask() {
        if (taskService.breakTask(2, "aa好", new Date())){
            System.out.println("Test Succeed");
        }
        else {
            System.out.println("Test Failed!");
        }
    }

    @Test
    public void endTask() {
        if (taskService.endTask(8, "he", new Date())){
            System.out.println("Test Succeed");
        }
        else {
            System.out.println("Test Failed!");
        }
    }

    @Test
    public void queryTask() {
        List list = taskService.queryTask(2);
    }
}