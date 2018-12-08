package com.org.growth.DAO;

import com.org.growth.entity.AnalyzedataBean;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AnalyzeDataDAO {
    /*
    获取近六个月的记录，需要的是每天完成的总番茄，每天任务数，日期，每日完成度，用一个数据结构存储
     */
    List<AnalyzedataBean> getCompletedData(long userId);

    List<AnalyzedataBean> getAllCompletedData(long userId);


}
