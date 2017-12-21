package com.sendi.picture_recognition.presenter.act;

import android.content.Context;
import android.util.Log;

import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.RecordData;
import com.sendi.picture_recognition.model.act.RecordModel;
import com.sendi.picture_recognition.presenter.abstract_act.AbsRecordPresenter;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class RecordPresenter extends AbsRecordPresenter {

    private RecordModel mModel;

    public RecordPresenter() {
        mModel = new RecordModel();
    }

    @Override
    public void getRecordData(String uId) {
        mModel.getRecordData(uId)
                .compose(mModel.<BaseEntity<List<RecordData>>>setThread())
                .subscribe(new BaseObserver<List<RecordData>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<RecordData>> t) throws Exception {
                        getView().showRecord(t.getData());
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
    public void getCacheData(Context context) {

        List<RecordData> recordData = mModel.getCacheData(context);

        if (recordData != null)
            getView().showRecord(recordData);
    }

    @Override
    public void cacheData(List<RecordData> recordDatas, Context context) {
        mModel.cacheData(recordDatas, context);
    }
}
