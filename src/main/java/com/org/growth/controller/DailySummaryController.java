package com.org.growth.controller;

import com.org.growth.Service.DailySummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class DailySummaryController {

    @Autowired
    DailySummaryService dailySummaryService;

    @ResponseBody
    @GetMapping(value = "/savedailysummary ")
    public boolean saveDailySummary(@RequestParam(value = "userid") long userId, @RequestParam(value = "contetn")String content, @RequestParam(value = "time")Date time){
        return dailySummaryService.save(userId,content,time);
    }
}
