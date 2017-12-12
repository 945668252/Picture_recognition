package com.sendi.picture_recognition.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/23.
 * 挑战时提交的数据
 */

public class ChallengeData {
    public String userId;
    public String pkId;
    public List<ImgAndTags> iatList;

    public ChallengeData(){}

    public ChallengeData(String userId, String pkId, List<ImgAndTags> iatList) {
        this.userId = userId;
        this.pkId = pkId;
        this.iatList = iatList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public List<ImgAndTags> getIatList() {
        return iatList;
    }

    public void setIatList(List<ImgAndTags> iatList) {
        this.iatList = iatList;
    }

    @Override
    public String toString() {
        return "ChallengeData{" +
                "userId='" + userId + '\'' +
                ", pkId='" + pkId + '\'' +
                ", iatList=" + iatList +
                '}';
    }

    public class ImgAndTags {
        public String imgId;
        public List<String> tagsList;

        public String getImgId() {
            return imgId;
        }

        public void setImgId(String imgId) {
            this.imgId = imgId;
        }

        public List<String> getTagsList() {
            return tagsList;
        }

        public void setTagsList(List<String> tagsList) {
            this.tagsList = tagsList;
        }

        public ImgAndTags(String imgId, List<String> tagsList) {
            this.imgId = imgId;
            this.tagsList = tagsList;
        }

        @Override
        public String toString() {
            return "ImgAndTags{" +
                    "imgId='" + imgId + '\'' +
                    ", tagsList=" + tagsList +
                    '}';
        }
    }
}
