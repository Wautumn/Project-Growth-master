package com.org.growth.DAO;

import com.org.growth.entity.History;
import java.util.List;

public interface HistoryDao {
    List<History> viewHistory(long userId);

    boolean saveStartTomato(long userId);

    boolean saveBreakTomato(long userId);

    boolean saveEndTomato(long userId, boolean needAssociation);

    boolean saveEndTomato(long userId, boolean needAssociation, String taskName);
    
}
