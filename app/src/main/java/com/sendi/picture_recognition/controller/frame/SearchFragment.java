package com.sendi.picture_recognition.controller.frame;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.view.adapter.SearchAdapter;
import com.sendi.picture_recognition.controller.pager.BasePager;
import com.sendi.picture_recognition.controller.pager.search.ClassifyPager;
import com.sendi.picture_recognition.controller.pager.search.ColdPager;
import com.sendi.picture_recognition.controller.pager.search.HotPager;
import com.sendi.picture_recognition.widget.SearchViewPager;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/28.
 * 选择自己喜欢
 */

public class SearchFragment extends BaseFragment {
    private final static String TAG=SearchFragment.class.getSimpleName();

    private TabPageIndicator mIndicator;
    private ViewPager mViewPager;
    private SearchAdapter mAdapter;
    private List<String>tabList;
    private List<String>classList;
    private List<BasePager>mPagerList;
//    private Activity mActivity;

    public SearchFragment(){}

//    public SearchFragment(Activity activity) {
//        //可以用getActivity()代替。
//        mActivity = activity;
//    }


    @Override
    protected View initView() {
        Log.i(TAG, "initView: 喜欢页被初始化了");
        View view= View.inflate(mContext,R.layout.search_fragment_layout,null);
        mIndicator= (TabPageIndicator) view.findViewById(R.id.tab_page);
        mViewPager= (SearchViewPager) view.findViewById(R.id.vp_search);

        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        tabList=new ArrayList<>();
        mPagerList=new ArrayList<>();
        tabList.add("类别");
        tabList.add("热门");
        tabList.add("擅长");
        mPagerList.add(new ClassifyPager(mActivity));
        mPagerList.add(new HotPager(mActivity));
        mPagerList.add(new ColdPager(mActivity));
        mAdapter=new SearchAdapter(tabList,mPagerList);
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager);
    }
}
