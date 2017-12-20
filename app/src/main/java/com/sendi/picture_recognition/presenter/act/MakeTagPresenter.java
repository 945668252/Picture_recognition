package com.sendi.picture_recognition.presenter.act;

import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.model.abstract_act.AbsMakeTagModel;
import com.sendi.picture_recognition.model.act.MakeTagModel;
import com.sendi.picture_recognition.presenter.abstract_act.AbsMakeTagPresenter;

/**
 * Created by Administrator on 2017/12/20.
 */

public class MakeTagPresenter extends AbsMakeTagPresenter {

    private AbsMakeTagModel mModel;

    public MakeTagPresenter(){
        mModel=new MakeTagModel();
    }

    @Override
    public void submitTags(String uId, String imgId, String tags) {
        mModel.submitTags(uId, imgId, tags)
                .compose(mModel.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        getView().showDialog();
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                    @Override
                    protected void onRequestEnd(boolean isSuccess) {
                        super.onRequestEnd(isSuccess);
                        if (!isSuccess){
                            getView().showErrorDialog();
                        }
                    }
                });
    }

    @Override
    public void transToString(StringBuffer tagStrings) {
       String result= mModel.transToString(tagStrings);
       getView().setResultToET(result);
    }
}
