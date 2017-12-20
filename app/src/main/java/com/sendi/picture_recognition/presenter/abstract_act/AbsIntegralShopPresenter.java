package com.sendi.picture_recognition.presenter.abstract_act;

import com.sendi.picture_recognition.base.BasePresenter;
import com.sendi.picture_recognition.view.activity.abs.IntegralShopView;

/**
 * Created by Administrator on 2017/12/20.
 */

public abstract class AbsIntegralShopPresenter extends BasePresenter<IntegralShopView> {

    public abstract void shoeGoodsData();

    public abstract void submitGoodsData(String uId,String gId,
                                         String integral,String address,
                                         String phone,String name);

}
