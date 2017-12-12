package com.sendi.picture_recognition.bean;

/**
 * Created by Administrator on 2017/6/23.
 */
public class GoodsData {
    public String integration;
    public String name;
    public String path;
    public String id;
    public String descripe;

    public String getDescribe() {
        return descripe;
    }

    public void setDescribe(String describe) {
        this.descripe = describe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntegration() {
        return integration;
    }

    public void setIntegration(String integration) {
        this.integration = integration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "GoodsData{" +
                "integration='" + integration + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
