package com.sendi.picture_recognition.view.fragment.home.pk;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.ChallengeHistory;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.controller.LoadDataScrollController;
import com.sendi.picture_recognition.controller.activity.PKResultActivity;
import com.sendi.picture_recognition.controller.adapter.pk_adapter.HistoryAdapter;
import com.sendi.picture_recognition.presenter.pk.PkPresenter;
import com.sendi.picture_recognition.view.activity.SingleChallengeActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class HistoryFragment extends AbstractPkFragment<ChallengeHistory> {
    private final String TAG = this.getClass().getName();

    private RecyclerView mRecyclerView;
    private HistoryAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private List<ChallengeHistory> historyList;
    private PkPresenter mPkPresenter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.history_pager_layout, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_history_pk);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_history);
        mRefreshLayout.setColorSchemeResources(R.color.colorTheme);
        mRefreshLayout.setProgressViewOffset(true, 0, 10);

        return view;
    }

    @Override
    public void showData(List<ChallengeHistory> data) {
        historyList.clear();
        historyList.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {
        super.initData();
        mRecyclerView.addOnScrollListener(mController);
        mRefreshLayout.setOnRefreshListener(mController);

        historyList = new LinkedList<>();
        mAdapter = new HistoryAdapter(getContext(), historyList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setListener(new HistoryAdapter.OnHistoryItemClickListener() {
            @Override
            public void onCLick(View view, int position) {
                if (historyList.get(position).getResultStr().equals("等待")) {
                    //跳到挑战界面
                    Intent intent = new Intent(getActivity(), SingleChallengeActivity.class);
                    intent.putExtra("mId", historyList.get(position).getId());
                    intent.putExtra("type", 1);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), PKResultActivity.class);
                    intent.putExtra("mId", historyList.get(position).id);
                    startActivity(intent);
                }
            }
        });

        mPkPresenter=new PkPresenter();
        mPkPresenter.bindView(this);
        mPkPresenter.getPkHistory(GlobalConfig.USERID);

    }

    @Override
    public void showLoading() {
        mRefreshLayout.setRefreshing(true);
        mController.setLoadDataStatus(true);
    }

    @Override
    public void hideLoading(boolean isSuccess) {
        mRefreshLayout.setRefreshing(false);
        mController.setLoadDataStatus(false);
    }

    @Override
    public void onRefresh() {
        mPkPresenter.getPkHistory(GlobalConfig.USERID);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPkPresenter.detachView();
    }
}
