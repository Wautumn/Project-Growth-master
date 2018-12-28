package com.org.growth.entity;

import com.org.growth.Other.AutoIncrement;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(value = "USER")
public class User {

    @AutoIncrement
    @Id
    private long id;

    @Field("username")
    private String username;

    @Field("password")
    private String password;

    @Field("tomatoLength")
    private int tomatoLength;//以分钟为单位

    @Field("tomatoAmount")
    private int tomatoAmount;

    @Field("tomatoWeekly")
    private int tomatoWeekly;//本周已经完成的番茄数量

    @Field("email")
    private String email;

    @Field("userFace")
    private String userFace;//头像

    @Field("dayGoal")
    private int dayGoal;//番茄日目标

    @Field("weekGoal")
    private int weekGoal;//番茄周目标

    @Field("monthGoal")
    private int monthGoal;//番茄月目标

    //@Field("music")
    //private String music;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTomatoLength() {
        return tomatoLength;
    }

    public void setTomatoLength(int tomatoLength) {
        this.tomatoLength = tomatoLength;
    }

    public int getTomatoamount() {
        return tomatoAmount;
    }

    public void setTomatoamount(int tomatoAmount) {
        this.tomatoAmount = tomatoAmount;
    }

    public int getTomatoweekly() {
        return tomatoWeekly;
    }

    public void setTomatoweekly(int tomatoweekly) {
        this.tomatoWeekly = tomatoWeekly;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserface() {
        return userFace;
    }

    public void setUserface(String userface) {
        this.userFace = userFace;
    }

    public int getDayGoal() {
        return dayGoal;
    }

    public void setDayGoal(int dayGoal) {
        this.dayGoal = dayGoal;
    }

    public int getWeekGoal() {
        return weekGoal;
    }

    public void setWeekGoal(int weekGoal) {
        this.weekGoal = weekGoal;
    }

    public int getMonthGoal() {
        return monthGoal;
    }

    public void setMonthGoal(int monthGoal) {
        this.monthGoal = monthGoal;
    }

    /*public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }*/
}
