package com.sendi.picture_recognition.controller.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.sendi.picture_recognition.controller.pager.BasePager;
import com.sendi.picture_recognition.controller.pager.challenge.MoreChallengePager;

import java.util.List;

/**
 * Created by Administrator on 2017/6/23.
 */

public class ChallengePagerAdapter extends PagerAdapter {

    private List<MoreChallengePager>mPagerList;

    public ChallengePagerAdapter(List<MoreChallengePager> pagerList) {
        mPagerList = pagerList;
    }

    @Override
    public int getCount() {
        return mPagerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        MoreChallengePager pager=mPagerList.get(position);
        container.addView(pager.mRootView);
        pager.initData();
        return pager.mRootView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
