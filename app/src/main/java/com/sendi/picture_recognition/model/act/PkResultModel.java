package com.sendi.picture_recognition.model.act;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.PKResultData;
import com.sendi.picture_recognition.model.abstract_act.AbsPkResultModel;
import com.sendi.picture_recognition.service.PkService;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/21.
 */

public class PkResultModel extends AbsPkResultModel {

    private PkService mService=this.createService(PkService.class);

    @Override
    public Observable<BaseEntity<PKResultData>> getPkResultData(String pkId, String vId) {
        return mService.getPkResultData(pkId, vId);
    }
}
