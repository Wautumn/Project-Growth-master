package com.org.growth.controller;

import com.org.growth.entity.*;
import com.org.growth.Service.AnalyzeDataService;
import com.org.growth.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class GetAnalyzeDataController {
    @Autowired
    AnalyzeDataService analyzeDataService=new AnalyzeDataService();

    @Autowired
    UserService userService=new UserService();

    /*
    获取历史的图表信息
     */
    @RequestMapping(value = "/getHistoryData",method = RequestMethod.GET)
    public List<AnalyzedataBean> getAnalyzeData(@RequestParam(value = "userid") long userId){

        List<AnalyzedataBean> analyzedataBeans= analyzeDataService.getAllCompletedData(userId);
        return analyzedataBeans;

    }

    /*
    每周的哪一天效率比较高
     */
    @RequestMapping(value = "/getWeekdaySuggestion",method = RequestMethod.GET)
    public AnalyData getWeekdaySuggestion(@RequestParam(value = "userid") long userId){

        Map<String,Object> map=new HashMap<>();
        if(userService.findByUserId(userId)==null) {
            map.put("error","用户不存在！");
            //return new AnalyData("error",null);
        }
        List<newData> newDataList =analyzeDataService.getWeekdayData(userId);

        AnalyData analyData=new AnalyData();
        analyData.setData(newDataList);
        analyData.setResult(analyzeDataService.getWeekday());

        map.put("result",analyzeDataService.getWeekday());
        map.put("data",analyData);
        /*int time=0;
        time=analyzeDataService.getTimeData(userId);
        String day="无数据！";
        String daytime="无数据！";
        if(weekday==1)day="星期一";
        else if(weekday==2)day="星期二";
        else if(weekday==3)day="星期三";
        else if(weekday==4)day="星期四";
        else if(weekday==5)day="星期五";
        else if(weekday==6)day="星期六";
        else if(weekday==7)day="星期日";

        if(time==1) daytime="上午
        else if(time==3) daytime="晚上";";
        else if (time==2) daytime="下午";

        map.put("day",day);
        //map.put("time",daytime);
        map.put("TomatoNum",)*/

        return analyData;

    }



    /*
    哪个时间
     */
    @RequestMapping(value = "/getTimeSuggestion",method = RequestMethod.GET)
    public AnalyTimeData getTimeSuggestion(@RequestParam(value = "userid") long userId){

        List<newTimeData> newTimeDataList=new LinkedList<>();
        Map<String,Object> map=new HashMap<>();
        if(userService.findByUserId(userId)==null) {
            map.put("error","用户不存在！");
            //return new AnalyData("error",null);
        }
        newTimeDataList =analyzeDataService.getTimeData(userId);
        AnalyTimeData analyData=new AnalyTimeData();
        analyData.setResult(analyzeDataService.getDaytime());
        analyData.setData(newTimeDataList);
        /*int time=0;
        time=analyzeDataService.getTimeData(userId);
        String day="无数据！";
        String daytime="无数据！";
        if(weekday==1)day="星期一";
        else if(weekday==2)day="星期二";
        else if(weekday==3)day="星期三";
        else if(weekday==4)day="星期四";
        else if(weekday==5)day="星期五";
        else if(weekday==6)day="星期六";
        else if(weekday==7)day="星期日";

        if(time==1) daytime="上午";
        else if (time==2) daytime="下午";
        else if(time==3) daytime="晚上";

        map.put("day",day);
        //map.put("time",daytime);
        map.put("TomatoNum",)*/

        return analyData;

    }
}
