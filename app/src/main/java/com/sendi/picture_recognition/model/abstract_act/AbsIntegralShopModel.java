package com.sendi.picture_recognition.model.abstract_act;

import com.sendi.picture_recognition.base.BaseModel;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.GoodsData;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/20.
 */

public abstract class AbsIntegralShopModel extends BaseModel {
    /**
     * 获取商品列表
     * @return
     */
    public abstract Observable<BaseEntity<List<GoodsData>>> getGoodsData();

    /**
     * 提交购买商品信息
     * @param uId
     * @param gId
     * @param integral
     * @param address
     * @param phone
     * @param name
     * @return
     */
    public abstract Observable<BaseEntity<String>> submitGoodsData(String uId,String gId,
                                                                   String integral,String address,
                                                                   String phone,String name);

}
