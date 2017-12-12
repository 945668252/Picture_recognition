package com.sendi.picture_recognition.service;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ClassData;
import com.sendi.picture_recognition.bean.SearchPicData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/5.
 */

public interface SearchService {
    /**
     * 获取图片分类
     *
     * @return
     */
    @GET("picture/firstpush.action")
    Observable<BaseEntity<List<ClassData>>> getClassPicData(@Query("vid") String userId);

    /**
     * 获取热门图片
     * @return
     */
    @GET("picture/hotpush.action")
    Observable<BaseEntity<List<SearchPicData>>> getHotPicData(@Query("vid") String userId,
                                                              @Query("lg") String language);

    /**
     * 获取最新图片
     *
     * @return
     */
    @GET("picture/newpush.action")
    Observable<BaseEntity<List<SearchPicData>>> getNewPicData(@Query("vid") String userId,
                                                              @Query("lg") String language);
}
