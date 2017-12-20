package com.sendi.picture_recognition.presenter;

import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.GoodsData;
import com.sendi.picture_recognition.model.act.IntegralShopModel;
import com.sendi.picture_recognition.presenter.abstract_act.AbsIntegralShopPresenter;
import com.sendi.picture_recognition.service.IntegralShopService;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class IntegralShopPresenter extends AbsIntegralShopPresenter {

    private IntegralShopModel mModel;

    public IntegralShopPresenter(){
        mModel=new IntegralShopModel();
    }

    @Override
    public void shoeGoodsData() {
        mModel.getGoodsData()
                .compose(mModel.<BaseEntity<List<GoodsData>>>setThread())
                .subscribe(new BaseObserver<List<GoodsData>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<GoodsData>> t) throws Exception {
                        getView().showGoodsData(t.getData());
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                    @Override
                    protected void onRequestEnd(boolean isSuccess) {
                        super.onRequestEnd(isSuccess);
                        getView().hideLoading(isSuccess);
                    }
                });
    }

    @Override
    public void submitGoodsData(String uId, String gId, String integral, String address, String phone, String name) {
        mModel.submitGoodsData(uId, gId, integral, address, phone, name)
                .compose(mModel.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        getView().submitSuccess(t.getData());
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                    @Override
                    protected void onRequestEnd(boolean isSuccess) {
                        super.onRequestEnd(isSuccess);
                        getView().hideLoading(isSuccess);
                    }
                });
    }
}
