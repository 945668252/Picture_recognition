package com.sendi.picture_recognition.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/25.
 */

public class SingleChallengeData {
    private String pkId;
    private List<HomePicInfo> pictures;

    public List<HomePicInfo> getPictures() {
        return pictures;
    }

    public void setPictures(List<HomePicInfo> pictures) {
        this.pictures = pictures;
    }

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }


}
