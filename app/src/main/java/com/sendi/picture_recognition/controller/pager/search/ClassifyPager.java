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
import com.sendi.picture_recognition.controller.activity.PicClassDetailActivity;
import com.sendi.picture_recognition.controller.adapter.ClassPagerAdapter;
import com.sendi.picture_recognition.controller.pager.BasePager;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ClassData;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;

import java.util.List;

/**
 * 分类
 * Created by Administrator on 2017/5/5.
 */

public class ClassifyPager extends BasePager {
    private RecyclerView mRecyclerView;
    private ClassPagerAdapter mAdapter;
//    private TestAdapter mTestAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private List<String>classList;
    private List<Integer>picClass;
    private final String TAG = this.getClass().getName();

    public ClassifyPager(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.class_pager_layout, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_hobby);
        mRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.classify_refresh);
        mRefreshLayout.setColorSchemeResources(R.color.colorTheme);
        mRefreshLayout.setProgressViewOffset(true,0,10);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.i(TAG, "initData: Class界面初始化");

//        mTestAdapter=new TestAdapter(classList,picClass,mActivity);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity,3));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(mController);
        mRefreshLayout.setOnRefreshListener(mController);
        loadData();
    }

    /**
     * 从服务端加载数据
     */
    private void loadData(){
        RetrofitFactory
                .getInstance()
                .getAPI()
                .getClassPicData(GlobalConfig.USERID)
                .compose(mActivity.<BaseEntity<List<ClassData>>>setThread())
                .subscribe(new BaseObserver<List<ClassData>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<ClassData>> t) throws Exception {
                        Log.i(TAG, "onSuccess: "+t.toString());

                        showClass(t.getData());
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void showClass(final List<ClassData> data) {
        mAdapter=new ClassPagerAdapter(data,mActivity);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity,3));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setListener(new ClassPagerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.i(TAG, "onClick: "+data.get(position).toString());
                Intent intent=new Intent(mActivity, PicClassDetailActivity.class);
                intent.putExtra("classify",data.get(position).getClassify());
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {
        loadData();
        mRefreshLayout.setRefreshing(false);
        mController.setLoadDataStatus(false);
        Toast.makeText(mActivity, "正在刷新", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMore() {

    }
}
