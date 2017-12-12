package com.sendi.picture_recognition.service;

import com.sendi.picture_recognition.bean.BaseEntity;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/8.
 */

public interface UserService {
    /**
     * 修改用户图片
     * @return
     */
    @Multipart
    @POST("volunteer/resetportrait.action")
    Observable<BaseEntity<String>> alertUerPic(@Part List<MultipartBody.Part> partList);

    /**
     * 修改昵称
     *
     * @param nickname
     * @return
     */
    @FormUrlEncoded
    @POST("volunteer/resetnickname.action")
    Observable<BaseEntity<String>> alertUerNickname(@Field("nickname") String nickname, @Field("vid") String vId);

    /**
     * 修改手机号码
     *
     * @param phoneNumber
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("volunteer/resetmobile.action")
    Observable<BaseEntity<String>> alertUserPhoneNumber(@Field("mobile") String phoneNumber,
                                                    @Field("code") String code,
                                                    @Field("username") String userName);

    /**
     * 修改性别
     *
     * @param gender
     * @return
     */
    @FormUrlEncoded
    @POST("volunteer/resetgender.action")
    Observable<BaseEntity<String>> alertUserGender(@Field("gender") String gender, @Field("vid") String vId);

    /**
     * 修改兴趣爱好
     *
     * @param hobbies
     * @return
     */
    @FormUrlEncoded
    @POST("volunteer/resethobbies.action")
    Observable<BaseEntity<String>> alertUserHobbies(@Field("hobbies") String hobbies, @Field("vid") String vId);

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
     * 获取积分
     * @param vid
     * @return
     */
    @GET("volunteer/viewIntegral.action")
    Observable<BaseEntity<Float>>getIntegral(@Query("vid")String vid);
}
