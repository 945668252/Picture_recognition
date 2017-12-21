package com.sendi.picture_recognition.presenter.abstract_act;

import com.sendi.picture_recognition.base.BasePresenter;
import com.sendi.picture_recognition.view.activity.abs.AbsPkResultView;

/**
 * Created by Administrator on 2017/12/21.
 */

public abstract class AbsPkResultPresenter extends BasePresenter<AbsPkResultView> {
    /**
     * 加载pk结果
     * @param mId
     * @param lg
     */
    public abstract void  getPkResultData(String mId,String lg);

}
