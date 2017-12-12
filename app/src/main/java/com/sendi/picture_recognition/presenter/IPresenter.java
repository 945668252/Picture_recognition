package com.sendi.picture_recognition.presenter;

import com.sendi.picture_recognition.view.IView;

/**
 * Created by Administrator on 2017/12/5.
 */

public interface IPresenter<T extends IView> {
    /**
     * 绑定View
     * @param view
     */
    void bindView(T view);

    /**
     * 解绑View
     */
    void detachView();
}
