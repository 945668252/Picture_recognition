package com.sendi.picture_recognition.model.act;

import com.sendi.picture_recognition.base.BaseModel;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.service.LoginAndRegister;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/20.
 */

public class RegisterModel extends BaseModel {

    private LoginAndRegister mService = this.createService(LoginAndRegister.class);

    /**
     * 获取验证码
     * @param mobileNumber
     * @return
     */
    public Observable<BaseEntity<String>> getCode(String mobileNumber) {
        return mService.getCode(mobileNumber);
    }

    /**
     * 提交注册信息
     * @param name
     * @param psw
     * @param hobbies
     * @param sex
     * @param mobil
     * @param code
     * @return
     */
    public Observable<BaseEntity<String>> register(String name, String psw,
                                                   String hobbies, String sex,
                                                   String mobil, String code) {
        return mService.register(name, psw, hobbies, sex, mobil, code);
    }
}
