package com.sendi.picture_recognition.view.activity.abs;

import com.sendi.picture_recognition.base.BaseActivity;
import com.sendi.userdb.User;

/**
 * Created by Administrator on 2017/12/8.
 */

public abstract class AbsUserInfoView extends BaseActivity {

    public abstract void showUserInfo(User user);

    public abstract void alertUserPic(String path);

    //获取验证码
    public abstract void getCodeSuccess();

}
