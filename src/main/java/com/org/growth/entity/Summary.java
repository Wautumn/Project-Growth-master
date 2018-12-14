package com.org.growth.entity;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.org.growth.Other.AutoIncrement;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/***
 * @author  rubick
 * 用于存放每日总结
 */
@Document(collection = "Summary")
public class Summary {

    @Id
    @AutoIncrement
    private long id;

    @Field("userId")
    private long userId;//与User中的Id保持一致

    @Field("content")
    private String content;

    @Field("time")
    private Date time;

    @Field("selfrating")
    private int selfRating;

    public int getSelfRating() {
        return selfRating;
    }

    public void setSelfRating(int selfRating) {
        this.selfRating = selfRating;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public void setId(long id) {
        this.id = id;
    }

}
