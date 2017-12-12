package com.sendi.picture_recognition.presenter.home;

import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.model.home.AbstractHomeModel;
import com.sendi.picture_recognition.model.home.HomeModel;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */

public class HomePresenter extends AbstractHomePresenter {

    private AbstractHomeModel mModel;

    public HomePresenter(){
        mModel=new HomeModel();
    }

    @Override
    public void loadPicData(String userId, String languageType) {
        mModel.loadPicData(userId,languageType)
                .compose(mModel.<BaseEntity<List<HomePicInfo>>>setThread())
                .subscribe(new BaseObserver<List<HomePicInfo>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<HomePicInfo>> t) throws Exception {
                        getView().showPic(t.getData());
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                    @Override
                    protected void onRequestStart() {
                        super.onRequestStart();
                        getView().showLoading();
                    }

                    @Override
                    protected void onRequestEnd(boolean isSuccess) {
                        super.onRequestEnd(isSuccess);
                        getView().hideLoading(isSuccess);
                    }
                });
    }
}
