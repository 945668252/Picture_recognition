package com.sendi.picture_recognition.model.act;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.model.abstract_act.AbsGradeModel;
import com.sendi.picture_recognition.service.UserService;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/8.
 */

public class GradeModel extends AbsGradeModel {

    UserService mUserService=this.createService(UserService.class);

    @Override
    public Observable<BaseEntity<Float>> getIntegral(String vid) {
        return mUserService.getIntegral(vid);
    }
}
