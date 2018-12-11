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

    /*
    获取历史的一些信息
     */
    @RequestMapping(value = "/getHistoryData",method = RequestMethod.GET)
    public List<AnalyzedataBean> getAnalyzeData(@RequestParam(value = "userid") long userId){
        List<AnalyzedataBean> analyzedataBeans= analyzeDataService.getAllCompletedData(userId);
        return analyzedataBeans;

    }

    /*
    每周的哪一天效率比较高，每一天的哪个时段完成番茄比较多
     */
    @RequestMapping(value = "/getSuggestion",method = RequestMethod.GET)
    public String getSuggestion(@RequestParam(value = "userid") long userId){
        String suggestion=null;

        return suggestion;
    }
}
