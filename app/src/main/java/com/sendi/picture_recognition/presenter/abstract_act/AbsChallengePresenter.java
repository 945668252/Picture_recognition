package com.sendi.picture_recognition.presenter.abstract_act;

import com.sendi.picture_recognition.base.BasePresenter;
import com.sendi.picture_recognition.bean.ChallengeData;
import com.sendi.picture_recognition.view.activity.abs.AbsChallengeView;

import retrofit2.http.Body;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/7.
 */

public abstract class AbsChallengePresenter extends BasePresenter<AbsChallengeView> {

    public enum Type{
        ACCEPT,
        MORE,
        SINGLE;
    }

    //获取挑战数据
    public abstract void getPkInfo(String... ids);
    //提交挑战数据
    public abstract void submit(ChallengeData challengeData,Type type);
    //接收挑战
    public abstract void getSinglePkData(String pkId);

}
