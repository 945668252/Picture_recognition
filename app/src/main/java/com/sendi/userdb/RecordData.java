package com.sendi.userdb;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "RECORD_DATA".
 */
public class RecordData {

    private String img_url;
    private String img_id;
    private String tags_selected;
    private String tags_unselected;
    private String status;

    public RecordData() {
    }

    public RecordData(String img_url, String img_id, String tags_selected, String tags_unselected, String status) {
        this.img_url = img_url;
        this.img_id = img_id;
        this.tags_selected = tags_selected;
        this.tags_unselected = tags_unselected;
        this.status = status;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public String getTags_selected() {
        return tags_selected;
    }

    public void setTags_selected(String tags_selected) {
        this.tags_selected = tags_selected;
    }

    public String getTags_unselected() {
        return tags_unselected;
    }

    public void setTags_unselected(String tags_unselected) {
        this.tags_unselected = tags_unselected;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
