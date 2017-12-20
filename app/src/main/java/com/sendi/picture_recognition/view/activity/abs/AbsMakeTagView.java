package com.sendi.picture_recognition.view.activity.abs;

import com.sendi.picture_recognition.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/20.
 */

public abstract class AbsMakeTagView extends BaseActivity {

    public abstract void showDialog();

    public abstract void showErrorDialog();

    public abstract void setResultToET(String resultStr);
}
