package com.org.growth.DAO;

import java.util.Date;

public interface HistoryDao {

    boolean saveStartTomato(long userId);

    boolean saveBreakTomato(long userId);

    boolean saveEndTomato(long userId, boolean needAssociation);

    boolean saveEndTomato(long userId, boolean needAssociation, String taskName);
}
