package com.sendi.picture_recognition.bean;

/**
 * Created by Administrator on 2017/6/23.
 * 多人PK数据
 */

public class MorePKInfo {
    public String id;
    public String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MorePKInfo{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
