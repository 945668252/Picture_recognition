package com.sendi.picture_recognition.model.home;

import com.sendi.picture_recognition.base.BaseModel;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.HomePicInfo;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Administrator on 2017/12/5.
 */

public abstract class AbstractHomeModel extends BaseModel {

    public abstract Observable<BaseEntity<List<HomePicInfo>>> loadPicData(String userId, String lg);

}
