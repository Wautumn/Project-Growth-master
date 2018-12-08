package com.org.growth.entity;

import java.util.Date;

/*
前端需要图表的数据
 */
public class AnalyzedataBean {
    private int tomatocount;
    private int taskCount;
    private Date date;
    private int level;


    public AnalyzedataBean(int tomatocount,int taskCount,Date date,int level){
        this.tomatocount=tomatocount;
        this.taskCount=taskCount;
        this.date=date;
        this.level=level;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
