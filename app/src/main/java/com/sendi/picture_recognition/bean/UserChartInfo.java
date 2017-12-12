package com.sendi.picture_recognition.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by Administrator on 2017/6/24.
 * 排行榜数据模型
 */

public class UserChartInfo {
    public String portrait;
    public String nickname;
    public String weight;
    public String id;

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserChartInfo{" +
                "portrait='" + portrait + '\'' +
                ", nickname='" + nickname + '\'' +
                ", weight='" + weight + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
