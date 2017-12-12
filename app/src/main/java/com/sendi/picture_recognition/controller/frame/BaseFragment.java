package com.sendi.picture_recognition.controller.frame;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sendi.picture_recognition.controller.activity.MainActivity;

/**
 * Created by Administrator on 2017/4/28.
 * 基类，子类：HomeFragment、PkFragment、SearchFragment、UserFragment
 */

public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected MainActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    /**
     * 初始化View，让每个子类都必须实现
     * @return
     */
    protected abstract View initView() ;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 子类初始化数据
     * 网络请求、数据绑定、数据展示
     */
    protected  void initData(){
        mActivity= (MainActivity) getActivity();
    }
}
