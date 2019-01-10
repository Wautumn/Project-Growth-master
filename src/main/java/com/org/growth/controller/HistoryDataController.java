package com.org.growth.controller;

import com.org.growth.Service.AnalyzeDataService;
import com.org.growth.Service.TomatoService;
import com.org.growth.Service.UserService;
import com.org.growth.entity.useful.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class HistoryDataController {

    @Autowired
    TomatoService tomatoService;

    @Autowired
    AnalyzeDataService analyzeDataService=new AnalyzeDataService();

    @Autowired
    UserService userService=new UserService();

    @GetMapping(value = "/viewYearHistory")
    public java.util.List<Map> viewYearHistory(@RequestParam(value = "userId") long userId, @RequestParam(value = "year") String year){
        return tomatoService.getYearHistory(userId,year);
    }


    /*
    get history info for graphs and charts
     */
    @RequestMapping(value = "/getHistoryData",method = RequestMethod.GET)
    public List<AnalyzedataBean> getAnalyzeData(@RequestParam(value = "userid") long userId){

        List<AnalyzedataBean> analyzedataBeans= analyzeDataService.getAllCompletedData(userId);
        return analyzedataBeans;

    }

    /*
    graphs and charts of certain period
     */
    @RequestMapping(value = "/getOneYearHistoryData",method = RequestMethod.GET)
    public List<AnalyzedataBean> getTwoMonth(@RequestParam(value = "userid") long userId,@RequestParam(value = "date") String date){
        return analyzeDataService.getOneYearData(userId,date);
        // }
    }

    /*
    which day of the week is the most efficient one
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
        return analyData;

    }

    /*
    which time
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

        return analyData;

    }





}
