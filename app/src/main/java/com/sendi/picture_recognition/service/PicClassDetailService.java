package com.sendi.picture_recognition.service;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.HomePicInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/21.
 */

public interface PicClassDetailService {
    /**
     * 获取某一类别的图片
     * @param userId
     * @param classify
     * @param language
     * @return
     */
    @GET("picture/classifypush.action")
    Observable<BaseEntity<List<HomePicInfo>>> getDiatailClassPic(@Query("vid") String userId,
                                                                 @Query("classify") String classify,
                                                                 @Query("lg") String language);
}
