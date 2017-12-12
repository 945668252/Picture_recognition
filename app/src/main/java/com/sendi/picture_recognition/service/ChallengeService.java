package com.sendi.picture_recognition.service;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ChallengeData;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.bean.SingleChallengeData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/7.
 */

public interface ChallengeService {
    /**
     * 一条多人pk的数据
     *
     * @param pId
     * @return
     */
    @GET("picture/pushmoreactionpic.action")
    Observable<BaseEntity<List<HomePicInfo>>> getMorePKInfo(@Query("mid") String pId);

    /**
     * 提交要pk的信息
     * @param myId
     * @param oId
     * @param imgCount
     * @return
     */
    @FormUrlEncoded
    @POST("volunteer/compete.action")
    Observable<BaseEntity<SingleChallengeData>>getSinglePkData(@Field("challenger")String myId, @Field("invitee")String oId,
                                                         @Field("number")String imgCount);

    /**
     * 提交挑战的数据
     * @param challengeData
     * @return
     */
    @POST("mcompetition/compete.action")
    Observable<BaseEntity<String>> submitMorePkData(@Body ChallengeData challengeData);

    @GET("volunteer/accept.action")
    Observable<BaseEntity<SingleChallengeData>>getSinglePkData(@Query("pkId")String pkId);

    /**
     * 提交挑战的数据(发起挑战)
     * @param challengeData
     * @return
     */
    @POST("volunteer/firstRound.action")
    Observable<BaseEntity<String>> submitSinglePkData(@Body ChallengeData challengeData);
    /**
     * 提交挑战的数据(接受挑战)
     *  http://192.168.1.112:8080/picture_recognition/volunteer/firstRound.action?json
     * @param challengeData
     * @return
     */
    @POST("volunteer/secondRound.action")
    Observable<BaseEntity<String>> submitAcceptPkData(@Body ChallengeData challengeData);
}
