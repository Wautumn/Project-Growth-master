package com.org.growth.entity;

import java.util.Map;

public class AnalyData {
    private String result;

    private Map<String,Object> data;

    public AnalyData(){

    }

    public AnalyData(String result,Map<String,Object> map){
        this.result=result;
        this.data=map;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
