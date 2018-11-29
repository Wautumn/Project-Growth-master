package com.org.growth.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Generated;
import java.util.Date;

/*
users' Feedback
 */
@Document(collection = "Feedback")
public class Feedback {

    @Id
    private Long id;

    private Date time;

    private Long userid;

    private String content;

    private int state;

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
