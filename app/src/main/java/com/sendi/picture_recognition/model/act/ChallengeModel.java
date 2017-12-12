package com.sendi.picture_recognition.model.act;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ChallengeData;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.bean.SingleChallengeData;
import com.sendi.picture_recognition.model.abstract_act.AbsChallengeModel;
import com.sendi.picture_recognition.service.ChallengeService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/7.
 */

public class ChallengeModel extends AbsChallengeModel {
    private ChallengeService mService=this.createService(ChallengeService.class);

    @Override
    public Observable<BaseEntity<List<HomePicInfo>>> getMorePKInfo(String pId) {
        return mService.getMorePKInfo(pId);
    }

    @Override
    public Observable<BaseEntity<SingleChallengeData>> getSinglePkData(String myId, String oId, String imgCount) {
        return mService.getSinglePkData(myId, oId, imgCount);
    }

    @Override
    public Observable<BaseEntity<String>> submitMorePkData(ChallengeData challengeData) {
        return mService.submitMorePkData(challengeData);
    }

    @Override
    public Observable<BaseEntity<String>> submitSinglePkData(ChallengeData challengeData) {
        return mService.submitSinglePkData(challengeData);
    }

    @Override
    public Observable<BaseEntity<SingleChallengeData>> getSinglePkData(String pkId) {
        return mService.getSinglePkData(pkId);
    }

    @Override
    public Observable<BaseEntity<String>> submitAcceptPkData(ChallengeData challengeData) {
        return mService.submitAcceptPkData(challengeData);
    }
}
