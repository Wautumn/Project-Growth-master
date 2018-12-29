package com.org.growth.controller;

import com.org.growth.Service.TomatoService;
import com.org.growth.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class TomatoController {

    @Autowired
    TomatoService tomatoService;

    @ResponseBody
    @GetMapping(value = "/startTomato")
    public boolean startTomato(@RequestParam(value = "userId") long userId,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime){
        return tomatoService.saveStartTomato(userId,startTime);
    }

    @ResponseBody
    @GetMapping(value = "breakTomato")
    public boolean breakTomato(@RequestParam(value = "userId") long userId,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date breakTime){
        return tomatoService.saveBreakTomato(userId,startTime,breakTime);
    }

    @ResponseBody
    @GetMapping(value = "endTomato")
    public boolean endTomato(@RequestParam(value = "userId") long userId, @RequestParam(value = "needAssociation") boolean needAssociation,
                             @RequestParam(value = "taskName") String taskName,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endTime){
        return tomatoService.saveEndTomato(userId,needAssociation,taskName,startTime,endTime);
    }

}
