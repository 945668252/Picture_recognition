package com.sendi.picture_recognition.controller.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.controller.adapter.PKResultAdapter;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.PKResultData;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;
import com.sendi.picture_recognition.widget.CircleImageView;

/**
 * Created by Administrator on 2017/6/23.
 */

public class PKResultActivity extends BaseActivity {
    
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
    //pk结果的集合
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk_result);
        initView();
        
        initData();
    }

    private void initView() {
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_pk_result);
        myImageView= (CircleImageView) findViewById(R.id.civ_my_pic);
        otherImageView= (CircleImageView) findViewById(R.id.civ_other_pic);
        linearLayout= (LinearLayout) findViewById(R.id.ll_two_user);
        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("挑战结果");
    }

    private void initData() {
        mId=getIntent().getStringExtra("mId");

        RetrofitFactory
                .getInstance()
                .getAPI()
                .getPkResultData(mId, GlobalConfig.USERID)
                .compose(this.<BaseEntity<PKResultData>>setThread())
                .subscribe(new BaseObserver<PKResultData>() {
                    @Override
                    protected void onSuccess(BaseEntity<PKResultData> t) throws Exception {
                        showData(t.getData());
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    /**
     * 展示数据
     * @param data
     */
    private void showData(PKResultData data) {
        Glide.with(PKResultActivity.this)
                .load(data.getMyUrl())
                .into(myImageView);
        Log.i("TAG", "showData: "+data.getMyUrl());
        Glide.with(PKResultActivity.this)
                .load(data.getOtherUrl())
                .into(otherImageView);
        mAdapter=new PKResultAdapter(this,data.getImgAndGradeList());
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    public void back(View view){
        finish();
    }
}
