package com.org.growth.DAO;

import com.org.growth.entity.History;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HistoryDao {
    Page<History> viewHistory(long userId, int size, int page);
    java.util.List<History> viewMonthHistory(long userId, String year);
    //Page<History> viewHistoryStatus(long userId, int size, int page, int status);

    boolean saveStartTomato(long userId);

    boolean saveBreakTomato(long userId);

    boolean saveEndTomato(long userId, boolean needAssociation);

    boolean saveEndTomato(long userId, boolean needAssociation, String taskName);

}
