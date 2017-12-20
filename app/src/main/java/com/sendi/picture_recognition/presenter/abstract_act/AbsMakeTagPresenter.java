package com.sendi.picture_recognition.presenter.abstract_act;

import com.sendi.picture_recognition.base.BasePresenter;
import com.sendi.picture_recognition.view.activity.abs.AbsMakeTagView;

/**
 * Created by Administrator on 2017/12/20.
 */

public abstract class AbsMakeTagPresenter extends BasePresenter<AbsMakeTagView> {

    public abstract void submitTags(String uId, String imgId, String tags);

    public abstract void transToString(StringBuffer tagStrings);

}
