package com.sendi.picture_recognition.base;

import com.sendi.picture_recognition.presenter.IPresenter;
import com.sendi.picture_recognition.view.IView;

/**
 * Created by Administrator on 2017/12/5.
 */

public abstract class BasePresenter<T extends IView> implements IPresenter<T> {
    protected T mView;

    @Override
    public void bindView(T view) {
        if (view==null)
            throw new NullPointerException("param view must not null.");
        this.mView=view;
    }

    @Override
    public void detachView() {
        if (this.mView!=null)
            this.mView=null;
    }

    public T getView(){
        return this.mView;
    }
}
