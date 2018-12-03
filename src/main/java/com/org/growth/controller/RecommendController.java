package com.org.growth.controller;

import com.org.growth.Service.RecommendService;
import com.org.growth.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecommendController {
    @Autowired
    RecommendService recommendService=new RecommendService();

    @Autowired
    UserService userService=new UserService();





}
