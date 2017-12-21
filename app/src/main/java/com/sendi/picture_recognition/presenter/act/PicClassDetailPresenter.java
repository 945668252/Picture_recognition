package com.sendi.picture_recognition.presenter.act;

import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.model.abstract_act.AbsPicClassDetailModel;
import com.sendi.picture_recognition.model.act.PicClassifyModel;
import com.sendi.picture_recognition.presenter.abstract_act.AbsPicCLassDetailPresenter;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */

public class PicClassDetailPresenter extends AbsPicCLassDetailPresenter {

    private AbsPicClassDetailModel mModel;

    public PicClassDetailPresenter(){
        mModel=new PicClassifyModel();
    }

    @Override
    public void getDetailClassPic(String uId, String classify, String lg) {
        mModel.getDiatailClassPic(uId, classify, lg)
                .compose(mModel.<BaseEntity<List<HomePicInfo>>>setThread())
                .subscribe(new BaseObserver<List<HomePicInfo>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<HomePicInfo>> t) throws Exception {
                        getView().showPicData(t.getData());
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
