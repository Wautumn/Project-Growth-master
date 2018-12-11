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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class AnalyzeDataService implements AnalyzeDataDAO {
    @Autowired
    MongoTemplate mongoTemplate;

    public static LocalDate earlyData;

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

       /* Date current=new Date();
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

        }*/
        return analyzedataBeans;
    }

    /*
    从用户最早的历史记录开始所有的图表数据
     */
    @Override
    public List<AnalyzedataBean> getAllCompletedData(long userId){

        List<AnalyzedataBean> analyzedataBeans = new LinkedList<>();//用链表类型好一点
        Query query = new BasicQuery("{}").with(new Sort(new Sort.Order(Sort.Direction.ASC, "starttime"))).limit(1);
        History history = mongoTemplate.findOne(query, History.class);
        earlyData=DateToLocalDate(history.getStartTime());
       // System.out.println(DateToLocalDate(history.getStartTime())+","+history.getTaskId());//最早的历史记录的时间
        LocalDate startTime=DateToLocalDate(history.getStartTime());
        LocalDate nowTime=LocalDate.now();
        long between= ChronoUnit.DAYS.between(startTime,nowTime);
        int daycliff=(int) between;
        for(int i=0;i<daycliff;i++)
        {
            LocalDate cur=startTime.plusDays(i);
            AnalyzedataBean analyzedataBean=new AnalyzedataBean(0,0,cur,0);
            analyzedataBean = getDateData(userId, cur);
            analyzedataBeans.add(analyzedataBean);
        }
        return analyzedataBeans;
    }


    /*
    某天的分析
     */
    @Override
    public AnalyzedataBean getDateData(long userId, LocalDate current){
        Query query=Query.query(Criteria.where("userId").is(userId));

        List<History> histories=mongoTemplate.find(query,History.class);
        List<Task> tasks=mongoTemplate.find(query,Task.class);
        List<Summary> summaries=mongoTemplate.find(query,Summary.class);

        List<History> dateHistory=new LinkedList<>();
        List<Task> dateTask=new LinkedList<>();

        if(histories!=null&&histories.size()>0) dateHistory=getOneDayHistory(histories,current);//获取当前用户某一天的记录数
        if(tasks!=null&&tasks.size()>0)    dateTask=getOneDayTask(tasks,current);

        int tomatocount=0;
        int taskCount=0;
        int level=0;

        AnalyzedataBean analyzedataBean=new AnalyzedataBean(0,0,current,100);
        if(dateHistory!=null&&dateHistory.size()>0) {
            for (int i = 0; i < dateHistory.size(); i++) {
                if (dateHistory.get(i).getStatus() == 2)
                    tomatocount++;
            }
        }else{
            tomatocount=0;
        }

        taskCount=dateTask.size();
        if(summaries!=null&&summaries.size()>0) level=getOneDayLevel(summaries,current);

        analyzedataBean.setDate(current);
        analyzedataBean.setTaskCount(taskCount);
        analyzedataBean.setTomatocount(tomatocount);
        analyzedataBean.setLevel(level);
        return analyzedataBean;

    }

    private List<History> getOneDayHistory(List<History> histories,LocalDate day){
        List<History> dayHistories=new LinkedList<>();
        if(histories!=null&&histories.size()>0) {
            for (int i = 0; i < histories.size(); i++) {
                Date starttime = histories.get(i).getStartTime();
                LocalDate localDate = DateToLocalDate(starttime);
                if (localDate.equals(day)) {
                       History history = histories.get(i);
                       dayHistories.add(history);

                }

            }
        }
        return dayHistories;
    }

    private List<Task> getOneDayTask(List<Task> tasks,LocalDate day){
        List<Task> dayTasks=new ArrayList<>();
        LocalDate localDate;
        if(tasks!=null&&tasks.size()>0) {
            for (int i = 0; i < tasks.size(); i++) {
                Date starttime = tasks.get(i).getSetTime();
                if(starttime!=null) {
                    localDate = DateToLocalDate(starttime);
                    if (localDate.equals(day)) {
                       // System.out.println("d");
                        Task task=tasks.get(i);
                      //  System.out.println(task.getName()+"name");
                        dayTasks.add(task);

                    }
                }

            }
        }
        return dayTasks;
    }

    private int getOneDayLevel(List<Summary> summary,LocalDate day) {
        List<Summary> summaries = new LinkedList<>();
        for (int i = 0; i < summary.size(); i++) {
            Date time = summary.get(i).getTime();
            LocalDate localDate = DateToLocalDate(time);
            if (localDate.equals(day)) {
                summaries.add(summary.get(i));
            }

        }
        return summaries.get(0).getSelfRating();
    }

    public LocalDate DateToLocalDate(Date date){

            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate localDate = instant.atZone(zoneId).toLocalDate();//Date转化为LocalDate
            return localDate;
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