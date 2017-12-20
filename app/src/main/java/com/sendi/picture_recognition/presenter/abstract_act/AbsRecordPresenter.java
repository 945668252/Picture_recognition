package com.sendi.picture_recognition.presenter.abstract_act;

import android.content.Context;

import com.sendi.picture_recognition.base.BasePresenter;
import com.sendi.picture_recognition.bean.RecordData;
import com.sendi.picture_recognition.view.activity.abs.AbsRecordView;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public abstract class AbsRecordPresenter extends BasePresenter<AbsRecordView> {
    /**
     * 从网络获取数据
     * @param uId
     */
    public abstract void getRecordData(String uId);

    /**
     * 从缓存获取数据
      */
    public abstract void getCacheData(Context context);

    /**
     * 缓存数据
     * @param recordDatas
     */
    public abstract void cacheData(List<RecordData> recordDatas,Context context);
}
