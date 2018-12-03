package com.org.growth.Service;

import com.org.growth.DAO.TaskDao;
import com.org.growth.DAO.TaskTreeDao;
import com.org.growth.entity.History;
import com.org.growth.entity.Task;
import com.org.growth.entity.TaskTree;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class TaskService implements TaskDao, TaskTreeDao {

    @Resource
    private static MongoTemplate mongoTemplate;

    @Override
    public int addTask(long userId, String name, String description, int expectedTomato, Date deadline, Date remindTime) {
        try {
            //primary key
            Criteria criteria = new Criteria();
            criteria.and("userId").is(userId);
            criteria.and("name").is(name);
            Query query = Query.query(criteria);
            Task task1 = mongoTemplate.findOne(query,Task.class);
            if (task1 != null)
                return 0;

            Task task = new Task();
            task.setUserId(userId);
            task.setName(name);
            task.setDescription(description);
            task.setExpectedTomato(expectedTomato);
            task.setTomatoCompleted(0);
            task.setDeadline(deadline);
            task.setRemindTime(remindTime);
            task.setFinishedTime(null);
            task.setStatus(0);

            mongoTemplate.insert(task);
            return 1;
        }
        catch (Exception e) {
            return -1;
        }
    }


    @Override
    public boolean startTask(long userId, String taskName,Date startTime) {
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
            mongoTemplate.insert(history);
            return  true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean breakTask(long userId, String taskName, Date time) {
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
        try {
            //update task
            Criteria criteria = new Criteria();
            criteria.and("userId").is(userId);
            criteria.and("taskName").is(taskName);
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
