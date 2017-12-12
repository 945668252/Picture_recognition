package com.sendi.picture_recognition.presenter.search;

import com.sendi.picture_recognition.base.BasePager;
import com.sendi.picture_recognition.base.BasePresenter;

/**
 * Created by Administrator on 2017/12/5.
 */

public abstract class AbstractSearchPresenter extends BasePresenter<BasePager> {
    //加载类别
   public abstract void getClassPicData(String userId);

//   加载热门
    public abstract void getHotPicData(String userId, String lg);

//    加载最新
    public abstract void getNewPicData(String userId, String lg);
}
