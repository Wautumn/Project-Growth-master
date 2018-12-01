package com.org.growth.utils;

import com.org.growth.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class Util {
    /*
    获得当前用户
     */
    public static User getCurrentUser(){
        User user=(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
