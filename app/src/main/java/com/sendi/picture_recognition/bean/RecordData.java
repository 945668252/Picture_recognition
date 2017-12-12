package com.sendi.picture_recognition.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/4/25.
 */

public class RecordData implements Parcelable{
    int imgUrl;
    private String imageUrl;//图片的url
    private String pid;//图片id
    private String selectedTags[];//被打过的标签
    private String unselectedTags[];//系统推送的标签
    private String flag;
    private String status;//状态

    public RecordData() {
    }

    public RecordData(int imgUrl, String imageUrl, String pid, String[] selectedTags, String[] unselectedTags, String flag, String status) {
        this.imgUrl = imgUrl;
        this.imageUrl = imageUrl;
        this.pid = pid;
        this.selectedTags = selectedTags;
        this.unselectedTags = unselectedTags;
        this.flag = flag;
        this.status = status;
    }

    public RecordData(int imgUrl, String flag, String status) {
        this.imgUrl = imgUrl;
        this.flag = flag;
        this.status = status;
    }

    @Override
    public String toString() {
        return "RecordData{" +
                "imgUrl=" + imgUrl +
                ", imageUrl='" + imageUrl + '\'' +
                ", pid='" + pid + '\'' +
                ", selectedTags=" + Arrays.toString(selectedTags) +
                ", unselectedTags=" + Arrays.toString(unselectedTags) +
                ", flag='" + flag + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String[] getSelectedTags() {
        return selectedTags;
    }

    public void setSelectedTags(String[] selectedTags) {
        this.selectedTags = selectedTags;
    }

    public String[] getUnselectedTags() {
        return unselectedTags;
    }

    public void setUnselectedTags(String[] unselectedTags) {
        this.unselectedTags = unselectedTags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(pid);
        dest.writeStringArray(selectedTags);
        dest.writeStringArray(unselectedTags);
        dest.writeString(status);
    }
    public static final Creator<RecordData> CREATOR=new Creator<RecordData>() {
        @Override
        public RecordData createFromParcel(Parcel source) {
            RecordData recordData=new RecordData();
            recordData.imageUrl=source.readString();
            recordData.pid=source.readString();
            recordData.selectedTags=source.createStringArray();
            recordData.unselectedTags=source.createStringArray();
            recordData.status=source.readString();
            return recordData;
        }

        @Override
        public RecordData[] newArray(int size) {
            return new RecordData[size];
        }
    };
}
