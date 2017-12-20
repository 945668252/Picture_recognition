package com.sendi.picture_recognition.view.activity.abs;

import com.sendi.picture_recognition.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/20.
 */

public abstract class AbsRegisterView extends BaseActivity {
    /**
     * 获取验证码成功
     */
    public abstract void getCodeSuccess();

    /**
     * 注册成功
     */
    public abstract void registerSuccess();
}
