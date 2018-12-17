package com.org.growth.DAO;

import java.util.Date;
import java.util.List;

public interface SummaryDao {

    boolean saveSummary(long userId, String content, Date time, double selfRating);
    //return status and object
    List readSummary(long userId, Date time);

    boolean modifySummary(long userId, String content, Date time, int selfRating);

    List querySummaryByYear(long userId, String year);
}
