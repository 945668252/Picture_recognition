package com.sendi.picture_recognition.service;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.RecordData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/20.
 */

public interface RecordService {
    /**
     * 获得历史记录
     * @return
     */
    @GET("volunteer/viewHistory.action")
    Observable<BaseEntity<List<RecordData>>> getRecordData(@Query("vid") String vId);
}
