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
    private long id;

    @Field("time")
    private Date time;

    @Field("userid")
    private Long userid;

    @Field("content")
    private String content;

    @Field("state")
    private int state;

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
}
