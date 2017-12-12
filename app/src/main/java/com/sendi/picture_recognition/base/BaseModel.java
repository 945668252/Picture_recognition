package com.sendi.picture_recognition.base;

import com.sendi.picture_recognition.model.IModel;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/12/5.
 */

public abstract class BaseModel implements IModel{

    public <T> ObservableTransformer<T,T> setThread(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public <T> T createService(Class<T> tClass){
        checkService(tClass);

        return RetrofitFactory.getService(tClass);
    }

    /**
     * 检查接口
     * @param tClass
     * @param <T>
     */
    private <T> void checkService(Class<T> tClass) {
        if (tClass==null)
            throw  new NullPointerException("service must not null.");
        if (!tClass.isInterface())
            throw new IllegalArgumentException("service must be interface.");
        if (tClass.getInterfaces().length>0)
            throw new IllegalArgumentException("service interfaces must not extends other interface.");
    }
}
