package com.sendi.picture_recognition.presenter.abstract_act;

import android.content.Context;

import com.sendi.picture_recognition.base.BasePresenter;
import com.sendi.picture_recognition.view.activity.SettingActivity;

/**
 * Created by Administrator on 2017/12/8.
 */

public abstract class AbsSettingPresenter extends BasePresenter<SettingActivity> {
    public abstract void exit(Context context);
    //初始化语言
    public abstract void initLgType(Context context);
}
