package com.org.growth.DAO;

import com.org.growth.entity.History;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface HistoryDao {
    Page<History> viewHistory(long userId, int size, int page);
    java.util.List<History> viewMonthHistory(long userId, String year);
    //Page<History> viewHistoryStatus(long userId, int size, int page, int status);

    boolean saveStartTomato(long userId, Date startTime);

    boolean saveBreakTomato(long userId,Date startTime,Date endTime);

    boolean saveEndTomato(long userId, boolean needAssociation,Date startTime,Date endTime);

    boolean saveEndTomato(long userId, boolean needAssociation, String taskName,Date startTime,Date endTime);

}
