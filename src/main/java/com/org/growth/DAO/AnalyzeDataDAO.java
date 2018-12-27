package com.org.growth.DAO;

import com.org.growth.entity.useful.AnalyzedataBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public interface AnalyzeDataDAO {
    /*
    获取近六个月的记录，需要的是每天完成的总番茄，每天任务数，日期，每日完成度，用一个数据结构存储
     */
    List<AnalyzedataBean> getOneYearData(long userId,String localTime);


    List<AnalyzedataBean> getAllCompletedData(long userId)throws Exception;
   //void getAllCompletedData(long userId);

    AnalyzedataBean getDateData(long userId, LocalDate current)throws Exception;


}
