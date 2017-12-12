package com.sendi.picture_recognition.model.abstract_act;

import com.sendi.picture_recognition.base.BaseModel;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ChallengeData;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.bean.SingleChallengeData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/7.
 */

public abstract class AbsChallengeModel extends BaseModel {
    /**
     * 一条多人pk的数据
     *
     * @param pId
     * @return
     */
    public abstract Observable<BaseEntity<List<HomePicInfo>>> getMorePKInfo(String pId);

    /**
     * 提交要pk的信息
     *
     * @param myId
     * @param oId
     * @param imgCount
     * @return
     */
    public abstract Observable<BaseEntity<SingleChallengeData>> getSinglePkData(String myId, String oId,
                                                                                String imgCount);

    /**
     * 提交挑战的数据
     *
     * @param challengeData
     * @return
     */
    public abstract Observable<BaseEntity<String>> submitMorePkData(ChallengeData challengeData);

    /**
     * 提交挑战的数据(发起挑战)
     *
     * @param challengeData
     * @return
     */

    public abstract Observable<BaseEntity<String>> submitSinglePkData(ChallengeData challengeData);

    /**
     * 接收挑战
     *
     * @param pkId
     * @return
     */
    public abstract Observable<BaseEntity<SingleChallengeData>> getSinglePkData(String pkId);

    /**
     * 提交接受挑战的数据
     * @param challengeData
     * @return
     */
    public abstract Observable<BaseEntity<String>> submitAcceptPkData(ChallengeData challengeData);
}
