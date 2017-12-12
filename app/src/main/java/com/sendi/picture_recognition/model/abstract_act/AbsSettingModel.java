package com.sendi.picture_recognition.model.abstract_act;

import android.content.Context;

import com.sendi.picture_recognition.base.BaseModel;

/**
 * Created by Administrator on 2017/12/8.
 */

public abstract class AbsSettingModel extends BaseModel {
    public abstract void initLgType(Context context);

    public abstract String exit(Context context);
}
