package com.sendi.picture_recognition.model.act;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.RecordData;
import com.sendi.picture_recognition.model.abstract_act.AbsAlertTagsModel;
import com.sendi.picture_recognition.service.TagService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/21.
 */

public class AlertTagsModel extends AbsAlertTagsModel {
    
    private TagService mTagService=this.createService(TagService.class);
    
    @Override
    public void initData(RecordData recordData, List<String> selectedTagList, List<String> unselectedTagList) {
        for (int i = 0; i < recordData.getSelectedTags().length; i++) {
            if (recordData.getSelectedTags()[i] != null)
                selectedTagList.add(recordData.getSelectedTags()[i]);
        }
        for (int i = 0; i < recordData.getUnselectedTags().length; i++) {
            if (recordData.getUnselectedTags()[i] != null)
                unselectedTagList.add(recordData.getUnselectedTags()[i]);
        }
    }

    @Override
    public Observable<BaseEntity<String>> deleteTag(String uId, String pId, String tag) {
        return mTagService.deleteTag(uId, pId, tag);
    }

    @Override
    public Observable<BaseEntity<String>> addCommonTag(String uId, String pId, String tag) {
        return mTagService.addCommonTag(uId, pId, tag);
    }

    @Override
    public Observable<BaseEntity<String>> alertTags(String uId, String pId, String oldTag, String tag) {
        return mTagService.alertTag(uId, pId, oldTag, tag);
    }

    @Override
    public Observable<BaseEntity<String>> addNewTag(String uId, String pId, String tag) {
        return mTagService.addEditTag(uId, pId, tag);
    }
}
