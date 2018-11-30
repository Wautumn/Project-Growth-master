package com.org.growth.DAO;

import java.util.Date;

public interface HistoryDao {

    boolean saveStartTomato(long userId);

    boolean saveBreakTomato(long userId);
}
