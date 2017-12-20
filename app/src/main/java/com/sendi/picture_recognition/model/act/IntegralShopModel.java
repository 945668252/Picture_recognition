package com.sendi.picture_recognition.model.act;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.GoodsData;
import com.sendi.picture_recognition.model.abstract_act.AbsIntegralShopModel;
import com.sendi.picture_recognition.service.IntegralShopService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/20.
 */

public class IntegralShopModel extends AbsIntegralShopModel {

    private IntegralShopService mIntegralShopService=this.createService(IntegralShopService.class);

    @Override
    public Observable<BaseEntity<List<GoodsData>>> getGoodsData() {
        return mIntegralShopService.getGoodsData();
    }

    @Override
    public Observable<BaseEntity<String>> submitGoodsData(String uId, String gId, String integral, String address, String phone, String name) {
        return mIntegralShopService.submitGoodsData(uId, gId, integral, address, phone, name);
    }
}
