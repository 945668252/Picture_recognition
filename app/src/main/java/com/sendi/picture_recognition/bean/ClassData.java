package com.sendi.picture_recognition.bean;

/**
 * Created by Administrator on 2017/5/23.
 */

public class ClassData {
    private String  classify;
    private String  path;

    public ClassData(String classify, String path) {
        this.classify = classify;
        this.path = path;
    }

    @Override
    public String toString() {
        return "ClassData{" +
                "classify='" + classify + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public String getClassify() {
        return classify;
    }

    public void setName(String classify) {
        this.classify = classify;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
