package com.org.growth.entity.useful;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Date;

/*
前端需要图表的数据
 */

public class AnalyzedataBean {
    private String date;

    private int tomatocount;

    private int taskCount;

    public AnalyzedataBean(int tomatocount,int taskCount,String date){
        this.tomatocount=tomatocount;
        this.taskCount=taskCount;
        this.date=date;

    }


    public int getTomatocount() {
        return tomatocount;
    }

    public void setTomatocount(int tomatocount) {
        this.tomatocount = tomatocount;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
