package com.sendi.picture_recognition.model.act;

import android.content.Context;

import com.sendi.picture_recognition.base.BaseModel;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.User;
import com.sendi.picture_recognition.service.LoginAndRegister;
import com.sendi.picture_recognition.utils.httputils.sqliteutils.SqliteDBUtils;
import com.sendi.userdb.UserDao;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/20.
 */

public class LoginModel extends BaseModel {

    private LoginAndRegister mService=this.createService(LoginAndRegister.class);

    /**
     * 登录
     * @param username
     * @param psw
     * @return
     */
    public Observable<BaseEntity<User>> login(String username,String psw){
        return mService.login(username, psw);
    }

    /**
     * 缓存用户数据
     */
    public void saveUserInfo(User user, Context context){
        UserDao userDao= SqliteDBUtils
                .getInstance(context)
                .getUserDao();
        //添加
        com.sendi.userdb.User mUser=new com.sendi.userdb.User();
        mUser.setUser_id(user.getUserId());//志愿号
        mUser.setUser_pic_url(user.getUser_pic_url());//头像
        mUser.setUser_nickname(user.getUserNickname());//名字
        mUser.setId(user.getId());//用户id
        mUser.setGander(user.getGender());//性别
        mUser.setHobbies(user.getHobbies());//爱好
        mUser.setPhone_number(user.getPhone_number());//手机号码
        userDao.insert(mUser);
    }
}
