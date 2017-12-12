package com.sendi.picture_recognition.presenter.abstract_act;

import com.sendi.picture_recognition.base.BasePresenter;
import com.sendi.picture_recognition.view.activity.GradeActivity;

/**
 * Created by Administrator on 2017/12/8.
 */

public abstract class AbsGradepresenter extends BasePresenter<GradeActivity> {
    //获取积分
    public abstract void getIntegral(String vId);
}
