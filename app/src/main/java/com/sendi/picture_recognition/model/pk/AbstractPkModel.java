package com.sendi.picture_recognition.model.pk;

import com.sendi.picture_recognition.base.BaseModel;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ChallengeHistory;
import com.sendi.picture_recognition.bean.MorePKInfo;
import com.sendi.picture_recognition.bean.UserChartInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/6.
 */

public abstract class AbstractPkModel extends BaseModel {
    //排行榜数据
    public abstract Observable<BaseEntity<List<UserChartInfo>>> getChartData(int page);

    //挑战记录数据
    public abstract Observable<BaseEntity<List<ChallengeHistory>>>getPkHistory(String vId);

    //多人挑战数据
    public abstract Observable<BaseEntity<List<MorePKInfo>>> getMorePKInfoList(String vId);

    //创建挑战
    public abstract Observable<BaseEntity<String>>getCreateResult(String vId);
}
