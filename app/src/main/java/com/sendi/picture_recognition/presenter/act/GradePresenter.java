package com.sendi.picture_recognition.presenter.act;

import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.model.abstract_act.AbsGradeModel;
import com.sendi.picture_recognition.model.act.GradeModel;
import com.sendi.picture_recognition.presenter.abstract_act.AbsGradepresenter;

/**
 * Created by Administrator on 2017/12/8.
 */

public class GradePresenter extends AbsGradepresenter {
    private AbsGradeModel mModel;

    public GradePresenter(){
        mModel=new GradeModel();
    }
    @Override
    public void getIntegral(String vId) {
        mModel.getIntegral(vId)
                .compose(mModel.<BaseEntity<Float>>setThread())
                .subscribe(new BaseObserver<Float>() {
                    @Override
                    protected void onSuccess(BaseEntity<Float> t) throws Exception {
                        getView().showIntegral(t.getData());
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }
}
