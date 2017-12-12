package com.sendi.picture_recognition.model.abstract_act;

import com.sendi.picture_recognition.base.BaseModel;
import com.sendi.picture_recognition.bean.BaseEntity;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/8.
 */

public abstract class AbsGradeModel extends BaseModel {
    //获取积分
   public abstract Observable<BaseEntity<Float>> getIntegral(String vid);
}
