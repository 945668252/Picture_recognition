package com.sendi.picture_recognition.presenter.abstract_act;

import android.content.Context;

import com.sendi.picture_recognition.base.BasePresenter;
import com.sendi.picture_recognition.view.activity.abs.AbsUserInfoView;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2017/12/8.
 */

public abstract class AbsAlertUserInfoPresenter extends BasePresenter<AbsUserInfoView> {

    public abstract void alertUerPic(List<MultipartBody.Part> partList);

    public abstract void alertUerNickname(String nickname, String vId);

    public abstract void alertUserPhoneNumber(String phoneNumber, String code, String userName);

    public abstract void alertUserGender(String gender, String vId);

    public abstract void alertUserHobbies(String hobbies, String vId);

    public abstract void getCode(String phoneNumber);

    //从本地数据库中取出User
    public abstract void initUser(Context context);

}
