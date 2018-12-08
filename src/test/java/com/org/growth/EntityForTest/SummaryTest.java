package com.org.growth.EntityForTest;

import com.org.growth.entity.Summary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SummaryTest {
    public static Summary summaryTest1;
    public static Summary summaryTest2;
    static {
        summaryTest1.setUserId(100L);
        summaryTest1.setContent("TEST");
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String input = "2018-11-10";
        Date t = null;
        try {
            t = ft.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        summaryTest1.setTime(t);
        summaryTest1.setSelfRating(8);
    }
}
