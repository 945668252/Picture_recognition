package com.sendi.picture_recognition.view.fragment.home;

import com.sendi.picture_recognition.base.BaseFragment;
import com.sendi.picture_recognition.bean.HomePicInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */

public abstract class AbstractHomeFragment extends BaseFragment {
    //展示图片
    public abstract void showPic(List<HomePicInfo> homePicInfoList);

    //更多
    public abstract void showMorePic(List<HomePicInfo> homePicInfoList);



}
