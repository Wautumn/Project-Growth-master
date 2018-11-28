package com.org.growth.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "History")
public class History {

    @Id
    private long id;

    @Field("userId")
    private long userId;

    @Field("startTime")
    private Date startTime;

    @Field("tomatoLength")
    private int tomatoLength;

    @Field("status")
    private int status;//0 undone,1 done

    @Field("taskId")
    private long taskId;//-1 代表没有
}
