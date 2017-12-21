package com.sendi.picture_recognition.model.abstract_act;

import com.sendi.picture_recognition.base.BaseModel;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.RecordData;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/21.
 */

public abstract class AbsAlertTagsModel extends BaseModel {
    //初始化数据
    public abstract void initData(RecordData recordData, List<String>selectedTagList,List<String>unselectedTagList);
    //删除标签
    public abstract Observable<BaseEntity<String>> deleteTag(String uId, String pId, String tag);
    //添加系统推送的标签
    public abstract Observable<BaseEntity<String>> addCommonTag(String uId,String pId,String tag);
    //修改标签
    public abstract Observable<BaseEntity<String>>  alertTags(String uId,String pId,String oldTag,String tag);
    //添加新的标签
    public abstract Observable<BaseEntity<String>>  addNewTag(String uId,String pId,String tag);
}
