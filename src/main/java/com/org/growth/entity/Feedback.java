package com.org.growth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.org.growth.Other.AutoIncrement;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.annotation.Generated;
import java.util.Date;

/*
users' Feedback
 */
@Document(collection = "Feedback")
public class Feedback {

    @AutoIncrement
    @Id
    @Field("id")
    private long id;

    @Field("time")
    private String time;

    @Field("userid")
    private Long userid;

    @Field("title")
    private String title;

    @Field("content")
    private String content;

    @Field("status")
    private int status;//1:handled  0:unhandled

    @Field("answer")
    private String answer;//answer: null or reply

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
