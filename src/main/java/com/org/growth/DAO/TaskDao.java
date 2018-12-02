package com.org.growth.DAO;

import com.org.growth.entity.Task;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;
import java.util.Date;

public interface TaskDao {

    // -1 错误， 0插入重复， 1 成功
    int addTask(long userId, String name, String description, int expectedTomato, Date deadline, Date remindTime);

    boolean startTask(long userId, String taskName, Date startTime);

    boolean breakTask(long userId, String taskName, Date time);

    boolean endTask(long userId, String taskName, Date endTime);
}