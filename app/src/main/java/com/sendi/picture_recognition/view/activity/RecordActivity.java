package com.sendi.picture_recognition.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.RecordData;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.view.adapter.RecordFragmentAdapter;
import com.sendi.picture_recognition.presenter.act.RecordPresenter;
import com.sendi.picture_recognition.view.activity.abs.AbsRecordView;
import com.sendi.picture_recognition.widget.circlerefreshlayout.CircleRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class RecordActivity extends AbsRecordView {
    private final static String TAG =RecordActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private CircleRefreshLayout mCircleRefreshLayout;
    private RecordFragmentAdapter mAdapter;
    private List<RecordData> mRecordLists;
    private boolean isSuccess;
    private RecordPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.record_rv);
        mCircleRefreshLayout = (CircleRefreshLayout) findViewById(R.id.circle_refresh_layout);
    }

    private void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecordLists = new ArrayList<>();
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RecordFragmentAdapter(mRecordLists, this);
        mAdapter.setOnItemClickListener(new RecordFragmentAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(RecordActivity.this, UpdateTagsActivity.class);
                intent.putExtra("recordData", mRecordLists.get(position));
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mCircleRefreshLayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {
                if (!isSuccess) {
                    Toast.makeText(RecordActivity.this, "刷新失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void refreshing() {
                mPresenter.getRecordData(GlobalConfig.USERID);
            }
        });

        mPresenter = new RecordPresenter();
        mPresenter.bindView(this);

        mPresenter.getCacheData(this);

        mPresenter.getRecordData(GlobalConfig.USERID);
    }

    @Override
    public void showRecord(List<RecordData> recordData) {
        mRecordLists.clear();
        mRecordLists.addAll(recordData);
        mAdapter.notifyDataSetChanged();

        //缓存
        mPresenter.cacheData(mRecordLists, this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading(boolean isSuccess) {
        mCircleRefreshLayout.finishRefreshing();
       this.isSuccess=isSuccess;
    }

    public void back(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
