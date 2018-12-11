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
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
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
    用完成番茄数来衡量平均的一周中 哪天完成的数量比较多，前四周的数据
     */
    public void getWeekdayData(long userId){
        int Mon=0,Tues=0,Wed=0,Thur=0,Fri=0,Sat=0,Sun=0;

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

        Query query=Query.query(Criteria.where("userId").is(userId));
        List<History>histories=mongoTemplate.find(query,History.class);//获取所有历史记录
        System.out.println("size his"+histories.size());

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

        for(int i=0;i<histories.size();++i) {
            DayOfWeek cur = DateToLocalDate(histories.get(i).getStartTime()).getDayOfWeek();
            System.out.println(cur.toString() + "星期几");
            if (cur.getValue() == 1) Mon += mongoTemplate.find(query, History.class).size();
            else if (cur.getValue() == 2) Tues += mongoTemplate.find(query, History.class).size();
            else if (cur.getValue() == 3) Wed += mongoTemplate.find(query, History.class).size();
            else if (cur.getValue() == 4) Thur += mongoTemplate.find(query, History.class).size();
            else if (cur.getValue() == 5) Fri += mongoTemplate.find(query, History.class).size();
            else if (cur.getValue() == 6) Sat += mongoTemplate.find(query, History.class).size();
            else if (cur.getValue() == 7) Sun += mongoTemplate.find(query, History.class).size();
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