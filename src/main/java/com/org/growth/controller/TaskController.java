package com.org.growth.controller;

import com.org.growth.Service.DailySummaryService;
import com.org.growth.Service.TaskService;
import com.org.growth.entity.TaskTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @ResponseBody
    @GetMapping(value = "addtask")
    public List addTask(@RequestParam(value = "userid") long userId, @RequestParam(value = "taskname") String taskName,
                        @RequestParam(value = "deadline") Date deadline, @RequestParam(value = "description") String description,
                        @RequestParam(value = "expectedtomato") int expectedTomato, @RequestParam(value = "remnidtime") Date remindTime){
        return taskService.addTask(userId,description,taskName, expectedTomato,deadline,remindTime);
    }
    @ResponseBody
    @GetMapping(value = "starttask")
    public boolean startTask(@RequestParam(value = "userid") long userId, @RequestParam(value = "taskname") String taskName,
                             @RequestParam(value = "startTime") Date startTime){
        return taskService.startTask(userId, taskName, startTime);
    }

    @ResponseBody
    @GetMapping(value = "breaktask")
    public boolean breakTask(@RequestParam(value = "userid") long userId, @RequestParam(value = "taskname") String taskName,
                             @RequestParam(value = "startTime") Date startTime){
        return taskService.breakTask(userId, taskName, startTime);
    }

    @ResponseBody
    @GetMapping(value = "endtask")
    public boolean endTask(@RequestParam(value = "userid") long userId, @RequestParam(value = "taskname") String taskName,
                           @RequestParam(value = "startTime") Date startTime){
        return taskService.endTask(userId, taskName, startTime);
    }
    @ResponseBody
    @GetMapping(value = "addsubtask")
    public int addSubTask(@RequestParam(value = "taskid") long taskid, @RequestParam(value = "name") String name){
        return taskService.addSubTask(taskid,name);
    }

}
