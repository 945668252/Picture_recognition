package com.sendi.picture_recognition.service;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.HomePicInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/5.
 */

public interface HomeService {
    /**
     * 首页拉取图片信息
     * @return
     */
    @GET("picture/push.action")
    Observable<BaseEntity<List<HomePicInfo>>> getHomePicData(@Query("vid") String userId
            , @Query("lg") String lg);
}
