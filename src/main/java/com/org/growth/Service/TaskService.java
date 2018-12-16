package com.org.growth.Service;

import com.org.growth.DAO.TaskDao;
import com.org.growth.DAO.TaskTreeDao;
import com.org.growth.entity.History;
import com.org.growth.entity.Summary;
import com.org.growth.entity.Task;
import com.org.growth.entity.TaskTree;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/***
 * @author Rubick
 */
@Component
public class TaskService implements TaskDao, TaskTreeDao {

    @Resource
    private MongoTemplate mongoTemplate1;

    private static MongoTemplate mongoTemplate;


    @PostConstruct
    void init(){
        mongoTemplate = mongoTemplate1;
    }

    @Override
    public List addTask(long userId, String name, String description, int expectedTomato, Date setTime,Date deadline, Date remindTime) {
        List list = new ArrayList();
        try {
            //primary key
            Criteria criteria = new Criteria();
            criteria.and("userId").is(userId);
            criteria.and("name").is(name);
            Query query = Query.query(criteria);
            Task task1 = mongoTemplate.findOne(query,Task.class);
            if (task1 != null) {
                list.add(0);
                list.add(-1);
                return list;
            }

            Task task = new Task();
            task.setUserId(userId);
            task.setName(name);
            task.setSetTime(setTime);
            task.setDescription(description);
            task.setExpectedTomato(expectedTomato);
            task.setTomatoCompleted(0);
            task.setDeadline(deadline);
            task.setRemindTime(remindTime);
            task.setFinishedTime(null);
            task.setStatus(0);

            mongoTemplate.insert(task);
            //return taskid
            Task task2 = TaskService.findByNameAndUserId(name,userId);
            list.add(1);
            list.add(task2.getId());
            return list;
        }
        catch (Exception e) {
            list.add(-1);
            list.add(-1);
            return list;
        }
    }


    @Override
    public boolean startTask(long userId, String taskName,Date startTime) {
        mongoTemplate = mongoTemplate1;
        try{
            //update task
            Criteria criteria = new Criteria();
            criteria.and("userId").is(userId);
            criteria.and("name").is(taskName);
            Query query = Query.query(criteria);

            Update update = new Update();
            update.set("status", 1);
            mongoTemplate.updateFirst(query, update, Task.class);

            //insert into history
            History history = new History();
            history.setUserId(userId);
            history.setStartTime(startTime);
            history.setEndTime(null);
            history.setStatus(0);
            history.setTaskId(TaskService.findByNameAndUserId(taskName, userId).getId());
            history.setTomatoLength(UserService.findById(userId).getTomatoLength());
            history.setName(taskName);
            mongoTemplate.insert(history);
            return  true;
        }
        catch (Exception e){
            return false;
        }
    }


