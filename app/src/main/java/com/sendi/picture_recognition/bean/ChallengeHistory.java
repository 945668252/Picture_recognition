package com.sendi.picture_recognition.bean;

/**
 * Created by Administrator on 2017/6/24.
 * 双人挑战的历史记录
 */

public class ChallengeHistory {
    public String id;//pk的ID
    public String imgUrl;//图片代表的url
    public String resultStr;//状态
    public String challenger;//发起挑战者

    public ChallengeHistory(String id, String resultStr, String imgUrl) {
        this.id = id;
        this.resultStr = resultStr;
        this.imgUrl = imgUrl;
    }

    public String getChallenger() {
        return challenger;
    }

    public void setChallenger(String challenger) {
        this.challenger = challenger;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }
}
