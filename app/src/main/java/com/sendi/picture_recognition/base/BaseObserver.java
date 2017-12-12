package com.sendi.picture_recognition.base;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.util.Log;


import com.sendi.picture_recognition.bean.BaseEntity;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/4/26.
 */

public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {

    protected Context mContext;

    public BaseObserver(Context context) {
        mContext = context;
    }

    public BaseObserver() {
    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    @Override
    public void onNext(BaseEntity<T> tBaseEntity) {
       onRequestEnd(true);
        if (tBaseEntity.isSuccess()){
            try {
                onSuccess(tBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                onCodeError(tBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {

        onRequestEnd(false);
        try {
            if (e instanceof ConnectException
                    ||e instanceof TimeoutException
                    ||e instanceof NetworkErrorException
                    ||e instanceof UnknownHostException){
                Log.i("SENDI", "onError: 网络出错true");
                onConnectFailure(e,true);
            }else {
                onConnectFailure(e,false);
                Log.i("SENDI", "onError: 网络出错false");
            }
        }catch (Exception e1){
            Log.i("SENDI", "onError: 网络出错catch");
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 成功返回，但是code错误
     * @param t
     * @throws Exception
     */
    protected  void onCodeError(BaseEntity<T> t)throws Exception{

    }

    /**
     * 成功返回
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccess(BaseEntity<T> t)throws Exception;
    /**
     * 返回失败
     * @param e
     * @param isNetWorkError
     * @throws Exception
     */
    protected abstract void onConnectFailure(Throwable e,boolean isNetWorkError)throws Exception;

    protected void onRequestStart(){}
    protected void onRequestEnd(boolean isSuccess){}
}
