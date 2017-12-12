package com.sendi.picture_recognition.model.search;

import com.sendi.picture_recognition.base.BaseModel;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ClassData;
import com.sendi.picture_recognition.bean.SearchPicData;
import com.sendi.picture_recognition.model.IModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/5.
 */

public abstract class AbstractSearchModel extends BaseModel {
    //类别图片
    public abstract Observable<BaseEntity<List<ClassData>>> getClassPicData(String userId);
    //热门图片
    public abstract Observable<BaseEntity<List<SearchPicData>>> getHotPicData(String userId,String lg);
    //最新图片
    public abstract Observable<BaseEntity<List<SearchPicData>>> getNewPicData(String userId,String lg);
}
