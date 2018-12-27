package com.org.growth.entity.useful;

import org.springframework.stereotype.Component;

@Component
public class newData {
    private String weekday;

    private int tomatocount;

    private int taskcount;

    public newData(){

    }

    public newData(String s,int a,int b){
        this.weekday=s;
        this.tomatocount=a;
        this.taskcount=b;
    }


    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public int getTomatocount() {
        return tomatocount;
    }

    public void setTomatocount(int tomatocount) {
        this.tomatocount = tomatocount;
    }

    public int getTaskcount() {
        return taskcount;
    }

    public void setTaskcount(int taskcount) {
        this.taskcount = taskcount;
    }
}
