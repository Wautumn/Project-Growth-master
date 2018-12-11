package com.org.growth.controller;

import com.org.growth.Service.TomatoService;
import com.org.growth.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TomatoController {

    @Autowired
    TomatoService tomatoService;

    @ResponseBody
    @GetMapping(value = "/viewHistory")
    public Page<History> viewHistory(@RequestParam(value = "userId") long userId, @RequestParam(value = "size") int size,
                                     @RequestParam(value = "page") int page){
        return tomatoService.viewHistory(userId, size, page);
    }

    /*
    @ResponseBody
    @GetMapping(value = "/viewHistoryStatus")
    public Page<History> viewHistoryStatus(@RequestParam(value = "userId") long userId, @RequestParam(value = "size") int size,
                                           @RequestParam(value = "page") int page, @RequestParam(value = "status") int status){
        return tomatoService.viewHistoryStatus(userId, size, page, status);
    }
    */

    @ResponseBody
    @GetMapping(value = "/startTomato")
    public boolean startTomato(@RequestParam(value = "userId") long userId){
        return tomatoService.saveStartTomato(userId);
    }

    @ResponseBody
    @GetMapping(value = "breakTomato")
    public boolean breakTomato(@RequestParam(value = "userId") long userId){
        return tomatoService.saveBreakTomato(userId);
    }

    @ResponseBody
    @GetMapping(value = "endTomato")
    public boolean endTomato(@RequestParam(value = "userId") long userId, @RequestParam(value = "needAssociation") boolean needAssociation,
                             @RequestParam(value = "taskName") String taskName){
        return tomatoService.saveEndTomato(userId,needAssociation,taskName);
    }

}
