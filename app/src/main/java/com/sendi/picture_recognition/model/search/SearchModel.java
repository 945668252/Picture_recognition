package com.sendi.picture_recognition.model.search;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ClassData;
import com.sendi.picture_recognition.bean.SearchPicData;
import com.sendi.picture_recognition.service.SearchService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/5.
 */

public class SearchModel extends AbstractSearchModel {

    SearchService mService = this.createService(SearchService.class);

    @Override
    public Observable<BaseEntity<List<ClassData>>> getClassPicData(String userId) {
        return mService.getClassPicData(userId);
    }

    @Override
    public Observable<BaseEntity<List<SearchPicData>>> getHotPicData(String userId, String lg) {
        return mService.getHotPicData(userId, lg);
    }

    @Override
    public Observable<BaseEntity<List<SearchPicData>>> getNewPicData(String userId, String lg) {
        return mService.getNewPicData(userId, lg);
    }
}
