package com.org.growth.controller;

import com.org.growth.Service.DailySummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * @author Ruabick
 * save and read dailysummary
 */
@RestController
@RequestMapping("summary")
public class DailySummaryController {

    @Autowired
    DailySummaryService dailySummaryService;

    @ResponseBody
    @GetMapping(value = "/save")
    public boolean saveDailySummary(@RequestParam(value = "userid")
                                                long userId, @RequestParam(value = "content")
            String content,
                                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date time,
                                    @RequestParam(value = "selfRating") int selfRating){
        return dailySummaryService.saveSummary(userId,content,time,selfRating);
    }

    @ResponseBody
    @GetMapping(value = "/read")
    public List readDailySummary(@RequestParam(value = "userid") long userId, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date time){
        return dailySummaryService.readSummary(userId,time);
    }

    @ResponseBody
    @GetMapping(value = "/update")
    public boolean updateDailySummary(@RequestParam(value = "userid") long userId, @RequestParam(value = "content")String content, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date time, @RequestParam(value = "selfRating") int selfRating){
        return dailySummaryService.modifySummary(userId,content,time,selfRating);
    }
}
