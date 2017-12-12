package com.sendi.picture_recognition.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/23.
 */

public class PKResultData {
    private String myUrl;
    private String otherUrl;
    private List<ImgAndGrade>records;

    public List<ImgAndGrade> getImgAndGradeList() {
        return records;
    }

    public void setImgAndGradeList(List<ImgAndGrade> imgAndGradeList) {
        records = imgAndGradeList;
    }

    public String getMyUrl() {
        return myUrl;
    }

    public void setMyUrl(String myUrl) {
        this.myUrl = myUrl;
    }

    public String getOtherUrl() {
        return otherUrl;
    }

    public void setOtherUrl(String otherUrl) {
        this.otherUrl = otherUrl;
    }

    @Override
    public String toString() {
        return "PKResultData{" +
                "myUrl='" + myUrl + '\'' +
                ", otherUrl='" + otherUrl + '\'' +
                ", mImgAndGradeList=" + records +
                '}';
    }

    /**
     * 图片和对应的分数比重
     */
    public class ImgAndGrade{
        public String myGrade;
        public String otherGrade;
        public String picUrl;

        public String getMyGrade() {
            return myGrade;
        }

        public void setMyGrade(String myGrade) {
            this.myGrade = myGrade;
        }

        public String getOtherGrade() {
            return otherGrade;
        }

        public void setOtherGrade(String otherGrade) {
            this.otherGrade = otherGrade;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        @Override
        public String toString() {
            return "ImgAndGrade{" +
                    "myGrade='" + myGrade + '\'' +
                    ", otherGrade='" + otherGrade + '\'' +
                    ", picUrl='" + picUrl + '\'' +
                    '}';
        }
    }
}
