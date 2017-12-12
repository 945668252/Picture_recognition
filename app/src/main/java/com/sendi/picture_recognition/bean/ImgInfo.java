package com.sendi.picture_recognition.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/5/10.
 */
public class ImgInfo implements Parcelable {
    private String url;
    private String[] tags;
    private String pic_id;

    public String getPic_id() {
        return pic_id;
    }

    public void setPic_id(String pic_id) {
        this.pic_id = pic_id;
    }

    @Override
    public String toString() {
        return "ImgInfo{" +
                "url='" + url + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", pic_id='" + pic_id + '\'' +
                '}';
    }

    public ImgInfo(String[] tags, String url, String pic_id) {
        this.tags = tags;
        this.url = url;
        this.pic_id=pic_id;
    }
    public ImgInfo(){}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(pic_id);
        dest.writeStringArray(tags);
    }
    public static final Parcelable.Creator<ImgInfo> CREATOR=new Creator<ImgInfo>() {
        @Override
        public ImgInfo createFromParcel(Parcel source) {
            ImgInfo imgInfo=new ImgInfo();
            imgInfo.url=source.readString();
            imgInfo.pic_id=source.readString();
            imgInfo.tags=source.createStringArray();
            return imgInfo;
        }

        @Override
        public ImgInfo[] newArray(int size) {
            return new ImgInfo[size];
        }
    };
}
