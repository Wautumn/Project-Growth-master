package com.org.growth.controller;

import com.org.growth.Service.AnalyzeDataService;
import com.org.growth.entity.AnalyzedataBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAnalyzeDataController {
    @Autowired
    AnalyzeDataService analyzeDataService=new AnalyzeDataService();

    @RequestMapping(value = "/getAnalyze",method = RequestMethod.GET)
    public List<AnalyzedataBean> getAnalyzeData(@RequestParam(value = "userid") long userId){
        /*
        判断有多少天的数据,三个月为界限
         */
        if(analyzeDataService.isExistHistoryEnough(userId))
            return analyzeDataService.getCompletedData(userId);
        else
            return analyzeDataService.getAllCompletedData(userId);

    }
}
