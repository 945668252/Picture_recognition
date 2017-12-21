package com.sendi.picture_recognition.presenter.act;

import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.RecordData;
import com.sendi.picture_recognition.model.abstract_act.AbsAlertTagsModel;
import com.sendi.picture_recognition.model.act.AlertTagsModel;
import com.sendi.picture_recognition.presenter.abstract_act.AbsAlertTagsPresenter;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */

public class AlertTagsPresenter extends AbsAlertTagsPresenter {

    private AbsAlertTagsModel mModel;

    public AlertTagsPresenter(){
        mModel=new AlertTagsModel();
    }

    @Override
    public void initData(RecordData recordData, List<String> selectedTagList, List<String> unselectedTagList) {
        mModel.initData(recordData, selectedTagList, unselectedTagList);
    }

    @Override
    public void deleteTag(String uId, String pId, String tag, final int position) {
        mModel.deleteTag(uId, pId, tag)
                .compose(mModel.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        getView().deleteTagSuccess(position);
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
    public void addCommonTag(String uId, String pId, final String tag) {
        mModel.addCommonTag(uId, pId, tag)
                .compose(mModel.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        getView().addCommonTagSuccess(tag);
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
    public void alertTags(String uId, String pId, String oldTag, final String tag, final int position) {
        mModel.alertTags(uId, pId,oldTag, tag)
                .compose(mModel.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        getView().alertTagsSuccess(t.getData(),tag,position);
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                    @Override
                    protected void onCodeError(BaseEntity<String> t) throws Exception {
                        super.onCodeError(t);
                        getView().fail(t.getData());
                    }
                });
    }

    @Override
    public void addNewTag(String uId, String pId, final String tag) {
        mModel.addNewTag(uId, pId, tag)
                .compose(mModel.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        getView().addNewTagSuccess(t.getData(),tag);
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                    @Override
                    protected void onCodeError(BaseEntity<String> t) throws Exception {
                        super.onCodeError(t);
                        getView().fail(t.getData());
                    }
                });
    }
}
