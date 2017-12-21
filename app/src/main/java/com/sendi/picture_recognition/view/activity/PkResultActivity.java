package com.sendi.picture_recognition.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.PKResultData;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.view.adapter.PKResultAdapter;
import com.sendi.picture_recognition.presenter.abstract_act.AbsPkResultPresenter;
import com.sendi.picture_recognition.presenter.act.PkResultPresenter;
import com.sendi.picture_recognition.view.activity.abs.AbsPkResultView;
import com.sendi.picture_recognition.widget.CircleImageView;

/**
 * Created by Administrator on 2017/12/21.
 */

public class PkResultActivity extends AbsPkResultView {

    private RecyclerView mRecyclerView;
    private CircleImageView myImageView;
    private CircleImageView otherImageView;
    private TextView tv_title;
    private LinearLayout linearLayout;
    private LinearLayout ll_error;
    private PKResultAdapter mAdapter;
    private String myUrl;//自己头像
    private String otherUrl;//对方头像
    private String mId;

    private AbsPkResultPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk_result);
        initView();

        initData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_pk_result);
        myImageView = (CircleImageView) findViewById(R.id.civ_my_pic);
        otherImageView = (CircleImageView) findViewById(R.id.civ_other_pic);
        linearLayout = (LinearLayout) findViewById(R.id.ll_two_user);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("挑战结果");
    }

    private void initData() {
        mId = getIntent().getStringExtra("mId");
        mPresenter=new PkResultPresenter();
        mPresenter.bindView(this);
        mPresenter.getPkResultData(mId, GlobalConfig.USERID);
    }

    @Override
    public void showPkResult(PKResultData pkResultData) {
        Glide.with(this)
                .load(pkResultData.getMyUrl())
                .into(myImageView);
        Glide.with(this)
                .load(pkResultData.getOtherUrl())
                .into(otherImageView);
        mAdapter=new PKResultAdapter(this,pkResultData.getImgAndGradeList());
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading(boolean isSuccess) {

    }
    public void back(View view){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
