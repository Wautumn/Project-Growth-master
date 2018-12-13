package com.org.growth.entity;

import org.springframework.stereotype.Component;

@Component
public class newTimeData {
    private String time;

    private int taskcount;

    public newTimeData(){

    }

    public newTimeData(String t,int a){
        this.time=t;
        this.taskcount=a;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTaskcount() {
        return taskcount;
    }

    public void setTaskcount(int taskcount) {
        this.taskcount = taskcount;
    }
}
