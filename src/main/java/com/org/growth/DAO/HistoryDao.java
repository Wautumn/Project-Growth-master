package com.org.growth.DAO;

import java.util.Date;

public interface HistoryDao {

    boolean saveStartTomato(long userId, Date startTime);

    boolean saveBreakTomato(long userId);
}
