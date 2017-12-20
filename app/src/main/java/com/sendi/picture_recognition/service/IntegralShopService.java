package com.sendi.picture_recognition.service;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.GoodsData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/12/20.
 */

public interface IntegralShopService {

    /**
     * @return 获取商品
     */
    @GET("store/pushgoods.action")
    Observable<BaseEntity<List<GoodsData>>> getGoodsData();

    /**
     * @return 提交商品信息
     */
    @FormUrlEncoded
    @POST("store/buygoods.action")
    Observable<BaseEntity<String>> submitGoodsData(@Field("vid") String vId, @Field("gid") String gId,
                                                   @Field("integral") String integral, @Field("address") String address,
                                                   @Field("phone") String phone, @Field("name") String name);
}
