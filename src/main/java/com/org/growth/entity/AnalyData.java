package com.org.growth.entity;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnalyData  {
    private String result;

    private List<newData> data;

    public AnalyData(){

    }

    public AnalyData(String result,List<newData> map){
        this.result=result;
        this.data=map;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<newData> getData() {
        return data;
    }



    public void setData(List<newData> data) {
        this.data = data;
    }

}
