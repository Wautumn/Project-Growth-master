package com.org.growth.Service;

import com.org.growth.DAO.TomatoDao;
import com.org.growth.entity.History;
import com.org.growth.entity.Task;
import com.org.growth.entity.User;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
public class TomatoService implements TomatoDao {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public Page<History> viewHistory(long userId, int size, int page) {

        Sort sort = new Sort(Sort.Direction.DESC, "starttime");
        Pageable pageable = new PageRequest(page-1, size, sort);
        Query query = Query.query(where("userId").is(userId));
        java.util.List<History> items = mongoTemplate.find(query.with(pageable), History.class);
        long total = mongoTemplate.count(query, History.class);
        return new PageImpl(items, pageable, total);

    }

    private static Date getStartTime(String year) {

        int time = Integer.parseInt(year);
        Calendar start = Calendar.getInstance();
        start.set(Calendar.YEAR, time);
        start.set(Calendar.MONTH, 1);
        start.set(Calendar.DATE, 1);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        return start.getTime();
    }

    private static Date getEndTime(String year) {

        int time = Integer.parseInt(year);
        Calendar end = Calendar.getInstance();
        end.set(Calendar.YEAR, time);
        end.set(Calendar.MONTH, 12);
        end.set(Calendar.DATE, 31);
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.set(Calendar.MILLISECOND, 999);
        return end.getTime();
    }

    @Override
    public java.util.List<History> viewMonthHistory(long userId, String year){

        java.util.List<History> items = mongoTemplate.find(Query.query(where("starttime")
                .gte(getStartTime(year))
                .lte(getEndTime(year))
                .and("userId").is(userId)), History.class);
        return items;

    }

    @Override
    public boolean saveStartTomato(long userId, Date startTime) {
        try {
            History history = new History();
            history.setStartTime(startTime);
            history.setUserId(userId);
            Query query = new Query(where("_id").is(userId));
            User user = mongoTemplate.findOne(query,User.class);
            history.setTomatoLength(user.getTomatoLength());
            history.setTaskId(-1);
            mongoTemplate.insert(history);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean saveBreakTomato(long userId, Date startTime, Date endTime) {
        try{
            Update update = new Update();
            Criteria criteria = new Criteria();
            criteria.and("userId").is(userId);
            criteria.and("starttime").is(startTime);
            Query query = Query.query(criteria);
            update.set("endtime", endTime);
            update.set("status", -1);
            mongoTemplate.updateFirst(query,update, History.class);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean saveEndTomato(long userId, boolean needAssociation,Date startTime,Date endTime) {
        return saveEndTomato(userId, needAssociation, "",startTime,endTime);
    }

    @Override
    public boolean saveEndTomato(long userId, boolean needAssociation, String taskName,Date startTime,Date endTime){
        try{

            //query
            Criteria criteria = new Criteria();
            criteria.and("userId").is(userId);
            criteria.and("starttime").is(startTime);
            Query query = Query.query(criteria);
            //update
            Update update = new Update();
            update.set("endtime", endTime);
            update.set("status", 1);
            mongoTemplate.updateFirst(query,update, History.class);

            if(needAssociation){
                //query
                Criteria criteria1 = new Criteria();
                criteria1.and("userId").is(userId);
                criteria1.and("name").is(taskName);
                Query query1 = Query.query(criteria1);
                Task list = mongoTemplate.findOne(query1, Task.class);

                //update
                Update update3 = new Update();
                update3.set("taskid", list.getId());
                update3.set("name",list.getName());
                mongoTemplate.updateFirst(query, update3, History.class);

                //task exists or not
                if (list != null){
                    //task exists
                    int tomatoCompleted = list.getTomatoCompleted() + 1;
                    int expectedTomato = list.getExpectedTomato();
                    //undone
                    if (tomatoCompleted < expectedTomato){
                        Update update1 = new Update();
                        update1.set("tomatoCompleted", tomatoCompleted);
                        update1.set("status", 1);
                        mongoTemplate.updateFirst(query1,update1, Task.class);
                    }
                    else{
                        Update update2 = new Update();
                        update2.set("tomatoCompleted", tomatoCompleted);
                        update2.set("status", 2);
                        mongoTemplate.updateFirst(query1,update2, Task.class);
                    }
                }
                else{
                    //task doesn't exist
                    Task list1 = new Task();
                    list1.setUserId(userId);
                    list1.setName(taskName);
                    list1.setDescription(null);
                    list1.setExpectedTomato(-1);
                    list1.setTomatoCompleted(1);
                    list1.setDeadline(null);
                    list1.setRemindTime(null);
                    list1.setFinishedTime(new Date());
                    list1.setStatus(2);
                    mongoTemplate.save(list1);
                }
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }


    /*
    返回某天的历史记录
     */
    public List<History> getOneDayHistory(long userid,LocalDate date){
        Query query=Query.query(Criteria.where("userId").is(userid));
        List<History> histories=mongoTemplate.find(query,History.class);
        List<History> dayhistory=new LinkedList<>();
        for(History history:histories){
            if ( DateToLocalDate(history.getStartTime()).equals(date))
                dayhistory.add(history);
        }
        return  dayhistory;

    }

    class historydata{
        private String start;
        private String end;
        private String text;
        private int status;

        public historydata(){

        }
        public historydata(String a,String b,String c,int t){
            this.start=a;
            this.end=b;
            this.text=c;
            this.status=t;

        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }


    /*
    得到一年的历史需要数据
     */
    public List<Map> getYearHistory(long userid,String year){
        DateTimeFormatter dateTimeFormatter0=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("hh:mm");
        LocalDate curdate = LocalDate.parse(year,dateTimeFormatter0);//当前日期
        LocalDate end=curdate.plusYears(1);
        List<Map> his=new LinkedList<>();

        long diff= ChronoUnit.DAYS.between(curdate, end);
        int bet=(int) diff;
        for(int i=0;i<diff;++i){
            LocalDate date=curdate.plusDays(i);
            List<History> histories=getOneDayHistory(userid,date);//当天的所有历史
            if(histories.size()==0) continue;
            Map<String,Object> map=new HashMap<>();
            map.put("date",date.toString());//日期
            List<historydata> historyday=new LinkedList<>();//存储需要的格式的当天的所有历史
            for(int j=0;j<histories.size();++j){
                historydata da=new historydata();
                da.setStart(DateToLocalTime(histories.get(j).getStartTime()).format(dateTimeFormatter));
                da.setEnd(DateToLocalTime(histories.get(j).getEndTime()).format(dateTimeFormatter));
                da.setStatus(histories.get(j).getStatus());
                da.setText(histories.get(j).getName());
                historyday.add(da);
            }
            map.put("content",historyday);
            his.add(map);
        }
        return his;
    }




    private LocalDate DateToLocalDate(Date date) {
        LocalDate newd;
        if(date==null) return  null;
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();//Date转化为LocalDate
        return localDate;
    }

    private LocalTime DateToLocalTime(Date date){
        LocalTime new1=LocalTime.of(00,00);
        if(date==null) return new1;
        Instant instant=date.toInstant();
        ZoneId zoneId=ZoneId.systemDefault();
        LocalTime localTime=instant.atZone(zoneId).toLocalTime();
        return localTime;
    }





}
