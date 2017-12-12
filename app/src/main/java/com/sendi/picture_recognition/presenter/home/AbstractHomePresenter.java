package com.sendi.picture_recognition.presenter.home;

import com.sendi.picture_recognition.base.BasePresenter;
import com.sendi.picture_recognition.view.fragment.home.AbstractHomeFragment;

/**
 * Created by Administrator on 2017/12/5.
 */

public abstract class AbstractHomePresenter  extends BasePresenter<AbstractHomeFragment>{

    public abstract void loadPicData(String userId,String languageType);

}
