package com.org.growth.entity;

import com.org.growth.Other.AutoIncrement;
import org.springframework.dao.DataAccessException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/***
 * @author Ruabick
 * 用于存放番茄和任务的历史纪录
 */
@Document(collection = "History")
public class History {

    @AutoIncrement
    @Id
    private long id;

    //userid and starttime are primary key
    @Field("userId")
    private long userId;//same with id in User

    @Field("starttime")
    private Date startTime;

    @Field("endtime")
    private Date endTime;

    @Field("tomatolength")
    private int tomatoLength;//same with tomatoLength in User

    @Field("status")
    private int status;//1:end normally -1:interrupted

    @Field("taskid")
    private long taskId;//-1 as default : not exist

    @Field("name")
    private String name;  //newly add

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

    public void setName(String name){
        this.name = name;
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

    public String getName(){
        return name;
    }

    public long getUserId() {
        return userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
