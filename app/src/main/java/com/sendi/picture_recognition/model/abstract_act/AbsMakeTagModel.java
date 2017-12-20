package com.sendi.picture_recognition.model.abstract_act;

import com.sendi.picture_recognition.base.BaseModel;
import com.sendi.picture_recognition.bean.BaseEntity;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/20.
 */

public abstract class AbsMakeTagModel extends BaseModel {

    public abstract Observable<BaseEntity<String>> submitTags(String uId, String imgId, String tags);

    public abstract String transToString(StringBuffer tagStrings);
}
