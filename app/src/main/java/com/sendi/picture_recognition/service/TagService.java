package com.sendi.picture_recognition.service;

import com.sendi.picture_recognition.bean.BaseEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/12/20.
 */

public interface TagService {
    /**
     * 提交标签
     *
     * @param userId
     * @param imgId
     * @param tags
     * @return
     */
    @FormUrlEncoded
    @POST("volunteer/tagging.action")
    Observable<BaseEntity<String>> submitTags(@Field("vid") String userId
            , @Field("pid") String imgId
            , @Field("labels") String tags);
}
