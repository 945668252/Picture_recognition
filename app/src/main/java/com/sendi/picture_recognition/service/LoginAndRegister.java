package com.sendi.picture_recognition.service;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/12/20.
 */

public interface LoginAndRegister {
    /**
     * 获取验证码
     *
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("volunteer/verify.action")
    Observable<BaseEntity<String>> getCode(@Field("mobile") String mobile);

    /**
     * 注册
     * @return
     */
    @FormUrlEncoded
    @POST("volunteer/regist.action")
    Observable<BaseEntity<String>> register(@Field("username") String name,
                                            @Field("password") String psw,
                                            @Field("hobbies") String hobbies,
                                            @Field("gender") String gender,
                                            @Field("mobile") String mobil,
                                            @Field("code") String code);

    /**
     * 登录
     *
     * @param userName
     * @param userPsw
     * @return
     */
    @FormUrlEncoded
    @POST("volunteer/login.action")
    Observable<BaseEntity<User>> login(@Field("username") String userName, @Field("password") String userPsw);
}
