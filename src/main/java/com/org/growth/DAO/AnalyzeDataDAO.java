package com.org.growth.DAO;

import com.org.growth.entity.useful.AnalyzedataBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public interface AnalyzeDataDAO {
    /*
    get records of recent 6 months
    attributes needed:
    tomato daily, task daily, date, completion
    build a data structure to store it
     */
    List<AnalyzedataBean> getOneYearData(long userId,String localTime);


    List<AnalyzedataBean> getAllCompletedData(long userId)throws Exception;
   //void getAllCompletedData(long userId);

    AnalyzedataBean getDateData(long userId, LocalDate current)throws Exception;


}
