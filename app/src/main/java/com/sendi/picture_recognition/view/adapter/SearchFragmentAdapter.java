package com.sendi.picture_recognition.view.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.sendi.picture_recognition.base.BasePager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 */

public class SearchFragmentAdapter extends PagerAdapter {
    private List<String>tabList;
    List<BasePager> pagerList;

    public SearchFragmentAdapter(List<String> tabList, List<BasePager> pagerList) {
        this.tabList = tabList;
        this.pagerList=pagerList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position);
    }

    @Override
    public int getCount() {
        return tabList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager pager=pagerList.get(position);
        container.addView(pager.getRootView());
        pager.initData();
        return pager.getRootView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
