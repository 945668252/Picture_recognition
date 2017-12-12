package com.sendi.picture_recognition.presenter.act;

import android.content.Context;

import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.model.abstract_act.AbsAlertUserInfoModel;
import com.sendi.picture_recognition.model.act.AlertUserInfoModel;
import com.sendi.picture_recognition.presenter.abstract_act.AbsAlertUserInfoPresenter;
import com.sendi.userdb.User;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2017/12/8.
 */

public class AlertUserInfoPresenter extends AbsAlertUserInfoPresenter {

    private AbsAlertUserInfoModel mModel;

    public AlertUserInfoPresenter() {
        mModel = new AlertUserInfoModel();
    }


    @Override
    public void alertUerPic(List<MultipartBody.Part> partList) {
        mModel.alertUerPic(partList)
                .compose(mModel.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        mModel.getUser().setUser_pic_url(t.getData());
                        mModel.alertUser();
                        GlobalConfig.USERPID = t.getData();
                        getView().alertUserPic(t.getData());
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @Override
    public void alertUerNickname(final String nickname, String vId) {
        mModel.alertUerNickname(nickname, vId)
                .compose(mModel.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        mModel.getUser().setUser_nickname(nickname);
                        mModel.alertUser();
                        getView().finish();
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @Override
    public void alertUserPhoneNumber(final String phoneNumber, String code, String userName) {
        mModel.alertUserPhoneNumber(phoneNumber, code, userName)
                .compose(mModel.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        mModel.getUser().setPhone_number(phoneNumber);
                        mModel.alertUser();
                        getView().finish();
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @Override
    public void alertUserGender(final String gender, String vId) {
        mModel.alertUserGender(gender, vId)
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        mModel.getUser().setGander(gender);
                        mModel.alertUser();
                        getView().finish();
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @Override
    public void alertUserHobbies(final String hobbies, String vId) {
        mModel.alertUserHobbies(hobbies, vId)
                .compose(mModel.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        mModel.getUser().setHobbies(hobbies);
                        mModel.alertUser();
                        getView().finish();
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @Override
    public void getCode(String phoneNumber) {
        mModel.getCode(phoneNumber)
                .compose(mModel.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        getView().getCodeSuccess();
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @Override
    public void initUser(Context context) {
        User user = mModel.initUser(context);
        getView().showUserInfo(user);
    }
}
