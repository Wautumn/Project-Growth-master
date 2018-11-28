package com.org.growth.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

public class List {
    @Id
    private long id;//即任务id

    @Field("name")
    private String name;
    @Field("description")
    private String description;

    @Field("expectedTomato")
    private int expectedTomato;

    @Field("tomatoCompleted")
    private int tomatoCompleted;

    @Field("deadline")
    private Date deadline;

    @Field("remindTime")
    private Date remindTime;

    @Field("finishedTime")
    private Date finishedTime;

    @Field("status")
    private int status;//-1为放弃，0为未开始,1为正在进行，2为已放弃
}
