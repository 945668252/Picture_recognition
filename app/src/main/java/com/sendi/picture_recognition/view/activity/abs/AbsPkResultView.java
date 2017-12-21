package com.sendi.picture_recognition.view.activity.abs;

import com.sendi.picture_recognition.base.BaseActivity;
import com.sendi.picture_recognition.bean.PKResultData;

/**
 * Created by Administrator on 2017/12/21.
 */

public abstract class AbsPkResultView extends BaseActivity {

    public abstract void showPkResult(PKResultData pkResultData);
}
