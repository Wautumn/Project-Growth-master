package com.org.growth.EntityForTest;

import com.org.growth.entity.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskTest {
    public static Task task1;
    public static Task task2;
    static {
        //initial task1
        task1.setId(100L);
        task1.setUserId(100L);
        task1.setName("TEST");
        task1.setDescription("TEST");
        task1.setExpectedTomato(3);
        task1.setTomatoCompleted(1);

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String input = "2019-10-10";
        Date t = null;
        try {
            t = ft.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        task1.setDeadline(t);
        input = "2019-05-05";
        try {
            t = ft.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        task1.setRemindTime(t);
        task1.setFinishedTime(null);
        task1.setStatus(1);
        //initial task2
        task2.setId(101L);
        task2.setUserId(100L);
        task2.setName("TEST1");
        task2.setDescription("TEST1");
        task2.setExpectedTomato(4);
        task2.setTomatoCompleted(0);

        input = "2019-01-01";
        try {
            t = ft.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        task2.setDeadline(t);
        input = "2018-12-29";
        try {
            t = ft.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        task2.setRemindTime(t);
        task2.setFinishedTime(t);
        task2.setStatus(2);
    }
}
