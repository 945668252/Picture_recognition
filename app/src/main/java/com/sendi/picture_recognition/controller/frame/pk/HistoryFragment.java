package com.sendi.picture_recognition.controller.frame.pk;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.controller.LoadDataScrollController;
import com.sendi.picture_recognition.controller.activity.PKResultActivity;
import com.sendi.picture_recognition.controller.activity.SingleChallengeActivity;
import com.sendi.picture_recognition.controller.adapter.pk_adapter.HistoryAdapter;
import com.sendi.picture_recognition.controller.frame.BaseFragment;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ChallengeHistory;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 * 挑战纪录
 */

public class HistoryFragment extends BaseFragment implements LoadDataScrollController.OnRecyclerRefreshListener{

    private final String TAG=this.getClass().getName();

    private RecyclerView mRecyclerView;
    private HistoryAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private List<String> mList;
    private List<ChallengeHistory>historyList;
    public LoadDataScrollController mController;

    @Override
    protected View initView() {
        View view=View.inflate(mContext, R.layout.history_pager_layout,null);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.rv_history_pk);
        mRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.srl_history);
        mRefreshLayout.setColorSchemeResources(R.color.colorTheme);
        mRefreshLayout.setProgressViewOffset(true,0,10);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        mController= new LoadDataScrollController(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(mController);
        mRefreshLayout.setOnRefreshListener(mController);

        loadData();
        mList=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add("胜利");
        }

    }

    /**
     * 加载数据
     */
    private void loadData() {
        RetrofitFactory
                .getInstance()
                .getAPI()
                .getPkHistory(GlobalConfig.USERID)
                .compose(mActivity.<BaseEntity<List<ChallengeHistory>>>setThread())
                .subscribe(new BaseObserver<List<ChallengeHistory>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<ChallengeHistory>> t) throws Exception {
                        if (t.getData().size() == 0){
                            Log.i(TAG, "onSuccess: null");
//                            return;
                        }
                        showData(t.getData());
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void showData(List<ChallengeHistory> data) {
        historyList=data;
        mAdapter=new HistoryAdapter(mContext,historyList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setListener(new HistoryAdapter.OnHistoryItemClickListener() {
            @Override
            public void onCLick(View view, int position) {
                if (historyList.get(position).getResultStr().equals("等待")) {//跳到挑战界面
                    Intent intent=new Intent(mActivity, SingleChallengeActivity.class);
                    intent.putExtra("mId",historyList.get(position).getId());
                    intent.putExtra("type",1);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(mActivity, PKResultActivity.class);
                    intent.putExtra("mId",historyList.get(position).id);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public void onRefresh() {
        loadData();
        mRefreshLayout.setRefreshing(false);
        mController.setLoadDataStatus(false);
    }

    @Override
    public void onLoadMore() {

    }
}
