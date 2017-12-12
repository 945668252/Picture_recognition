package com.sendi.picture_recognition.utils.httputils;


import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ChallengeData;
import com.sendi.picture_recognition.bean.ChallengeHistory;
import com.sendi.picture_recognition.bean.ClassData;
import com.sendi.picture_recognition.bean.GoodsData;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.bean.MorePKInfo;
import com.sendi.picture_recognition.bean.PKResultData;
import com.sendi.picture_recognition.bean.RecordData;
import com.sendi.picture_recognition.bean.SearchPicData;
import com.sendi.picture_recognition.bean.SingleChallengeData;
import com.sendi.picture_recognition.bean.User;
import com.sendi.picture_recognition.bean.UserChartInfo;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/4/29.
 */

public interface ApiServer {
    /**
     * 注册
     *
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
     * 获取验证码
     *
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("volunteer/verify.action")
    Observable<BaseEntity<String>> getCode(@Field("mobile") String mobile);

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

    /**
     * 获取积分
     * @param vid
     * @return
     */
    @GET("volunteer/viewIntegral.action")
    Observable<BaseEntity<Float>>getIntegral(@Query("vid")String vid);

    /**
     * 提交标签
     *
     * @param userId
     * @param imgId
     * @param tags
     * @return
     */
    @FormUrlEncoded
    @POST("volunteer/tagging.action")
    Observable<BaseEntity<String>> submitTags(@Field("vid") String userId
            , @Field("pid") String imgId
            , @Field("labels") String tags);

    /**
     * 首页拉取图片信息
     *
     * @return
     */
    @GET("picture/push.action")
    Observable<BaseEntity<List<HomePicInfo>>> getHomePicData(@Query("vid") String userId
            , @Query("lg") String lg);

    /**
     * 获取图片及图片的类
     *
     * @return
     */
    @GET("picture/hotpush.action")
    Observable<BaseEntity<List<SearchPicData>>> getHotPicData(@Query("vid") String userId,
                                                              @Query("lg") String language);

    /**
     * 获取图片分类
     *
     * @return
     */
    @GET("picture/firstpush.action")
    Observable<BaseEntity<List<ClassData>>> getClassPicData(@Query("vid") String userId);

    /**
     * 获取最新图片
     *
     * @return
     */
    @GET("picture/newpush.action")
    Observable<BaseEntity<List<SearchPicData>>> getNewPicData(@Query("vid") String userId,
                                                              @Query("lg") String language);


    @GET("picture/classifypush.action")
    Observable<BaseEntity<List<HomePicInfo>>> getDiatailClassPic(@Query("vid") String userId,
                                                                 @Query("classify") String classify,
                                                                 @Query("lg") String language);

    /**
     * 排行榜数据
     * @return
     * http://192.168.1.112:8080/picture_recognition/volunteer/ranking.action?page=0
     */
    @GET("volunteer/ranking.action")
    Observable<BaseEntity<List<UserChartInfo>>>getChartData(@Query("page")int page);

    /**
     * 提交要pk的信息
     * @param myId
     * @param oId
     * @param imgCount
     * @return
     * 双人PK发起挑战  http://192.168.1.112:8080/picture_recognition/volunteer/compete.action?challenger=1&invitee=2&number=4
     */
    @FormUrlEncoded
    @POST("volunteer/compete.action")
    Observable<BaseEntity<SingleChallengeData>>getPkData(@Field("challenger")String myId, @Field("invitee")String oId,
                                                            @Field("number")String imgCount);



    @GET("volunteer/accept.action")
    Observable<BaseEntity<SingleChallengeData>>getSinglePkData(@Query("pkId")String pkId);

    /**
     * 一条多人pk的数据
     *
     * @param pId
     * @return
     */
    @GET("picture/pushmoreactionpic.action")
    Observable<BaseEntity<List<HomePicInfo>>> getMorePKInfo(@Query("mid") String pId);
    /**
     * 多人pk列表数据
     * @param vId
     * @return
     */
    @GET("picture/selectmoreaction.action")
    Observable<BaseEntity<List<MorePKInfo>>> getMorePKInfoList(@Query("vid") String vId);

    /**
     * 创建多人PK
     * @param vId
     * @return http://localhost:8080/picture_recognition/picture/creatmoreaction.action?vid=15
     */
    @GET("picture/creatmoreaction.action")
    Observable<BaseEntity<String>> getCreateResult(@Query("vid") String vId);

