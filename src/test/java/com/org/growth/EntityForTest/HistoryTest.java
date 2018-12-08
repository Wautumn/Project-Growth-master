package com.org.growth.EntityForTest;

import com.org.growth.entity.History;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryTest {
    public static History historyTest1;
    static {
        historyTest1.setUserId(100L);
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String input = "2018-12-1-10-20-01";
        Date t = null;
        try {
            t = ft.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        historyTest1.setStartTime(t);
        input = "2018-12-1-10-40-01";
        try {
            t = ft.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        historyTest1.setEndTime(t);
        historyTest1.setTomatoLength(30);
        historyTest1.setStatus(-1);
        historyTest1.setTaskId(100L);
    }
}
