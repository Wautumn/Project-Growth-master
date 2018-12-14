package com.org.growth.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.org.growth.Other.AutoIncrement;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
    @JsonSerialize
    private long userId;//与User中的Id保持一致

    @Field("content")
    @JsonSerialize
    private String content;

    @Field("time")
    @JsonSerialize
    private Date time;

    @Field("selfrating")
    @JsonSerialize
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
}
