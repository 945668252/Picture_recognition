package com.sendi.picture_recognition.model.abstract_act;

import android.content.Context;

import com.sendi.picture_recognition.base.BaseModel;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.RecordData;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/20.
 */

public abstract class AbsRecordModel extends BaseModel {
    /**
     * 获取网络记录数据
     * @param uId
     * @return
     */
    public abstract Observable<BaseEntity<List<RecordData>>> getRecordData(String uId);

    /**
     * 获取缓存在本地的数据
     * @return
     */
    public abstract List<RecordData>getCacheData(Context context);

    /**
     * 缓存数据
     * @param recordDatas
     */
    public abstract  void cacheData(List<RecordData> recordDatas, Context context);
}
