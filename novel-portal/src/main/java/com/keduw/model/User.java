package com.keduw.model;

import java.util.Date;

public class User {
    private Integer id;

    private String sacct; //账号

    private String pwd;   //密码

    private String name;  //昵称

    private Date registTime; //注册时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSacct() {
        return sacct;
    }

    public void setSacct(String sacct) {
        this.sacct = sacct;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }
}