    @Override
    public boolean breakTask(long userId, String taskName, Date time) {
        mongoTemplate = mongoTemplate1;
        try{
            //update task
            Criteria criteria = new Criteria();
            criteria.and("userId").is(userId);
            criteria.and("name").is(taskName);
            Query query = Query.query(criteria);

            Update update = new Update();
            update.set("status", -1);
            update.set("finishedTime", time);
            mongoTemplate.updateFirst(query, update, Task.class);

            //update history
            Task task = TaskService.findByNameAndUserId(taskName, userId);
            Criteria criteria1 = new Criteria();
            criteria.and("userId").is(userId);
            criteria.and("id").is(task.getId());
            Query query1 = Query.query(criteria1);

            Update update1 = new Update();
            update1.set("endtime",time);
            update1.set("status",-1);
            mongoTemplate.updateFirst(query1, update1, History.class);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean endTask(long userId, String taskName, Date endTime) {
        mongoTemplate = mongoTemplate1;
        try {
            //update task
            Criteria criteria = new Criteria();
            criteria.and("userId").is(userId);
            criteria.and("name").is(taskName);
            Query query = Query.query(criteria);
            Task task = mongoTemplate.findOne(query,Task.class);

            Update update = new Update();
            update.set("tomatoCompleted",task.getExpectedTomato());
            update.set("finishedTime", endTime);
            update.set("status", 2);
            mongoTemplate.updateFirst(query,update,Task.class);

            //update history
            Criteria criteria1 = new Criteria();
            criteria1.and("userId").is(userId);
            criteria1.and("taskid").is(TaskService.findByNameAndUserId(taskName, userId).getId());
            Query query1 = Query.query(criteria1);

            Update update1 = new Update();
            update1.set("endtime",endTime);
            update1.set("status", 1);
            mongoTemplate.updateFirst(query1, update1,History.class);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    class ResultTask{
        private long id;
        private long userId;
        private String name;
        private String description;
        private int expectedTomato;
        private int tomatoCompleted;
        private String setTime;
        private String deadline;
        private String remindTime;
        private String finishedTime;
        private int status;

        public ResultTask(Task task){
            try {
                this.userId = task.getUserId();
                this.id = task.getId();
                this.name = task.getName();
                this.description = task.getDescription();
                this.expectedTomato = task.getExpectedTomato();
                this.tomatoCompleted = task.getTomatoCompleted();
                this.status = task.getStatus();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                this.setTime = sdf.format(task.getSetTime());
                this.deadline = sdf.format(task.getDeadline());
                this.remindTime = sdf.format(task.getRemindTime());
                this.finishedTime = sdf.format(task.getFinishedTime());
            }catch (Exception e){}
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public int getExpectedTomato() {
            return expectedTomato;
        }

        public int getTomatoCompleted() {
            return tomatoCompleted;
        }

        public String getDeadline() {
            return deadline;
        }

        public int getStatus() {
            return status;
        }

        public long getUserId() {
            return userId;
        }

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        public String getFinishedTime() {
            return finishedTime;
        }

        public String getRemindTime() {
            return remindTime;
        }

        public String getSetTime() {
            return setTime;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setExpectedTomato(int expectedTomato) {
            this.expectedTomato = expectedTomato;
        }

        public void setFinishedTime(String finishedTime) {
            this.finishedTime = finishedTime;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setRemindTime(String remindTime) {
            this.remindTime = remindTime;
        }

        public void setSetTime(String setTime) {
            this.setTime = setTime;
        }

        public void setTomatoCompleted(int tomatoCompleted) {
            this.tomatoCompleted = tomatoCompleted;
        }
    }
    @Override
    public List queryTask(long userId) {
        try{
            List taskList = new ArrayList();
            Criteria criteria = new Criteria();
            criteria.and("userId").is(userId);
            Query  query = Query.query(criteria);
            query.with(new Sort(Sort.Direction.ASC, "status"));
            List resultList = mongoTemplate.find(query,Task.class);
            Iterator iterator = resultList.iterator();
            Task task;
            while(iterator.hasNext()){
                task = (Task)iterator.next();
                ResultTask resultTask = new ResultTask(task);
                taskList.add(resultTask);
            }
            return taskList;
        }
        catch (Exception e){
            return null;
        }
    }
    class TempResult{
        String time;
        String content;
        int status;

        public String getContent() {
            return content;
        }

        public int getStatus() {
            return status;
        }

        public String getTime() {
            return time;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        public TempResult(String time, String content, int status){
            this.time =time;
            this.content = content;
            this.status =status;
        }
    }
    class Result{

        public List<TempResult> tempResult;
        private String date;

        public Result(){
            tempResult = new ArrayList<TempResult>();
            date = null;
            /*tempResult.setContent(content);
            tempResult.setStatus(status);
            tempResult.setTime(time.substring(11,19));
            this.setDate(time.substring(0,10));*/
        }
        public String getDate() {
            return date;
        }

        public void setTempResult(List<TempResult> tempResult) {
            this.tempResult = tempResult;
        }

        public List<TempResult> getTempResult() {
            return tempResult;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }
    @Override
    public List queryTaskByYear(long userId, int startYear, int endYear) {
        try {
            List resultList = new ArrayList();
            Criteria criteria = new Criteria();
            criteria.and("userId").is(userId);
            Query query = Query.query(criteria);
            query.with(new Sort(Sort.Direction.ASC, "finishedTime"));
            String startOfYearStr = startYear + "-01-01 00:00:00";
            String endOfYearStr = endYear + "-12-31 23:59:59";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startOfYear = null;
            Date endOfYear = null;
            startOfYear = sdf.parse(startOfYearStr);
            endOfYear = sdf.parse(endOfYearStr);

            query.addCriteria(Criteria.where("finishedTime").lte(endOfYear).gte(startOfYear));
            List queryResult = mongoTemplate.find(query, Task.class);
            Iterator resultIterator = queryResult.iterator();
            Task task = (Task) resultIterator.next();
            ;
            do {
                Result result = new Result();
                String tempDate = sdf.format(task.getFinishedTime()).substring(0, 10);
                result.setDate(tempDate);
                String tempTime = sdf.format(task.getFinishedTime()).substring(11, 19);
                List<TempResult> list = new ArrayList<TempResult>();
                TempResult tempResult = new TempResult(tempTime, task.getDescription(), task.getStatus());
                list.add(tempResult);
                while (resultIterator.hasNext()) {
                    task = (Task) resultIterator.next();
                    if (tempDate.equals(sdf.format(task.getFinishedTime()).substring(0, 10))) {
                        tempTime = sdf.format(task.getFinishedTime()).substring(11, 19);
                        tempResult = new TempResult(tempTime, task.getDescription(), task.getStatus());
                        list.add(tempResult);
                    } else
                        break;
                }
                result.setTempResult(list);
                resultList.add(result);
            } while (resultIterator.hasNext());
            return resultList;
        }catch (Exception e){
            return  null;
        }
    }

    @Override
    public boolean removeTaskByName(long userId, String name) {
        try {

            Criteria criteria = new Criteria();
            criteria.and("userId").is(userId);
            criteria.and("name").is(name);
            Query query = Query.query(criteria);
            mongoTemplate.remove(query, Task.class);
            return true;
        }
        catch (Exception e){
            return  false;
        }
    }

    @Override
    public boolean modifyTask(long userId, String taskName, String property, String value) {
        Criteria criteria = new Criteria();
        Update update = new Update();
        criteria.and("userId").is(userId);
        criteria.and("name").is(taskName);
        try {
            if (property.equals("userId")) {
                long newId = Long.parseLong(value);
                update.set("userId", newId);
            } else if (property.equals("name") || property.equals("description")) {
                update.set(property, value);
            } else if (property.equals("expectedTomato") || property.equals("tomatoCompleted") || property.equals("status")) {
                int newValue = Integer.parseInt(value);
                update.set(property, newValue);
            } else if (property.equals("setTime") || property.equals("deadline") || property.equals("remindTime") || property.equals("finishedTime")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                update.set(property, sdf.parse(value));
            }
            Query query = Query.query(criteria);
            mongoTemplate.updateFirst(query, update, Task.class);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    static public Task findByNameAndUserId(String taskname, long userId) {

        try {
            Criteria criteria = new Criteria();
            criteria.and("name").is(taskname);
            criteria.and("userId").is(userId);
            Query query = Query.query(criteria);
            return mongoTemplate.findOne(query, Task.class);
        }
        catch (Exception e){
            return null;
        }
    }


    @Override
    public int addSubTask(long parentId, String sonName) {
        mongoTemplate = mongoTemplate1;
        // is existed
        try {
            Criteria criteria = new Criteria();
            criteria.and("parenttaskid").is(parentId);
            criteria.and("sontaskname").is(sonName);
            Query query = Query.query(criteria);
            TaskTree taskTree = mongoTemplate.findOne(query, TaskTree.class);
            if (taskTree != null)
                return 0;

            //insert
            TaskTree taskTree1 = new TaskTree();
            taskTree1.setParentTaskId(parentId);
            taskTree1.setSonTaskName(sonName);
            taskTree1.setTaskDone(false);
            mongoTemplate.insert(taskTree1);
            return 1;
        }
        catch (Exception e){
            return -1;
        }
    }


}
