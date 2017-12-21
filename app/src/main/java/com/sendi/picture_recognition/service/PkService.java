package com.sendi.picture_recognition.service;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ChallengeHistory;
import com.sendi.picture_recognition.bean.MorePKInfo;
import com.sendi.picture_recognition.bean.PKResultData;
import com.sendi.picture_recognition.bean.UserChartInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/5.
 */

public interface PkService {
    /**
     * 排行榜数据
     * @return
     */
    @GET("volunteer/ranking.action")
    Observable<BaseEntity<List<UserChartInfo>>> getChartData(@Query("page")int page);

    /**
     * pk记录
     * @param vId
     * @return
     */
    @GET("volunteer/viewDMRecords.action")
    Observable<BaseEntity<List<ChallengeHistory>>>getPkHistory(@Query("vid")String vId);

    /**
     * 多人pk列表数据
     * @param vId
     * @return
     */
    @GET("picture/selectmoreaction.action")
    Observable<BaseEntity<List<MorePKInfo>>> getMorePKInfoList(@Query("vid") String vId);

    /**
     * 创建多人PK
     * @param vId
     */
    @GET("picture/creatmoreaction.action")
    Observable<BaseEntity<String>> getCreateResult(@Query("vid") String vId);

    /**
     * 获取双人pk结果
     * @param pkId
     * @return
     */
    @GET("volunteer/detail.action")
    Observable<BaseEntity<PKResultData>>getPkResultData(@Query("pkId")String pkId, @Query("vid")String vId);
}
