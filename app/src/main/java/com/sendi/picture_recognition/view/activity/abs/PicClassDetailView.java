package com.sendi.picture_recognition.view.activity.abs;

import com.sendi.picture_recognition.base.BaseActivity;
import com.sendi.picture_recognition.bean.HomePicInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */

public abstract class PicClassDetailView extends BaseActivity {

    /**
     * 展示图片信息
     * @param picInfoList
     */
    public abstract void showPicData(List<HomePicInfo> picInfoList);

}
