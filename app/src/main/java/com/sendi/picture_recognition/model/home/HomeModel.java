package com.sendi.picture_recognition.model.home;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.service.HomeService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/5.
 */

public class HomeModel extends AbstractHomeModel {

    HomeService mService=this.createService(HomeService.class);

    @Override
    public Observable<BaseEntity<List<HomePicInfo>>> loadPicData(String userId, String lg) {
        return mService.getHomePicData(userId+"",lg+"");
    }
}
