package com.sendi.picture_recognition.presenter.act;

import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.PKResultData;
import com.sendi.picture_recognition.model.abstract_act.AbsPkResultModel;
import com.sendi.picture_recognition.model.act.PkResultModel;
import com.sendi.picture_recognition.presenter.abstract_act.AbsPkResultPresenter;

/**
 * Created by Administrator on 2017/12/21.
 */

public class PkResultPresenter extends AbsPkResultPresenter {

    private AbsPkResultModel mPkResultModel;

    public PkResultPresenter(){
        mPkResultModel=new PkResultModel();
    }
    @Override
    public void getPkResultData(String mId, String lg) {
        mPkResultModel.getPkResultData(mId,lg)
                .compose(mPkResultModel.<BaseEntity<PKResultData>>setThread())
                .subscribe(new BaseObserver<PKResultData>() {
                    @Override
                    protected void onSuccess(BaseEntity<PKResultData> t) throws Exception {
                        getView().showPkResult(t.getData());
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
}
