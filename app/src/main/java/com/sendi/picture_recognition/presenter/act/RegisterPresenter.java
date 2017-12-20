package com.sendi.picture_recognition.presenter.act;

import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.model.act.RegisterModel;
import com.sendi.picture_recognition.presenter.abstract_act.AbsRegisterPresenter;

/**
 * Created by Administrator on 2017/12/20.
 */

public class RegisterPresenter extends AbsRegisterPresenter {

    private RegisterModel mModel;

    public RegisterPresenter(){
        mModel=new RegisterModel();
    }

    @Override
    public void getCode(String mobileNumber) {
        mModel.getCode(mobileNumber)
                .compose(mModel.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        getView().getCodeSuccess();
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @Override
    public void register(String name, String psw, String hobbies, String sex, String mobil, String code) {
        mModel.register(name, psw, hobbies, sex, mobil, code)
                .compose(mModel.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        getView().registerSuccess();
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }
}
