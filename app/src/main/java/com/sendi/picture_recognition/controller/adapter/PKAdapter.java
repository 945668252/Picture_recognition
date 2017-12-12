package com.sendi.picture_recognition.controller.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.sendi.picture_recognition.controller.frame.BaseFragment;
import com.sendi.picture_recognition.controller.pager.BasePager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 */

public class PKAdapter extends PagerAdapter {
    List<BaseFragment> fragmentList;

    public PKAdapter( List<BaseFragment> pagerList) {

        this.fragmentList=pagerList;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
