package com.sendi.picture_recognition.view.activity.abs;

import com.sendi.picture_recognition.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/21.
 */

public abstract class AbsUpdateTagsView extends BaseActivity {
    //删除标签
    public abstract void deleteTagSuccess(int position);
    //添加系统推送的标签
    public abstract void addCommonTagSuccess(String selectedTag);
    //修改标签
    public abstract void alertTagsSuccess(String title,String newTag,int position);
    //添加新的标签
    public abstract void addNewTagSuccess(String title,String newTag);
    //操作失败
    public abstract void fail(String failStr);

}
