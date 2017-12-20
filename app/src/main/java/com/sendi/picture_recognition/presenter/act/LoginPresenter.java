package com.sendi.picture_recognition.presenter.act;

import android.content.Context;

import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.base.BasePresenter;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.User;
import com.sendi.picture_recognition.model.act.LoginModel;
import com.sendi.picture_recognition.view.activity.LoginActivity;

/**
 * Created by Administrator on 2017/12/20.
 */

public class LoginPresenter extends BasePresenter<LoginActivity> {
    private LoginModel mModel;

    public LoginPresenter(){
        mModel=new LoginModel();
    }

    public void login(String username,String psw){
        mModel.login(username, psw)
                .compose(mModel.<BaseEntity<User>>setThread())
                .subscribe(new BaseObserver<User>() {
                    @Override
                    protected void onSuccess(BaseEntity<User> t) throws Exception {
                        getView().loginSuccess(t.getData());
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

    public void saveUserInfo(User user, Context context){
        mModel.saveUserInfo(user,context);
    }
}
