package com.sendi.picture_recognition.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.controller.adapter.RecordFragmentAdapter;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.RecordData;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;
import com.sendi.picture_recognition.utils.httputils.parseutils.SplitString;
import com.sendi.picture_recognition.utils.httputils.sqliteutils.SqliteDBUtils;
import com.sendi.picture_recognition.widget.circlerefreshlayout.CircleRefreshLayout;
import com.sendi.userdb.RecordDataDao;

import java.util.ArrayList;
import java.util.List;

/**
 * 记录
 */
public class RecordActivity extends BaseActivity {
    private final static String TAG = RecordActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private CircleRefreshLayout mCircleRefreshLayout;
    private RecordFragmentAdapter mAdapter;
    private List<RecordData> mRecordLists;
    private boolean isSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.record_rv);
        mCircleRefreshLayout= (CircleRefreshLayout) findViewById(R.id.circle_refresh_layout);
    }

    private void initData() {
        Log.i(TAG, "initData: record");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCircleRefreshLayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {
                if (!isSuccess){
                    Toast.makeText(RecordActivity.this, "刷新失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void refreshing() {
                getDataFromSever();
            }
        });
        //先展示缓存的数据
        List<RecordData>cacheRecordDataList= getCacheData();
        if (cacheRecordDataList!=null){
            showRecord(cacheRecordDataList);
        }
        //从服务端拉去数据
        getDataFromSever();
    }


    private void getDataFromSever() {
        RetrofitFactory
                .getInstance()
                .getAPI()
                .getRecordData(GlobalConfig.USERID)
                .compose(this.<BaseEntity<List<RecordData>>>setThread())
                .subscribe(new BaseObserver<List<RecordData>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<RecordData>> t) throws Exception {
//                        Log.i(TAG, "onSuccess: "+t.getData().get(0).toString());
                        mRecordLists = t.getData();
                        Log.i(TAG, "onSuccess: " + mRecordLists.get(0).getUnselectedTags().length);

                        showRecord(mRecordLists);
                        cacheData(mRecordLists);
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                    @Override
                    protected void onRequestEnd(boolean isSuccess) {
                        super.onRequestEnd(isSuccess);

                       RecordActivity.this.isSuccess=isSuccess;

                        mCircleRefreshLayout.finishRefreshing();
                    }
                });
    }

    private void showRecord(final List<RecordData> recordLists) {
        mAdapter = new RecordFragmentAdapter(recordLists, this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecordFragmentAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.i(TAG, "onClick: " + recordLists.get(position).toString());
                Intent intent = new Intent(RecordActivity.this, UpdateTagsActivity.class);
                intent.putExtra("recordData", recordLists.get(position));
                startActivity(intent);
            }
        });
    }

    /**
     * 缓存数据
     *
     * @param recordLists
     */
    private void cacheData(List<RecordData> recordLists) {
        RecordDataDao recordDataDao = SqliteDBUtils
                .getInstance(RecordActivity.this)
                .getRecordDataDao();

        for (RecordData record : recordLists) {
//            com.sendi.userdb.RecordData recordData = new com.sendi.userdb.RecordData();
            com.sendi.userdb.RecordData recordData = recordDataDao
                    .queryBuilder()
                    .where(RecordDataDao.Properties.Img_id.eq(record.getPid()))
                    .unique();
            if (recordData == null) {//说明不存在
                recordData = new com.sendi.userdb.RecordData();
                recordData.setImg_id(record.getPid());
                recordData.setImg_url(record.getImageUrl());
                recordData.setStatus(record.getStatus());
                String selectedTag = getTagsString(record.getSelectedTags());
                recordData.setTags_selected(selectedTag);//历史
                String unselectedTag = getTagsString(record.getUnselectedTags());
                recordData.setTags_unselected(unselectedTag);//推荐
                //插入数据
                recordDataDao.insert(recordData);
            }

        }
    }

    /**
     * 获取缓存数据
     * @return
     */
    private List<RecordData> getCacheData() {
        List<RecordData>recordList=new ArrayList<>();
        List<com.sendi.userdb.RecordData> cacheRecordDataList = SqliteDBUtils
                .getInstance(RecordActivity.this)
                .getRecordDataDao()
                .queryBuilder()
                .listLazy();
        for (com.sendi.userdb.RecordData record : cacheRecordDataList) {
            RecordData recordData = new RecordData();
            recordData.setStatus(record.getStatus());
            recordData.setImageUrl(record.getImg_url());
            recordData.setPid(record.getImg_id());
            String[] selectedTags = SplitString.StringToArray(record.getTags_selected());
            recordData.setSelectedTags(selectedTags);
            String[] unselectedTags =SplitString.StringToArray(record.getTags_unselected());
            recordData.setUnselectedTags(unselectedTags);
            recordList.add(recordData);
        }
        return recordList;
    }

    //将数组变成字符串，以便缓存
    private String getTagsString(String[] tagsArray) {
        String tag = "";
        for (int i = 0; i < tagsArray.length; i++) {
            if (i == 0) {
                tag = tagsArray[i];
            } else {
                tag = tag + "," + tagsArray[i];
            }
        }
        return tag;
    }

    public void back(View view) {
        finish();
    }
}
