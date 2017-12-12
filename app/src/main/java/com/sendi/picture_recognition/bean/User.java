package com.sendi.picture_recognition.bean;

/**
 * Created by Administrator on 2017/5/7.
 */
public class User {
    private String portrait;
    private String username;//志願號
    private Long id;
    private String phone_number;
    private String gender;
    private String hobbies;
    private String regist_date;
    private String picUrl;
    private String nickname;

    public User(String portrait, String username, Long id, String phone_number, String gender, String hobbies, String regist_date) {
        this.portrait = portrait;
        this.username = username;
        this.id = id;
        this.phone_number = phone_number;
        this.gender = gender;
        this.hobbies = hobbies;
        this.regist_date = regist_date;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_pic_url='" + portrait + '\'' +
                ", username='" + username + '\'' +
                ", id=" + id +
                ", phone_number='" + phone_number + '\'' +
                ", gender='" + gender + '\'' +
                ", hobbies='" + hobbies + '\'' +
                ", regist_date='" + regist_date + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", userNickname='" + nickname + '\'' +
                '}';
    }

    public User(String user_pic_url, String username, Long id, String phone_number, String gender, String hobbies, String regist_date, String picUrl) {
        this.portrait = user_pic_url;
        this.username = username;
        this.id = id;
        this.phone_number = phone_number;
        this.gender = gender;
        this.hobbies = hobbies;
        this.regist_date = regist_date;
        this.picUrl = picUrl;
    }

    public String getUser_pic_url() {
        return portrait;
    }

    public String getUserNickname() {
        return nickname;
    }

    public void setUserNickname(String userNickname) {
        this.nickname = userNickname;
    }



    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }


    public void setUser_pic_url(String user_pic_url) {
        this.portrait = user_pic_url;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUserId() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getRegist_date() {
        return regist_date;
    }

    public void setRegist_date(String regist_date) {
        this.regist_date = regist_date;
    }
}
