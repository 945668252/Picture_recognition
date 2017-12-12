package com.sendi.picture_recognition.controller.pager;

import android.content.Context;
import android.view.View;

import com.sendi.picture_recognition.controller.LoadDataScrollController;
import com.sendi.picture_recognition.controller.activity.MainActivity;

/**
 * Created by Administrator on 2017/5/5.
 * 由HotPager、ColdPager、HobbyPager继承
 */

public abstract class BasePager implements LoadDataScrollController.OnRecyclerRefreshListener{
    public MainActivity mActivity;
    public View mRootView;
    public LoadDataScrollController mController;

    public BasePager(Context context) {
        mActivity = (MainActivity) context;
        mRootView = initView();
    }

    /**
     * 初始化View
     * @return
     */
    protected abstract View initView();

    /**
     * 初始化数据、绑定、展示等
     */
    public void initData(){
        mController=new LoadDataScrollController(this);
    }
}
