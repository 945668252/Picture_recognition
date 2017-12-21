package com.sendi.picture_recognition.model.abstract_act;

import com.sendi.picture_recognition.base.BaseModel;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.PKResultData;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/21.
 */

public abstract class AbsPkResultModel extends BaseModel {

    /**
     * 双人pk结果
     * @param pkId
     * @param vId
     * @return
     */
    public abstract Observable<BaseEntity<PKResultData>> getPkResultData(String pkId,String vId);
}
