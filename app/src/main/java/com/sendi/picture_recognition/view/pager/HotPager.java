package com.sendi.picture_recognition.view.pager;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.base.BaseActivity;
import com.sendi.picture_recognition.base.BasePager;
import com.sendi.picture_recognition.bean.ImgInfo;
import com.sendi.picture_recognition.bean.SearchPicData;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.view.activity.MakeTagActivity;
import com.sendi.picture_recognition.view.adapter.SearchPagerAdapter;
import com.sendi.picture_recognition.presenter.search.SearchPresenter;
import com.sendi.picture_recognition.utils.httputils.parseutils.SplitString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 * 最热门
 */

public class HotPager extends BasePager<SearchPicData> {

    private final String TAG = this.getClass().getName();
    private RecyclerView mRecyclerView;
    private SearchPagerAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private String[] tags;
    private List<SearchPicData>picDataList;
    private List<String>firstTags;//标签的代表
    private SearchPresenter mPresenter;

    public HotPager(BaseActivity activity) {
        super(activity);
        mPresenter=new SearchPresenter();
    }

    @Override
    public View initView() {
        View view=View.inflate(getActivity(), R.layout.hot_pager_layout,null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_hot);
        mRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.hot_refresh);
        mRefreshLayout.setColorSchemeResources(R.color.colorTheme);
        mRefreshLayout.setProgressViewOffset(true,0,10);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        mRecyclerView.addOnScrollListener(mLoadDataScrollController);
        mRefreshLayout.setOnRefreshListener(mLoadDataScrollController);
        firstTags=new ArrayList<>();
        picDataList=new ArrayList<>();
        mAdapter=new SearchPagerAdapter(picDataList,firstTags,getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setListener(new SearchPagerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                SearchPicData picData=picDataList.get(position);
                ImgInfo imgInfo=new ImgInfo(tags,picData.getPath(),picData.getId());
                Intent intent=new Intent(getActivity(),MakeTagActivity.class);
                intent.putExtra("imgInfo",imgInfo);
                getActivity().startActivity(intent);
            }
        });

        mPresenter.bindView(this);
        mPresenter.getHotPicData(GlobalConfig.USERID,GlobalConfig.LANGUAGETYPE);
    }

    @Override
    public void showPicData(List<SearchPicData> classData) {
        picDataList.clear();
        picDataList.addAll(classData);
        for (int i = 0; i < classData.size(); i++) {
            tags= SplitString.StringToArray(classData.get(i).getLabel());
            firstTags.add(tags[0]);
            Log.i(TAG, "onSuccess: "+firstTags.size());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        if (mPresenter!=null)
            mPresenter.getHotPicData(GlobalConfig.USERID,GlobalConfig.LANGUAGETYPE);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void showLoading() {
        mRefreshLayout.setRefreshing(true);
        mLoadDataScrollController.setLoadDataStatus(true);
    }

    @Override
    public void hideLoading(boolean isSuccess) {
        mRefreshLayout.setRefreshing(false);
        mLoadDataScrollController.setLoadDataStatus(false);
        if (isSuccess)
            Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(), "刷新失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }
}
