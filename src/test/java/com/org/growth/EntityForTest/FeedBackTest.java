package com.org.growth.EntityForTest;

import com.org.growth.entity.Feedback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FeedBackTest {
    public static Feedback feedbackTest1;
    static {
        feedbackTest1.setUserid(100L);
        feedbackTest1.setContent("TEST");
        feedbackTest1.setAnswer("TEST");

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String input = "2018-10-10";
        Date t = null;
        try {
            t = ft.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        feedbackTest1.setTime(t);
    }
}
