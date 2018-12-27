package com.org.growth.entity.useful;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnalyTimeData {
    private String result;

    private List<newTimeData> data;

    public AnalyTimeData(){

    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<newTimeData> getData() {
        return data;
    }

    public void setData(List<newTimeData> data) {
        this.data = data;
    }
}
