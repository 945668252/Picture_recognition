package com.sendi.picture_recognition.bean;

/**
 * Created by Administrator on 2017/5/13.
 */

public class HomePicInfo {
    private String path;
    private String label;
    private String id;

    public HomePicInfo(String pic_url, String id, String tags) {
        this.path = pic_url;
        this.id = id;
        this.label = tags;
    }

    public String getPic_url() {
        return path;
    }

    public void setPic_url(String pic_url) {
        this.path = pic_url;
    }

    public String getTags() {
        return label;
    }

    public void setTags(String tags) {
        this.label = tags;
    }

    public String getPic_id() {
        return id;
    }

    public void setPic_id(String pic_id) {
        this.id = pic_id;
    }

    @Override
    public String toString() {
        return "HomePicInfo{" +
                "pic_url='" + path + '\'' +
                ", tags='" + label + '\'' +
                ", pic_id='" + id + '\'' +
                '}';
    }
}
