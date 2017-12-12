package com.sendi.picture_recognition.presenter.pk;

import com.sendi.picture_recognition.base.BasePresenter;
import com.sendi.picture_recognition.view.fragment.home.pk.AbstractPkFragment;

/**
 * Created by Administrator on 2017/12/6.
 */

public abstract class AbstractPkPresenter extends BasePresenter<AbstractPkFragment> {
    //排行榜数据
    public abstract void getChartData(int page);
    //挑战记录数据
    public abstract void getPkHistory(String vId);
    //多人挑战数据
    public abstract void getMorePKInfoList(String vId);
    //创建多人挑战
    public abstract void getCreateResult(String vId);
}
