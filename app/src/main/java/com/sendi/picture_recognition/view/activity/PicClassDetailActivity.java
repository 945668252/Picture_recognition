package com.sendi.picture_recognition.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.bean.ImgInfo;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.view.adapter.ClassDetailAdapter;
import com.sendi.picture_recognition.presenter.act.PicClassDetailPresenter;
import com.sendi.picture_recognition.utils.httputils.parseutils.SplitString;
import com.sendi.picture_recognition.view.activity.abs.PicClassDetailView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */

public class PicClassDetailActivity extends PicClassDetailView {

    private final String TAG=this.getClass().getName();
    private String classify;
    private RecyclerView mRecyclerView;
    private ClassDetailAdapter mAdapter;
    private List<HomePicInfo>picInfoList;
    private LinearLayout ll_error;
    private PicClassDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_class_detail);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_detail);
        ll_error= (LinearLayout) findViewById(R.id.ll_error);
    }

    private void initData() {

        classify=getIntent().getStringExtra("classify");
        picInfoList=new ArrayList<>();

        mPresenter=new PicClassDetailPresenter();
        mPresenter.bindView(this);

        mAdapter=new ClassDetailAdapter(picInfoList,this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setListener(new ClassDetailAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                HomePicInfo homePicInfo=picInfoList.get(position);
                String[] tags= SplitString.StringToArray(homePicInfo.getTags());
                ImgInfo imgInfo=new ImgInfo(tags,homePicInfo.getPic_url(),homePicInfo.getPic_id());
                Intent intent=new Intent(PicClassDetailActivity.this,MakeTagActivity.class);
                intent.putExtra("imgInfo",imgInfo);
                startActivity(intent);
            }
        });

        mPresenter.getDetailClassPic(GlobalConfig.USERID,classify,GlobalConfig.LANGUAGETYPE);
    }

    @Override
    public void showPicData(List<HomePicInfo> picInfoList) {
        this.picInfoList.clear();
        this.picInfoList.addAll(picInfoList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading(boolean isSuccess) {
        if (isSuccess){
            Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
            ll_error.setVisibility(View.GONE);
        }else {
            ll_error.setVisibility(View.VISIBLE);
        }
    }

    public void back(View view) {
        finish();
    }

    /**
     * 重新加载
     * @param view
     */
    public void reLoad(View view) {
        mPresenter.getDetailClassPic(GlobalConfig.USERID,classify,GlobalConfig.LANGUAGETYPE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
