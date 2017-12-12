package com.sendi.picture_recognition.presenter.pk;

import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ChallengeHistory;
import com.sendi.picture_recognition.bean.MorePKInfo;
import com.sendi.picture_recognition.bean.UserChartInfo;
import com.sendi.picture_recognition.model.pk.PkModel;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class PkPresenter extends AbstractPkPresenter {

    private PkModel mModel;

    public PkPresenter(){
        mModel=new PkModel();
    }

    @Override
    public void getChartData(int page) {
        mModel.getChartData(page)
                .compose(mModel.<BaseEntity<List<UserChartInfo>>>setThread())
                .subscribe(new BaseObserver<List<UserChartInfo>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<UserChartInfo>> t) throws Exception {
                        getView().showData(t.getData());
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

    @Override
    public void getPkHistory(String vId) {
        mModel.getPkHistory(vId)
                .compose(mModel.<BaseEntity<List<ChallengeHistory>>>setThread())
                .subscribe(new BaseObserver<List<ChallengeHistory>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<ChallengeHistory>> t) throws Exception {
                        getView().showData(t.getData());
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

    @Override
    public void getMorePKInfoList(String vId) {
        mModel.getMorePKInfoList(vId)
                .compose(mModel.<BaseEntity<List<MorePKInfo>>>setThread())
                .subscribe(new BaseObserver<List<MorePKInfo>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<MorePKInfo>> t) throws Exception {
                        getView().showData(t.getData());
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

    @Override
    public void getCreateResult(String vId) {
        mModel.getCreateResult(vId)
                .compose(mModel.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        getView().createPk(t.getData(),true);
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        getView().createPk("",false);
                    }
                });
    }
}
