package com.sendi.picture_recognition.model.abstract_act;

import android.content.Context;

import com.sendi.picture_recognition.base.BaseModel;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.userdb.User;
import com.sendi.userdb.UserDao;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2017/12/8.
 */

public abstract class AbsAlertUserInfoModel extends BaseModel {

    protected User mUser=null;
    protected UserDao mUserDao=null;

    public abstract Observable<BaseEntity<String>> alertUerPic(List<MultipartBody.Part> partList);

    public abstract Observable<BaseEntity<String>> alertUerNickname(String nickname, String vId);

    public abstract Observable<BaseEntity<String>> alertUserPhoneNumber(String phoneNumber, String code,
                                                                        String userName);
    public abstract Observable<BaseEntity<String>> alertUserGender(String gender, String vId);

    public abstract Observable<BaseEntity<String>> alertUserHobbies(String hobbies,String vId);

    public abstract Observable<BaseEntity<String>> getCode(String phoneNumber);

    public abstract User initUser(Context context);

    public User getUser(){
        return mUser;
    }
    //更新数据库
    public abstract void alertUser();
}
