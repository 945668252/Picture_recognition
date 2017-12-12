package com.sendi.picture_recognition.base;

import android.view.View;

import com.sendi.picture_recognition.bean.ClassData;
import com.sendi.picture_recognition.controller.LoadDataScrollController;
import com.sendi.picture_recognition.view.IView;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 * HotPager、ColdPager、HobbyPager继承
 */

public abstract class BasePager<T> implements LoadDataScrollController.OnRecyclerRefreshListener,IView {

    private BaseActivity mActivity;
    private View mRootView;
    public LoadDataScrollController mLoadDataScrollController;
    public BasePager(BaseActivity activity){
        this.mActivity=activity;
        mRootView=initView();
    }

    //加载各个类别的数据
    public abstract void showPicData(List<T> classData);

    //初始化界面
    public abstract View initView();

    //初始化数据
    public void initData(){
        mLoadDataScrollController=new LoadDataScrollController(this);
    }

    public BaseActivity getActivity(){
        return mActivity;
    }

    public View getRootView() {
        return mRootView;
    }
    //进行取消绑定
    public void onDetach(){}
}
