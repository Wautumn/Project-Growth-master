package com.org.growth.entity;

import com.org.growth.Other.AutoIncrement;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/***
 * @author rubick
 * store info of tasks
 */
@Document(collection = "Task")
public class Task {
    @AutoIncrement
    @Id
    private long id;//task id

    //userId and name : no repetition!!
    @Field("userId")
    private long userId;

    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("expectedTomato")
    private int expectedTomato;
    @Field("tomatoCompleted")
    private int tomatoCompleted;

    @Field("setTime")
    private Date setTime;//time of setting the task

    @Field("deadline")
    private Date deadline;

    @Field("remindTime")
    private Date remindTime;

    @Field("finishedTime")
    private Date finishedTime;

    @Field("status")
    private int status;//-1:give up   0:not begin yet  1:in process   2: done

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Date getRemindTime() {
        return remindTime;
    }

    public int getExpectedTomato() {
        return expectedTomato;
    }

    public int getTomatoCompleted() {
        return tomatoCompleted;
    }

    public int getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpectedTomato(int expectedTomato) {
        this.expectedTomato = expectedTomato;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }

    public void setTomatoCompleted(int tomatoCompleted) {
        this.tomatoCompleted = tomatoCompleted;
    }

    public long getId() {
        return id;
    }

    public Date getSetTime() {
        return setTime;
    }

    public void setSetTime(Date setTime) {
        this.setTime = setTime;
    }
}
