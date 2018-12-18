package com.org.growth.Service;

import com.org.growth.DAO.AnalyzeDataDAO;
import com.org.growth.entity.newData;
import com.org.growth.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class AnalyzeDataService implements AnalyzeDataDAO {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserService userService=new UserService();


    public String weekday="无";
    public String daytime="";

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getDaytime() {
        return daytime;
    }

    public void setDaytime(String daytime) {
        this.daytime = daytime;
    }

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
    返回给定日期一年的数据
     */
    @Override
    public List<AnalyzedataBean> getOneYearData(long userId,String localTime){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate curdate = LocalDate.parse(localTime,formatter);//当前日期
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        List<AnalyzedataBean> analyzedataBeans=new LinkedList<>();//用链表类型好一点
        if(userService.findByUserId(userId)==null)
        {
            System.out.println("null");
            return analyzedataBeans;
        }
        LocalDate end=curdate.plusYears(1);
        LocalDate earlytime=getEarlyTime(userId);
        if(earlytime==null) {
            System.out.println("null time");
            return analyzedataBeans;
        }
        System.out.println("start"+earlytime.toString());
        if(earlytime.isAfter(end)){//这一年没有数据
            System.out.println("this year");
            return analyzedataBeans;
        }
        else if(earlytime.isBefore(end)&&earlytime.isAfter(curdate)){//中间
            long diff=ChronoUnit.DAYS.between(earlytime, end);
            int bet=(int) diff;
            for(int i=0;i<bet;++i){
                AnalyzedataBean analyzedataBean=getDateData(userId,earlytime.plusDays(i));
                if(analyzedataBean.getTaskCount()==0&&analyzedataBean.getTomatocount()==0) continue;
                analyzedataBeans.add(analyzedataBean);
            }
            System.out.println("1");

           // return analyzedataBeans;
        }

        else {//之前
            long diff = ChronoUnit.DAYS.between(curdate, end);
            int bet = (int) diff;

            for (int i = 0; i < bet; ++i) {
                AnalyzedataBean analyzedataBean = getDateData(userId, curdate.plusDays(i));
                analyzedataBeans.add(analyzedataBean);
                System.out.println("2");
            }
        }
        return analyzedataBeans;
    }

    /*
    从用户最早的历史记录开始所有的图表数据
     */

    public LocalDate getEarlyTime(long Id){
       // Query query= Query.query(Criteria.where("userId").is(Id));
        if(userService.findByUserId(Id)==null) return null;
        History history=new History();
        while (history.getStartTime()==null)
        {
           Query query = new BasicQuery("{}").with(new Sort(new Sort.Order(Sort.Direction.ASC, "starttime", Sort.NullHandling.NULLS_LAST))).limit(1);
           query.addCriteria(Criteria.where("userId").is(Id));
           history = mongoTemplate.findOne(query, History.class);
           if(history==null) break;
           if(history.getStartTime()==null)
               mongoTemplate.remove(history);
       }
        LocalDate earlyData;
        if(history==null) return null;
        earlyData=DateToLocalDate(history.getStartTime());
        return earlyData;
    }
    @Override
    public List<AnalyzedataBean> getAllCompletedData(long userId){

        List<AnalyzedataBean> analyzedataBeans = new LinkedList<>();//用链表类型好一点
       /* Query query = new BasicQuery("{}").with(new Sort(new Sort.Order(Sort.Direction.ASC, "starttime"))).limit(1);
        History history = mongoTemplate.findOne(query, History.class);
        LocalDate earlyData;
        earlyData=DateToLocalDate(history.getStartTime());*/
       // System.out.println(DateToLocalDate(history.getStartTime())+","+history.getTaskId());//最早的历史记录的时间
        LocalDate startTime=getEarlyTime(userId);
        LocalDate nowTime=LocalDate.now();
        long between= ChronoUnit.DAYS.between(startTime,nowTime);
        int daycliff=(int) between;
        for(int i=0;i<daycliff;i++)
        {
            LocalDate cur=startTime.plusDays(i);
            AnalyzedataBean analyzedataBean=new AnalyzedataBean(0,0,cur.toString());
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


        List<History> dateHistory=new LinkedList<>();
        List<Task> dateTask=new LinkedList<>();

        if(histories!=null&&histories.size()>0) dateHistory=getOneDayHistory(histories,current);//获取当前用户某一天的记录数
        if(tasks!=null&&tasks.size()>0)    dateTask=getOneDayTask(tasks,current);

        int tomatocount=0;
        int taskCount=0;

        AnalyzedataBean analyzedataBean=new AnalyzedataBean(0,0,current.toString());
        if(dateHistory!=null&&dateHistory.size()>0) {
            for (int i = 0; i < dateHistory.size(); i++) {
                if (dateHistory.get(i).getStatus() == 1)
                    tomatocount++;
            }
        }else{
            tomatocount=0;
        }

        taskCount=dateTask.size();


        analyzedataBean.setDate(current.toString());
        analyzedataBean.setTaskCount(taskCount);
        analyzedataBean.setTomatocount(tomatocount);

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


    public LocalDate DateToLocalDate(Date date) {
        LocalDate localDate0=LocalDate.of(2018,01,01);
        if(date==null) return null;
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();//Date转化为LocalDate
        return localDate;
    }

    public LocalTime DateToLocalTime(Date date){
        if(date==null) return null;
        Instant instant=date.toInstant();
        ZoneId zoneId=ZoneId.systemDefault();
        LocalTime localTime=instant.atZone(zoneId).toLocalTime();
        return localTime;
    }


    /*
    用完成番茄数来衡量平均的一周中 哪天完成的数量比较多，前四周的数据
     */
    public List<newData> getWeekdayData(long userId){
        int Mon=0,Tues=0,Wed=0,Thur=0,Fri=0,Sat=0,Sun=0;
        int tMon=0,tTues=0,tWed=0,tThur=0,tFri=0,tSat=0,tSun=0;
        List<newData> newDataList=new LinkedList<>();
        List<Integer> day=new ArrayList<>();
        Map<String,Object> map=new LinkedHashMap<>();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

        Query query=Query.query(Criteria.where("userId").is(userId));
        List<Task> tasks=mongoTemplate.find(query,Task.class);//所有的任务

        query.addCriteria(Criteria.where("status").is(1));
        List<History>histories=mongoTemplate.find(query,History.class);//获取所有完成的番茄
        System.out.println("size his"+histories.size());
        System.out.println("task size his"+tasks.size());

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
            System.out.println(cur.toString() + "星期几"+cur.getValue());
            if (cur.getValue() == 1) Mon ++;
            else if (cur.getValue() == 2) Tues ++;
            else if (cur.getValue() == 3) Wed ++;
            else if (cur.getValue() == 4) Thur ++;
            else if (cur.getValue() == 5) Fri ++;
            else if (cur.getValue() == 6) Sat ++;
            else if (cur.getValue() == 7) Sun ++;
        }

        for(int i=0;i<tasks.size();++i) {
            Date date=tasks.get(i).getSetTime();
            if(date==null) continue;
            DayOfWeek cur = DateToLocalDate(tasks.get(i).getSetTime()).getDayOfWeek();
           // System.out.println(cur.toString() + "星期几"+cur.getValue());
            if (cur.getValue() == 1) tMon ++;
            else if (cur.getValue() == 2) tTues ++;
            else if (cur.getValue() == 3) tWed ++;
            else if (cur.getValue() == 4) tThur ++;
            else if (cur.getValue() == 5) tFri ++;
            else if (cur.getValue() == 6) tSat ++;
            else if (cur.getValue() == 7) tSun ++;
        }
        day.add(Mon);
        day.add(Tues);
        day.add(Wed);
        day.add(Thur);
        day.add(Fri);
        day.add(Sat);
        day.add(Sun);

        newData Mondata=new newData("星期一",Mon,tMon);
        newData Tuesdata=new newData("星期二",Tues,tTues);
        newData Weddata=new newData("星期三",Wed,tWed);
        newData Thurdata=new newData("星期四",Thur,tThur);
        newData Fridata=new newData("星期五",Fri,tFri);
        newData Satdata=new newData("星期六",Sat,tSat);
        newData Sundata=new newData("星期日",Sun,tSun);
        System.out.println("dddddd"+Mondata.getWeekday());
        newDataList.add(Mondata);
        newDataList.add(Tuesdata);
        newDataList.add(Weddata);
        newDataList.add(Thurdata);
        newDataList.add(Fridata);
        newDataList.add(Satdata);
        newDataList.add(Sundata);
      /*  map.put("星期一",Mon);
        map.put("星期二",Tues);
        map.put("星期三",Wed);
        map.put("星期四",Thur);
        map.put("星期五",Fri);
        map.put("星期六",Sat);
        map.put("星期日",Sun);*/
        int max=Collections.max(day);
        int dd=0;
        for(int i=0;i<7;i++){
            if(day.get(i).equals(max))
               dd=i+1;
        }
        String ddd="";
        if(dd==1)ddd="星期一";
        else if(dd==2)ddd="星期二";
        else if(dd==3)ddd="星期三";
        else if(dd==5)ddd="星期五";
        else if(dd==6)ddd="星期六";
        else if(dd==7)ddd="星期日";

        weekday=ddd;//完成番茄数最多的天

      //  AnalyData analyData=new AnalyData(ddd,map);
        return newDataList;



}

    /*
    哪个时段的完成数较多
     */
    public List<newTimeData> getTimeData(long userId){
        Map<String,Object> map=new LinkedHashMap<>();
        List<Integer> time=new LinkedList<>();
        List<newTimeData> newTimeDataList=new LinkedList<>();
        LocalTime localTime0=LocalTime.of(0,0);
        LocalTime localTime1=LocalTime.of(7,0);
        LocalTime localTime2=LocalTime.of(11,0);
        LocalTime localTime3=LocalTime.of(13,5);
        LocalTime localTime4=LocalTime.of(18,10);
        int mor=0;
        int aft=0;
        int eve=0;
        Query query=Query.query(Criteria.where("userId").is(userId));
        query.addCriteria(Criteria.where("status").is(1));
        List<History>histories=mongoTemplate.find(query,History.class);//获取所有完成番茄的历史记录
        System.out.println("size"+histories.size());
        for(History history:histories){
            LocalTime now= DateToLocalTime(history.getStartTime());
            System.out.println(now+"now");
            if(now.isBefore(localTime2)&&now.isAfter(localTime0))mor++;
            else if(now.isBefore(localTime4)&&now.isAfter(localTime2)) aft++;
            else if(now.isAfter(localTime4)) eve++;
        }
        time.add(mor);
        time.add(aft);
        time.add(eve);
        int max=Collections.max(time);
        int maxtime=0;
        for(int i=0;i<3;i++){
            if (time.get(i).equals(max))
                maxtime=i+1;
        }
        map.put("上午",mor);
        map.put("下午",aft);
        map.put("晚上",eve);

        newTimeData newTimeData1=new newTimeData("上午",mor);
        newTimeData newTimeData2=new newTimeData("下午",aft);
        newTimeData newTimeData3=new newTimeData("晚上",eve);

        newTimeDataList.add(newTimeData1);
        newTimeDataList.add(newTimeData2);
        newTimeDataList.add(newTimeData3);

         System.out.println("dd"+newTimeData1.getTime());

        if(maxtime==1) daytime="上午";
        else if(maxtime==3) daytime="晚上";
        else if (maxtime==2) daytime="下午";

       // System.out.println("mo"+mor+"af"+aft+"ev"+eve);
       // System.out.println(maxtime);

        return newTimeDataList;

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