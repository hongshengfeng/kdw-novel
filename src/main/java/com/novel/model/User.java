package com.novel.model;

public class User {
    private String email;
    private String qq;
    private String name;
    private String tel;
    private String age;
    public User(){

    }
    public User(String email,String qq, String name,String tel,String age){
        this.age=age;
        this.email=email;
        this.name=name;
        this.qq=qq;
        this.tel=tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
