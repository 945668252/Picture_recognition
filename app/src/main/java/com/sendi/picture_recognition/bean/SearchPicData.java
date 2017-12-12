package com.sendi.picture_recognition.bean;

/**
 * Created by Administrator on 2017/5/10.
 */

public class SearchPicData {
    private String path;
    private String label;
    private String id;

    public SearchPicData(String path, String label, String id) {
        this.path = path;
        this.label = label;
        this.id = id;
    }

    @Override
    public String toString() {
        return "SearchPicData{" +
                "path='" + path + '\'' +
                ", label='" + label + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
