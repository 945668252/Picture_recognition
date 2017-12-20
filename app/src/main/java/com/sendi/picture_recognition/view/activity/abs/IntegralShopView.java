package com.sendi.picture_recognition.view.activity.abs;

import com.sendi.picture_recognition.base.BaseActivity;
import com.sendi.picture_recognition.bean.GoodsData;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public abstract class IntegralShopView extends BaseActivity {

    public abstract void showGoodsData(List<GoodsData> goodsData);

    public abstract void submitSuccess(String result);
}
