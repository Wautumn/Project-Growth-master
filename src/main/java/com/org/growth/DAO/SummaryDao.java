package com.org.growth.DAO;

import java.util.Date;

public interface SummaryDao {
    boolean save(long userid, String content, Date time);
}
