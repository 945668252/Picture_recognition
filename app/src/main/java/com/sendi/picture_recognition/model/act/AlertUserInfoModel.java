package com.sendi.picture_recognition.model.act;

import android.content.Context;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.model.abstract_act.AbsAlertUserInfoModel;
import com.sendi.picture_recognition.service.UserService;
import com.sendi.picture_recognition.utils.httputils.sqliteutils.SqliteDBUtils;
import com.sendi.userdb.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2017/12/8.
 */

public class AlertUserInfoModel extends AbsAlertUserInfoModel {

    private UserService mService = this.createService(UserService.class);

    @Override
    public Observable<BaseEntity<String>> alertUerPic(List<MultipartBody.Part> partList) {
        return mService.alertUerPic(partList);
    }

    @Override
    public Observable<BaseEntity<String>> alertUerNickname(String nickname, String vId) {
        return mService.alertUerNickname(nickname, vId);
    }

    @Override
    public Observable<BaseEntity<String>> alertUserPhoneNumber(String phoneNumber,
                                                               String code, String userName) {
        return mService.alertUserPhoneNumber(phoneNumber, code, userName);
    }

    @Override
    public Observable<BaseEntity<String>> alertUserGender(String gender, String vId) {
        return mService.alertUserGender(gender, vId);
    }

    @Override
    public Observable<BaseEntity<String>> alertUserHobbies(String hobbies, String vId) {
        return mService.alertUserHobbies(hobbies, vId);
    }

    @Override
    public Observable<BaseEntity<String>> getCode(String phoneNumber) {
        return mService.getCode(phoneNumber);
    }

    @Override
    public User initUser(Context context) {
        mUserDao = SqliteDBUtils
                .getInstance(context)
                .getUserDao();
        mUser = mUserDao
                .queryBuilder()
                .listLazy()
                .get(0);

        return mUser;
    }

    @Override
    public void alertUser() {
        if (mUserDao != null && mUser != null)
            mUserDao.update(mUser);
    }
}
