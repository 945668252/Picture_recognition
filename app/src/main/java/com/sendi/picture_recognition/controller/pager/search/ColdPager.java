package com.sendi.picture_recognition.controller.pager.search;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.controller.activity.MakeTagActivity;
import com.sendi.picture_recognition.view.adapter.SearchPagerAdapter;
import com.sendi.picture_recognition.controller.pager.BasePager;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ImgInfo;
import com.sendi.picture_recognition.bean.SearchPicData;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;
import com.sendi.picture_recognition.utils.httputils.parseutils.SplitString;
import com.sendi.picture_recognition.utils.httputils.sputils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 * 最新界面
 */

public class ColdPager extends BasePager {

    private final String TAG = this.getClass().getName();
    private RecyclerView mRecyclerView;
    private SearchPagerAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private List<SearchPicData>mPicDatas;
    private List<String>firstTags;
    private String[] tags;

    public ColdPager(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View view=View.inflate(mActivity, R.layout.new_pager_layout,null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_new);
        mRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.cold_refresh);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        mRecyclerView.addOnScrollListener(mController);
        mRefreshLayout.setOnRefreshListener(mController);
        mPicDatas=new ArrayList<>();
        firstTags=new ArrayList<>();
        loadData();

        Log.i("TAG", "initView: New被初始化了");
    }

    /**
     * 加载数据
     */
    private void loadData() {

        String  userId= GlobalConfig.USERID;
        //语言类型
        String lgType= SPUtils
                .getLanguageType(mActivity);
        //获取图片及类别
        RetrofitFactory
                .getInstance()
                .getAPI()
                .getNewPicData(userId,lgType)
                .compose(mActivity.<BaseEntity<List<SearchPicData>>>setThread())
                .subscribe(new BaseObserver<List<SearchPicData>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<SearchPicData>> t) throws Exception {
                        mPicDatas=t.getData();
                        for (int i = 0; i < t.getData().size(); i++) {
//                           tags=stringToArray(tags,t.getData().get(i).getLabel());
                            tags= SplitString.StringToArray(t.getData().get(i).getLabel());
//                            Log.i(TAG, "onSuccess: "+tags[0]);
                            firstTags.add(tags[0]);
//                            Log.i(TAG, "onSuccess: "+firstTags.size());
                        }
                        showPic();//展示圖片
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void showPic() {
        mAdapter=new SearchPagerAdapter(mPicDatas,firstTags,mActivity);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity,3));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setListener(new SearchPagerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.i(TAG, "onClick: "+position);
                SearchPicData picData=mPicDatas.get(position);
                String[] tags= SplitString.StringToArray(picData.getLabel());
                ImgInfo imgInfo=new ImgInfo(tags,picData.getPath(),picData.getId());
                Intent intent=new Intent(mActivity,MakeTagActivity.class);
                intent.putExtra("imgInfo",imgInfo);
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(false);
        mController.setLoadDataStatus(false);
        Toast.makeText(mActivity, "下拉刷新", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMore() {

    }
}
