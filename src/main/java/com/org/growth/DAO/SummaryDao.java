package com.org.growth.DAO;

import java.util.Date;

public interface SummaryDao {
    boolean saveSummary(long userid, String content, Date time, int selfRating);
}
