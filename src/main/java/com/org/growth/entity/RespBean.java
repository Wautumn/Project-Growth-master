package com.org.growth.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public class RespBean {
    @Field("status")
    private String status;

    @Field("msg")
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RespBean() {
    }

    public RespBean(String status, String msg) {

        this.status = status;
        this.msg = msg;
    }

}
