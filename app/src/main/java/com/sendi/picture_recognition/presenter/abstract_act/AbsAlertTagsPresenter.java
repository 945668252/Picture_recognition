package com.sendi.picture_recognition.presenter.abstract_act;

import com.sendi.picture_recognition.base.BasePresenter;
import com.sendi.picture_recognition.bean.RecordData;
import com.sendi.picture_recognition.view.activity.abs.AbsUpdateTagsView;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */

public abstract class AbsAlertTagsPresenter extends BasePresenter<AbsUpdateTagsView> {
    //初始化数据
    public abstract void initData(RecordData recordData, List<String> selectedTagList, List<String>unselectedTagList);
    //删除标签
    public abstract void deleteTag(String uId,String pId,String tag,int position);
    //添加系统推送的标签
    public abstract void addCommonTag(String uId,String pId,String tag);
    //修改标签
    public abstract void alertTags(String uId,String pId,String oldTag,String tag,int position);
    //添加新的标签
    public abstract void addNewTag(String uId,String pId,String tag);
}
