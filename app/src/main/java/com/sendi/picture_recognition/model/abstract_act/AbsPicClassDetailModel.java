package com.sendi.picture_recognition.model.abstract_act;

import com.sendi.picture_recognition.base.BaseModel;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.HomePicInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/21.
 */

public abstract class AbsPicClassDetailModel extends BaseModel {
    /**
     * 获取某一类别的图片
     * @param uId
     * @param classify
     * @param lg
     * @return
     */
    public abstract Observable<BaseEntity<List<HomePicInfo>>> getDiatailClassPic(String uId,
                                                                                 String classify,
                                                                                 String lg);

}
