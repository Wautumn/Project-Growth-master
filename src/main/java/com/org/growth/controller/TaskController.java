package com.org.growth.controller;

import com.org.growth.Service.DailySummaryService;
import com.org.growth.Service.TaskService;
import com.org.growth.entity.TaskTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping(value = "task")
@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @ResponseBody
    @GetMapping(value = "addTask")
    public List addTask(@RequestParam(value = "userId") long userId, @RequestParam(value = "taskName") String taskName,
                        @RequestParam(value = "description") String description, @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")Date setTime,
                        @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")Date deadline,
                        @RequestParam(value = "expectedTomato") int expectedTomato, @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date remindTime){
        return taskService.addTask(userId,taskName, description,expectedTomato,setTime,deadline,remindTime);
    }


    @ResponseBody
    @GetMapping(value = "startTask")
    public boolean startTask(@RequestParam(value = "userId") long userId, @RequestParam(value = "taskName") String taskName,
                             @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime){
        return taskService.startTask(userId, taskName, startTime);
    }

    @ResponseBody
    @GetMapping(value = "breakTask")
    public boolean breakTask(@RequestParam(value = "userId") long userId, @RequestParam(value = "taskName") String taskName,
                             @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime){
        return taskService.breakTask(userId, taskName, startTime);
    }

    @ResponseBody
    @GetMapping(value = "endTask")
    public boolean endTask(@RequestParam(value = "userId") long userId, @RequestParam(value = "taskName") String taskName,
                           @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime){
        return taskService.endTask(userId, taskName, startTime);
    }
    @ResponseBody
    @GetMapping(value = "addSubTask")
    public int addSubTask(@RequestParam(value = "taskId") long taskId, @RequestParam(value = "name") String name){
        return taskService.addSubTask(taskId,name);
    }

    @ResponseBody
    @GetMapping(value = "getTask")
    public List queryTask(@RequestParam(value = "userId") long userId) {
        return taskService.queryTask(userId);
    }

    @ResponseBody
    @GetMapping(value = "querybyyear")
    public List queryTaskByYear(@RequestParam(value = "userId") long userId, @RequestParam(value = "startyear") int startYear, @RequestParam(value = "endyear") int endYear) {
        return taskService.queryTaskByYear(userId, startYear, endYear);
    }

    @ResponseBody
    @GetMapping(value = "deleteTask")
    public boolean deleteTaskByName(@RequestParam(value = "userId") long userId, @RequestParam(value = "taskName") String name) {
        return taskService.removeTaskByName(userId, name);
    }

    @ResponseBody
    @GetMapping(value = "modifyTask")
    public boolean deleteTaskByName(@RequestParam(value = "userId") long userId, @RequestParam(value = "taskName") String name, @RequestParam(value = "property") String property, @RequestParam(value = "value") String value) {
        return taskService.modifyTask(userId, name,property,value);
    }
}
