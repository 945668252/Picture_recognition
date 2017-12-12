package com.sendi.picture_recognition.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.controller.adapter.ClassDetailAdapter;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.bean.ImgInfo;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;
import com.sendi.picture_recognition.utils.httputils.parseutils.SplitString;
import com.sendi.picture_recognition.utils.httputils.sqliteutils.SqliteDBUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片相同类Activity
 */
public class PicClassDetailActivity extends BaseActivity {

    private final String TAG=this.getClass().getName();
    private RecyclerView mRecyclerView;
    private ClassDetailAdapter mAdapter;
    private List<HomePicInfo>picInfoList;
    private LinearLayout ll_error;
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

        picInfoList=new ArrayList<>();

        loadData();

    }

    private void loadData() {
        String classify=getIntent().getStringExtra("classify");
        String userId= SqliteDBUtils
                .getInstance(this)
                .getUserDao()
                .queryBuilder()
                .listLazy()
                .get(0)
                .getId()+"";
        RetrofitFactory
                .getInstance()
                .getAPI()
                .getDiatailClassPic(userId,classify, GlobalConfig.LANGUAGETYPE)
                .compose(this.<BaseEntity<List<HomePicInfo>>>setThread())
                .subscribe(new BaseObserver<List<HomePicInfo>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<HomePicInfo>> t) throws Exception {
                        picInfoList=t.getData();
                        showPic(picInfoList);
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                    @Override
                    protected void onRequestEnd(boolean isSuccess) {
                        super.onRequestEnd(isSuccess);
                        if (!isSuccess){
                            ll_error.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void showPic(final List<HomePicInfo> picInfoList) {
        if (picInfoList.size()==0){
            ll_error.setVisibility(View.VISIBLE);
            return;
        }
        mAdapter=new ClassDetailAdapter(picInfoList,this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setListener(new ClassDetailAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                HomePicInfo homePicInfo=picInfoList.get(position);
//                String[] tags=stringToArray(homePicInfo.getTags());
                String[] tags= SplitString.StringToArray(homePicInfo.getTags());
                ImgInfo imgInfo=new ImgInfo(tags,homePicInfo.getPic_url(),homePicInfo.getPic_id());
                Intent intent=new Intent(PicClassDetailActivity.this,MakeTagActivity.class);
                intent.putExtra("imgInfo",imgInfo);
                startActivity(intent);
            }
        });
    }

    public void back(View view) {
        finish();
    }

    /**
     * 重新加载
     * @param view
     */
    public void reLoad(View view) {
        loadData();
    }
}