    /**
     * 提交挑战的数据(发起挑战)
     *  http://192.168.1.112:8080/picture_recognition/volunteer/firstRound.action?json
     * @param challengeData
     * @return
     */
    @POST("volunteer/firstRound.action")
    Observable<BaseEntity<String>> submitPkData(@Body ChallengeData challengeData);

    /**
     * 提交挑战的数据(接受挑战)
     *  http://192.168.1.112:8080/picture_recognition/volunteer/firstRound.action?json
     * @param challengeData
     * @return
     */
    @POST("volunteer/secondRound.action")
    Observable<BaseEntity<String>> submitAcceptPkData(@Body ChallengeData challengeData);

    /**
     * 提交挑战的数据
     *  http://192.168.1.112:8080/picture_recognition/volunteer/firstRound.action?json
     * @param challengeData
     * @return
     */
    @POST("mcompetition/compete.action")
    Observable<BaseEntity<String>> submitMorePkData(@Body ChallengeData challengeData);

    /**
     * 获取双人pk结果
     * @param pkId
     * @return
     * 查看具体的挑战结果http://192.168.1.112:8080/picture_recognition/volunteer/detail.action?pkId=1
     */
    @GET("volunteer/detail.action")
    Observable<BaseEntity<PKResultData>>getPkResultData(@Query("pkId")String pkId,@Query("vid")String vId);

    /**
     * pk记录
     * @param vId
     * @return
     *
     */
    @GET("volunteer/viewDMRecords.action")
    Observable<BaseEntity<List<ChallengeHistory>>>getPkHistory(@Query("vid")String vId);

    /**
     * http://localhost:8080/picture_recognition/store/pushgoods.action
     *
     * @return 获取商品
     */
    @GET("store/pushgoods.action")
    Observable<BaseEntity<List<GoodsData>>> getGoodsData();

    /**
     * http://192.168.1.101:8080/picture_recognition/store/buygoods.action?vid=16&gid=2&integral=10&address=a&phone=13434936402&name=2
     *
     * @return 提交商品信息
     */
    @FormUrlEncoded
    @POST("store/buygoods.action")
    Observable<BaseEntity<String>> submitGoodsData(@Field("vid") String vId, @Field("gid") String gId,
                                                   @Field("integral") String integral, @Field("address") String address,
                                                   @Field("phone") String phone, @Field("name") String name);

    /**
     * 删除标签
     * 删除标签  http://192.168.1.112:8080/picture_recognition/volunteer/delete.action?pid=1&vid=1&label=标签
     */
    @FormUrlEncoded
    @POST("volunteer/delete.action")
    Observable<BaseEntity<String>> deleteTag(@Field("vid") String vId,
                                             @Field("pid") String pId, @Field("label") String tag);
    /**
     * 自定义增加
     * 新添加标签  http://192.168.1.112:8080/picture_recognition/volunteer/newAdd.action?pid=1&vid=1&label=标签
     */
    @FormUrlEncoded
    @POST("volunteer/newAdd.action")
    Observable<BaseEntity<String>> addEditTag(@Field("vid") String vId,
                                             @Field("pid") String pId, @Field("label")String tag);
    /**
     * 从系统推荐添加
     * 添加标签   http://192.168.1.112:8080/picture_recognition/volunteer/add.action?pid=1&vid=1&label=原有标签
     */
    @FormUrlEncoded
    @POST("volunteer/add.action")
    Observable<BaseEntity<String>> addCommonTag(@Field("vid") String vId,
                                             @Field("pid") String pId, @Field("label")String tag);
    /**
     * 修改
     * 修改标签  http://192.168.1.112:8080/picture_recognition/volunteer/newAdd.action?pid=1&vid=1&before_label=旧标签&after_label=新标签
     */
    @FormUrlEncoded
    @POST("volunteer/modify.action")
    Observable<BaseEntity<String>>alertTag(@Field("vid") String vId, @Field("pid") String pId,
                                           @Field("before_label")String oldTag,@Field("after_label")String tag);

    /**
     * 获得历史记录
     * @return
     */
    @GET("volunteer/viewHistory.action")
    Observable<BaseEntity<List<RecordData>>> getRecordData(@Query("vid") String vId);

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
    Observable<BaseEntity<String>> alertPhoneNumber(@Field("mobile") String phoneNumber,
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
    Observable<BaseEntity<String>> alertGender(@Field("gender") String gender, @Field("vid") String vId);

    /**
     * 修改兴趣爱好
     *
     * @param hobbies
     * @return
     */
    @FormUrlEncoded
    @POST("volunteer/resethobbies.action")
    Observable<BaseEntity<String>> alertHobbies(@Field("hobbies") String hobbies, @Field("vid") String vId);



}
