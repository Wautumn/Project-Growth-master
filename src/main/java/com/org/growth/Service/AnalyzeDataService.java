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


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;



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
       currentcal.setTime(new Date());
       int currentmonth = currentcal.get(Calendar.MONTH )+1;

        Calendar earliestcalendar = Calendar.getInstance();
        earliestcalendar.setTime(earliestDate);
        int earliestmonth=earliestcalendar.get(Calendar.MONTH)+1;

        int month=currentcal.get(Calendar.MONTH )-earliestcalendar.get(Calendar.MONTH);

        if(month<3)
            return false;//没有三个月的信息
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
        List<AnalyzedataBean> analyzedataBeans=null;//用链表类型好一点

        Date current=new Date();
        c.setTime(new Date());
        c.add(Calendar.MONTH,-3);
        Date m=c.getTime();//前三个月的Date

        long count=differentDaysByMillisecond(m,current);//总天数
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
    private int getSomedayTomato(Date date){
        Query query=Query.query(Criteria.where("starttime").is(date));//开始时间是这个日期的查询
        query.addCriteria(Criteria.where("status").is(1));//正常结束的
        List<History> histories=mongoTemplate.find(query,History.class);//某日期正常结束的历史记录
        int completedTomoto=histories.size();
        return completedTomoto;
    }

    /*
    某天的自我评价
     */
    private int getSomedaySelfEvaluation(Date date){
        Query query=Query.query(Criteria.where("time").is(date));
        Summary summary=mongoTemplate.findOne(query, Summary.class);
        int level=summary.getSelfRating();//百分制
        return level;
    }

    /*
    某天设置的任务数
     */
    private int getSomedayTask(Date date){
        Query query=Query.query(Criteria.where("setTime").is(date));
        List<Task> tasks=mongoTemplate.find(query,Task.class);
        return  tasks.size();
    }

    /*
    两个日期之间的所有天数
     */
    private static List<Date> findDates(Date dBegin, Date dEnd){

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

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    private static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    /*
    用完成番茄数来衡量平均的一周中 哪天完成的数量比较多，i代表是每周的第几天
     */
    public void getWeekdayData(){
        int Mon=0,Tues=0,Wed=0,Thur=0,Fri=0,Sat=0,Sun=0;

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);//当前时间的周一时间
        calendar.add(Calendar.DATE,-1);//上一周的周日
        Date endDate=calendar.getTime();

        Calendar calendar2=Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);//当前时间的周一时间
        calendar2.add(Calendar.DATE,-28);
        Date startDate=calendar2.getTime();//四周之前的周一时间

      //  System.out.println(simpleDateFormat.format(lateDate));
      //  System.out.println(simpleDateFormat.format(endDate));

        for(int z=0;z<4;z++) {
            for (int i = 0; i < 7; i++) {
                Calendar calendar3 = Calendar.getInstance();
                calendar3.setTime(startDate);
                calendar3.add(Calendar.DATE, z * 7 + i);
                Date nowDate = calendar3.getTime();

                System.out.println(simpleDateFormat.format(nowDate));

            //    Query query = Query.query(Criteria.where(simpleDateFormat.format("startTime")).is(nowDate));
          /*      Query query = Query.query(Criteria.where("startTime".).is(nowDate));
                query.addCriteria(Criteria.where("status").is(1));
                if (i == 0) Mon += mongoTemplate.find(query, History.class).size();
                else if (i == 1) Tues += mongoTemplate.find(query, History.class).size();
                else if (i == 2) Wed += mongoTemplate.find(query, History.class).size();
                else if (i == 3) Thur += mongoTemplate.find(query, History.class).size();
                else if (i == 4) Fri += mongoTemplate.find(query, History.class).size();
                else if (i == 5) Sat += mongoTemplate.find(query, History.class).size();
                else if (i == 6) Sun += mongoTemplate.find(query, History.class).size();*/
            }
        }



    }

    public  List<History> find(int userID){
        Query query=Query.query(Criteria.where("userId").is(userID));
      //  query.addCriteria(Criteria.where("status").is(1));
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        System.out.println(date.toString().substring(0,9));
        List<History> histories= mongoTemplate.find(query,History.class);
        List<History> histories2=new LinkedList<>();
        Date d=new Date();
        for(int i=0;i<histories.size();i++){
           if(simpleDateFormat.format(histories.get(i).getStartTime()).toString().substring(0,10).equals(d.toString()))
           {
               histories2.add(histories.get(i));
           }

        }
        return histories2;
    }




}