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

    /**
     * 删除标签
     */
    @FormUrlEncoded
    @POST("volunteer/delete.action")
    Observable<BaseEntity<String>> deleteTag(@Field("vid") String vId,
                                             @Field("pid") String pId, @Field("label") String tag);

    /**
     * 从系统推荐添加
     */
    @FormUrlEncoded
    @POST("volunteer/add.action")
    Observable<BaseEntity<String>> addCommonTag(@Field("vid") String vId,
                                                @Field("pid") String pId, @Field("label")String tag);

    /**
     * 自定义增加
     */
    @FormUrlEncoded
    @POST("volunteer/newAdd.action")
    Observable<BaseEntity<String>> addEditTag(@Field("vid") String vId,
                                              @Field("pid") String pId, @Field("label")String tag);
    /**
     * 修改
     */
    @FormUrlEncoded
    @POST("volunteer/modify.action")
    Observable<BaseEntity<String>>alertTag(@Field("vid") String vId, @Field("pid") String pId,
                                           @Field("before_label")String oldTag,@Field("after_label")String tag);
}
