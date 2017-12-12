package com.sendi.picture_recognition.view.fragment.home.search;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.base.BaseActivity;
import com.sendi.picture_recognition.base.BaseFragment;
import com.sendi.picture_recognition.base.BasePager;


import com.sendi.picture_recognition.view.adapter.SearchFragmentAdapter;
import com.sendi.picture_recognition.view.pager.*;
import com.sendi.picture_recognition.widget.SearchViewPager;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/28.
 * 选择自己喜欢
 */

public class SearchFragment extends BaseFragment {
    private final static String TAG= SearchFragment.class.getSimpleName();

    private TabPageIndicator mIndicator;
    private ViewPager mViewPager;
    private SearchFragmentAdapter mAdapter;
    private List<String>tabList;
    private List<String>classList;
    private List<BasePager>mPagerList;


    @Override
    public View initView() {
        Log.i(TAG, "initView: 喜欢页被初始化了");
        View view= View.inflate(mContext,R.layout.search_fragment_layout,null);
        mIndicator= (TabPageIndicator) view.findViewById(R.id.tab_page);
        mViewPager= (SearchViewPager) view.findViewById(R.id.vp_search);

        return view;
    }


    protected void initData() {
//        super.initData();
        tabList=new ArrayList<>();
        mPagerList=new ArrayList<>();
        tabList.add("类别");
        tabList.add("热门");
        tabList.add("擅长");
        mPagerList.add(new ClassifyPager((BaseActivity) getActivity()));
        mPagerList.add(new HotPager((BaseActivity) getActivity()));
        mPagerList.add(new NewPager((BaseActivity) getActivity()));
        mAdapter=new SearchFragmentAdapter(tabList,mPagerList);
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager);
        Log.i(TAG, "initData: ");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading(boolean isSuccess) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        for (BasePager pager:mPagerList){
            pager.onDetach();
        }
    }
}
