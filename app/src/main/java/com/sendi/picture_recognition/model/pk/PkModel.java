package com.sendi.picture_recognition.model.pk;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ChallengeHistory;
import com.sendi.picture_recognition.bean.MorePKInfo;
import com.sendi.picture_recognition.bean.UserChartInfo;
import com.sendi.picture_recognition.service.PkService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/6.
 */

public class PkModel extends AbstractPkModel {

    private PkService mService=this.createService(PkService.class);

    @Override
    public Observable<BaseEntity<List<UserChartInfo>>> getChartData(int page) {
        return mService.getChartData(page);
    }

    @Override
    public Observable<BaseEntity<List<ChallengeHistory>>> getPkHistory(String vId) {
        return mService.getPkHistory(vId);
    }

    @Override
    public Observable<BaseEntity<List<MorePKInfo>>> getMorePKInfoList(String vId) {
        return mService.getMorePKInfoList(vId);
    }

    @Override
    public Observable<BaseEntity<String>> getCreateResult(String vId) {
        return mService.getCreateResult(vId);
    }


}
