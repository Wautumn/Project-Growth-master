package com.org.Backend.entity;

public class User {
    private Long id;
    private String username;
    private String password;
    private int tomatolength;//以分钟为单位
    private int tomatoamount;
    private int tomatoweekly;//已经完成的番茄数量
    private String email;
    private String userface;//头像
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

    public int getTomatolength() {
        return tomatolength;
    }

    public void setTomatolength(int tomatolength) {
        this.tomatolength = tomatolength;
    }

    public int getTomatoamount() {
        return tomatoamount;
    }

    public void setTomatoamount(int tomatoamount) {
        this.tomatoamount = tomatoamount;
    }

    public int getTomatoweekly() {
        return tomatoweekly;
    }

    public void setTomatoweekly(int tomatoweekly) {
        this.tomatoweekly = tomatoweekly;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }
}
