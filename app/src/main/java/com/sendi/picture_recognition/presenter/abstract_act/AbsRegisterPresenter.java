package com.sendi.picture_recognition.presenter.abstract_act;

import com.sendi.picture_recognition.base.BasePresenter;
import com.sendi.picture_recognition.view.activity.abs.AbsRegisterView;

/**
 * Created by Administrator on 2017/12/20.
 */

public abstract class AbsRegisterPresenter extends BasePresenter<AbsRegisterView> {
    /**
     * 获取验证码
     * @param mobileNumber
     */
    public abstract void getCode(String mobileNumber);

    /**
     * 提交注册信息
     * @param name
     * @param psw :密码
     * @param hobbies
     * @param sex
     * @param mobil:电话号码
     * @param code:验证码
     */
    public abstract void register(String name,String psw,
                                  String hobbies,String sex,
                                  String mobil,String code);
}
