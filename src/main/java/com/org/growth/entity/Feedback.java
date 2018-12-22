package com.org.growth.entity;

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
    private Date time;

    @Field("userid")
    private Long userid;

    @Field("content")
    private String content;

    @Field("status")
    private int status;//1为已经处理好的，0为未处理

    @Field("answer")
    private String answer;//反馈情况，可为空，就还没来得及反馈


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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
