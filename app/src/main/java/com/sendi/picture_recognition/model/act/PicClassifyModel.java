package com.sendi.picture_recognition.model.act;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.model.abstract_act.AbsPicClassDetailModel;
import com.sendi.picture_recognition.service.PicClassDetailService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/21.
 */

public class PicClassifyModel extends AbsPicClassDetailModel {

    private PicClassDetailService  mService=this.createService(PicClassDetailService.class);

    @Override
    public Observable<BaseEntity<List<HomePicInfo>>> getDiatailClassPic(String uId, String classify, String lg) {
        return mService.getDiatailClassPic(uId, classify, lg);
    }
}
