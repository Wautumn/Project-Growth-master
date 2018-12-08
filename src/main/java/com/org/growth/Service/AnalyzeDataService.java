package com.org.growth.Service;

import com.org.growth.DAO.AnalyzeDataDAO;
import com.org.growth.entity.AnalyzedataBean;
import com.org.growth.entity.History;
import com.org.growth.entity.Summary;
import com.org.growth.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AccumulatorOperators;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;

@Component
public class AnalyzeDataService implements AnalyzeDataDAO {
    @Autowired
    MongoTemplate mongoTemplate;

    public boolean isExistHistoryEnough(long userId){
       Query query=new BasicQuery("{}").with(new Sort(new Sort.Order(Sort.Direction.ASC,"starttime"))).limit(1);
       History history=mongoTemplate.findOne(query,History.class);
       Date earliestDate=history.getStartTime();
       Date current=new Date();
       Calendar currentcal = Calendar.getInstance();
       int currentmonth = currentcal.get(Calendar.MONTH )+1;

        Calendar earliestcalendar = Calendar.getInstance();
        earliestcalendar.setTime(earliestDate);
        int earliestmonth=earliestcalendar.get(Calendar.MONTH)+1;

        int month=currentmonth-earliestmonth;

        if(month<3)
            return false;
        else
            return true;

    }

    /*
    返回三个月的这个数据结构AnalyzedataBean
     */
    @Override
    public List<AnalyzedataBean> getCompletedData(long userId){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c=Calendar.getInstance();
        List<AnalyzedataBean> analyzedataBeans=new LinkedList<>();//用链表类型好一点

        Date current=new Date();
        c.setTime(new Date());
        c.add(Calendar.MONTH,-3);
        Date m=c.getTime();//前三个月的Date

        long count=current.getTime()-m.getTime();//总天数
        for(int i=0;i<count;i++){
           // AnalyzedataBean analyzedataBean=new AnalyzedataBean();
            Calendar c2=Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DAY_OF_MONTH,-i);
            Date time=c.getTime();//日期
            int tomatoCount=getSomedayTomato(time);
            int taskCount=getSomedayTask(time);
            int level=getSomedaySelfEvaluation(time);
            AnalyzedataBean analyzedataBean=new AnalyzedataBean(tomatoCount,taskCount,time,level);
            analyzedataBeans.add(analyzedataBean);

        }
        return analyzedataBeans;
    }

    @Override
    public List<AnalyzedataBean> getAllCompletedData(long userId){
        List<AnalyzedataBean> analyzedataBeans=new LinkedList<>();//用链表类型好一点
        Query query=new BasicQuery("{}").with(new Sort(new Sort.Order(Sort.Direction.ASC,"starttime"))).limit(1);
        History history=mongoTemplate.findOne(query,History.class);
        Date earliestDate=history.getStartTime();
        Date dBegin = new Date();
        Date dEnd = earliestDate;
        List<Date> Dates = findDates(dBegin, dEnd);
        for (Date date : Dates){
            int tomatoCount=getSomedayTomato(date);
            int taskCount=getSomedayTask(date);
            int level=getSomedaySelfEvaluation(date);
            AnalyzedataBean analyzedataBean=new AnalyzedataBean(tomatoCount,taskCount,date,level);
            analyzedataBeans.add(analyzedataBean);
        }
        return analyzedataBeans;

    }


    /*
    某天完成的番茄数
     */
    public int getSomedayTomato(Date date){
        Query query=Query.query(Criteria.where("starttime").is(date));//开始时间是这个日期的查询
        query.addCriteria(Criteria.where("status").is(1));//正常结束的
        List<History> histories=mongoTemplate.find(query,History.class);//某日期正常结束的历史记录
        int completedTomoto=histories.size();
        return completedTomoto;
    }

    /*
    某天的自我评价
     */
    public int getSomedaySelfEvaluation(Date date){
        Query query=Query.query(Criteria.where("time").is(date));
        Summary summary=mongoTemplate.findOne(query, Summary.class);
        int level=summary.getSelfRating();//百分制
        return level;
    }

    /*
    某天设置的任务数
     */
    public int getSomedayTask(Date date){
        Query query=Query.query(Criteria.where("setTime").is(date));
        List<Task> tasks=mongoTemplate.find(query,Task.class);
        return  tasks.size();
    }

    /*
    两个日期之间的所有天数
     */
    public static List<Date> findDates(Date dBegin, Date dEnd){

        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()))
        {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }


}