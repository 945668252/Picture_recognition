package com.sendi.picture_recognition.presenter.act;

import android.content.Context;

import com.sendi.picture_recognition.model.abstract_act.AbsSettingModel;
import com.sendi.picture_recognition.model.act.SettingModel;
import com.sendi.picture_recognition.presenter.abstract_act.AbsSettingPresenter;

/**
 * Created by Administrator on 2017/12/8.
 */

public class SettingPresenter extends AbsSettingPresenter {

    private AbsSettingModel mModel;

    public SettingPresenter(){
        mModel=new SettingModel();
    }

    @Override
    public void exit(Context context) {
        String userName=mModel.exit(context);
        getView().toLogin(userName);
    }

    @Override
    public void initLgType(Context context) {
        mModel.initLgType(context);
    }
}
