package com.sendi.picture_recognition.presenter.search;

import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ClassData;
import com.sendi.picture_recognition.bean.SearchPicData;
import com.sendi.picture_recognition.model.search.SearchModel;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */

public class SearchPresenter extends AbstractSearchPresenter {

    private SearchModel mModel;

    public SearchPresenter(){
        mModel=new SearchModel();
    }

    @Override
    public void getClassPicData(String userId) {
        mModel.getClassPicData(userId)
                .compose(mModel.<BaseEntity<List<ClassData>>>setThread())
                .subscribe(new BaseObserver<List<ClassData>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<ClassData>> t) throws Exception {
                        getView().showPicData(t.getData());
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
    public void getHotPicData(String userId, String lg) {
        mModel.getHotPicData(userId, lg)
                .compose(mModel.<BaseEntity<List<SearchPicData>>>setThread())
                .subscribe(new BaseObserver<List<SearchPicData>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<SearchPicData>> t) throws Exception {
                        getView().showPicData(t.getData());
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
    public void getNewPicData(String userId, String lg) {
        mModel.getNewPicData(userId, lg)
                .compose(mModel.<BaseEntity<List<SearchPicData>>>setThread())
                .subscribe(new BaseObserver<List<SearchPicData>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<SearchPicData>> t) throws Exception {
                        getView().showPicData(t.getData());
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
