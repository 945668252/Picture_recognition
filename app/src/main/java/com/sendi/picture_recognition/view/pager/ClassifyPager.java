package com.sendi.picture_recognition.view.pager;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.base.BaseActivity;
import com.sendi.picture_recognition.base.BasePager;
import com.sendi.picture_recognition.bean.ClassData;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.view.adapter.ClassPagerAdapter;
import com.sendi.picture_recognition.presenter.search.SearchPresenter;
import com.sendi.picture_recognition.view.activity.PicClassDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 * 类别
 */

public class ClassifyPager extends BasePager<ClassData> {
    private final String TAG = this.getClass().getName();

    private RecyclerView mRecyclerView;
    private ClassPagerAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private SearchPresenter mPresenter;
    private List<ClassData> mClassDatas;

    public ClassifyPager(BaseActivity activity) {
        super(activity);
        mPresenter=new SearchPresenter();
    }

    @Override
    public View initView() {

        View mRootView=View.inflate(getActivity(), R.layout.class_pager_layout,null);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.rv_hobby);
        mRefreshLayout= (SwipeRefreshLayout) mRootView.findViewById(R.id.classify_refresh);
        mRefreshLayout.setColorSchemeResources(R.color.colorTheme);
        mRefreshLayout.setProgressViewOffset(true,0,10);

        return mRootView;
    }

    @Override
    public void initData() {
        super.initData();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(mLoadDataScrollController);
        mRefreshLayout.setOnRefreshListener(mLoadDataScrollController);

        mClassDatas=new ArrayList<>();
        mAdapter=new ClassPagerAdapter(mClassDatas,getActivity());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setListener(new ClassPagerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(getActivity(), PicClassDetailActivity.class);
                intent.putExtra("classify",mClassDatas.get(position).getClassify());
                getActivity().startActivity(intent);
            }
        });

        mPresenter.bindView(this);
        mPresenter.getClassPicData(GlobalConfig.USERID);
    }

    @Override
    public void showPicData(List<ClassData> classData) {
        mClassDatas.clear();
        mClassDatas.addAll(classData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        if (mPresenter!=null)
         mPresenter.getClassPicData(GlobalConfig.USERID);
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
