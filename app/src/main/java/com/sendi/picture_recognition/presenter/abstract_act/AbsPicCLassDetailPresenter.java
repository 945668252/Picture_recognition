package com.sendi.picture_recognition.presenter.abstract_act;

import com.sendi.picture_recognition.base.BasePresenter;
import com.sendi.picture_recognition.view.activity.abs.PicClassDetailView;

/**
 * Created by Administrator on 2017/12/21.
 */

public abstract class AbsPicCLassDetailPresenter extends BasePresenter<PicClassDetailView> {

    public abstract void getDetailClassPic(String uId, String classify, String lg) ;

}
