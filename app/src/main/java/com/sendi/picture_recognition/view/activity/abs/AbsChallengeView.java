package com.sendi.picture_recognition.view.activity.abs;

import com.sendi.picture_recognition.base.BaseActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */

public abstract class AbsChallengeView<T> extends BaseActivity {

    public abstract void showPkData(T pkData);

    public abstract void onSuccess(String msg);

}
