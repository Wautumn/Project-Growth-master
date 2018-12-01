package com.org.growth.controller;

import com.org.growth.Service.TomatoService;
import com.org.growth.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TomatoController {

    @Autowired
    TomatoService tomatoService;

    @ResponseBody
    @GetMapping(value = "/viewHistory")
    public List<History> viewHistory(@RequestParam(value = "userId") long userId){
        return tomatoService.viewHistory(userId);
    }

    @ResponseBody
    @GetMapping(value = "/starttomato")
    public boolean startTomato(@RequestParam(value = "userid") long userId){
        return tomatoService.saveStartTomato(userId);
    }

    @ResponseBody
    @GetMapping(value = "breaktomato")
    public boolean breakTomato(@RequestParam(value = "userid") long userId){
        return tomatoService.saveBreakTomato(userId);
    }

    @ResponseBody
    @GetMapping(value = "endtomato")
    public boolean endTomato(@RequestParam(value = "userid") long userId, @RequestParam(value = "needassociation") boolean needAssociation, @RequestParam(value = "taskname") String taskName){
        return tomatoService.saveEndTomato(userId,needAssociation,taskName);
    }

}
