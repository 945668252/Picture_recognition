package com.sendi.picture_recognition.model.act;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.model.abstract_act.AbsMakeTagModel;
import com.sendi.picture_recognition.service.TagService;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/20.
 */

public class MakeTagModel extends AbsMakeTagModel {

    private TagService mService = this.createService(TagService.class);

    @Override
    public Observable<BaseEntity<String>> submitTags(String uId, String imgId, String tags) {
        return mService.submitTags(uId, imgId, tags);
    }

    @Override
    public String transToString(StringBuffer tagStrings) {
        String finalResult = tagStrings.toString().replace(",", " ").
                replace("。", " ").
                replace("，", " ");
        return finalResult;
    }
}
