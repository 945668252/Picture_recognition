package com.sendi.picture_recognition.presenter.act;

import android.util.Log;

import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ChallengeData;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.bean.SingleChallengeData;
import com.sendi.picture_recognition.model.abstract_act.AbsChallengeModel;
import com.sendi.picture_recognition.model.act.ChallengeModel;
import com.sendi.picture_recognition.presenter.abstract_act.AbsChallengePresenter;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/7.
 */

public class ChallengePresenter extends AbsChallengePresenter {

    private AbsChallengeModel mModel;
    private ChallengeData challengeData;

    public ChallengePresenter() {
        mModel = new ChallengeModel();
    }

    @Override
    public void getPkInfo(String... ids) {
        if (ids.length > 1) {
            mModel.getSinglePkData(ids[0], ids[1], ids[2])
                    .compose(mModel.<BaseEntity<SingleChallengeData>>setThread())
                    .subscribe(new BaseObserver<SingleChallengeData>() {
                        @Override
                        protected void onSuccess(BaseEntity<SingleChallengeData> t) throws Exception {
                            getView().showPkData(t.getData());
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
        } else {
            mModel.getMorePKInfo(ids[0])
                    .compose(mModel.<BaseEntity<List<HomePicInfo>>>setThread())
                    .subscribe(new BaseObserver<List<HomePicInfo>>() {
                        @Override
                        protected void onSuccess(BaseEntity<List<HomePicInfo>> t) throws Exception {
                            getView().showPkData(t.getData());
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

    @Override
    public void submit(ChallengeData challengeData, Type type) {
        this.challengeData = challengeData;
        this.submit(type)
                .compose(mModel.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        getView().onSuccess(t.getData());
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                    @Override
                    protected void onRequestEnd(boolean isSuccess) {
                        super.onRequestEnd(isSuccess);
                        Log.i("TAG", "onRequestEnd: "+isSuccess);
                    }
                });
    }

    private Observable<BaseEntity<String>> submit(Type type) {
        switch (type) {
            case SINGLE:
                return mModel.submitSinglePkData(challengeData);
            case MORE:
                return mModel.submitMorePkData(challengeData);
            case ACCEPT:
                return mModel.submitAcceptPkData(challengeData);
        }
        return null;
    }

    @Override
    public void getSinglePkData(String pkId) {

        mModel.getSinglePkData(pkId)
                .compose(mModel.<BaseEntity<SingleChallengeData>>setThread())
                .subscribe(new BaseObserver<SingleChallengeData>() {
                    @Override
                    protected void onSuccess(BaseEntity<SingleChallengeData> t) throws Exception {
                        getView().showPkData(t.getData());
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
