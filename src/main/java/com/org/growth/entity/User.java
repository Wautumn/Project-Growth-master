package com.org.growth.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;


public class User {

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

    @Field("music")
    private String music;

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

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

}
