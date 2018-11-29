package com.org.growth.entity;

import com.org.growth.Other.AutoIncrement;
import org.springframework.dao.DataAccessException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "HISTORY")
public class History {

    @AutoIncrement
    @Id
    private long id;

    @Field("userId")
    private long userId;

    @Field("starttime")
    private Date startTime;

    @Field("endtime")
    private Date endTime;

    @Field("tomatolength")
    private int tomatoLength;//需与User中的时常一致

    @Field("status")
    private int status;//1为正常结束，-1为被中断

    @Field("taskid")
    private long taskId;//默认为-1,代表不存在

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public void setTomatoLength(int tomatoLength) {
        this.tomatoLength = tomatoLength;
    }

    public Date getStartTime() {
        return startTime;
    }

    public int getStatus() {
        return status;
    }

    public int getTomatoLength() {
        return tomatoLength;
    }

    public long getTaskId() {
        return taskId;
    }

    public long getUserId() {
        return userId;
    }
}
