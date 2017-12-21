package com.sendi.picture_recognition.view.fragment.home.pk;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.MorePKInfo;
import com.sendi.picture_recognition.config.GlobalConfig;

import com.sendi.picture_recognition.view.adapter.pk_adapter.MorePKAdapter;
import com.sendi.picture_recognition.presenter.pk.PkPresenter;
import com.sendi.picture_recognition.view.activity.MoreChallengeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class MoreFragment extends AbstractPkFragment<MorePKInfo> implements View.OnClickListener{

    private final String TAG = MoreFragment.class.getName();

    private RecyclerView mRecyclerView;
    private Button mButton;
    private MorePKAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private List<MorePKInfo> mList;
    private PkPresenter mPkPresenter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.more_pager_layout, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_more_pk);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_more);
        mButton = (Button) view.findViewById(R.id.bt_more_pk);
        mRefreshLayout.setColorSchemeResources(R.color.colorTheme);
        mRefreshLayout.setProgressViewOffset(true, 0, 10);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        mButton.setOnClickListener(this);
        mRecyclerView.addOnScrollListener(mController);
        mRefreshLayout.setOnRefreshListener(mController);

        mList = new ArrayList<>();
        mAdapter=new MorePKAdapter(getContext(),mList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setListener(new MorePKAdapter.AdapterItemListener() {
            @Override
            public void onClick(View view, int position) {
                Log.i(TAG, "onClick: "+position);
                Intent intent=new Intent(getActivity(), MoreChallengeActivity.class);
                intent.putExtra("mId",mList.get(position).getId());
                startActivity(intent);
            }
        });

        mPkPresenter=new PkPresenter();
        mPkPresenter.bindView(this);
        mPkPresenter.getMorePKInfoList(GlobalConfig.USERID);
    }

    @Override
    public void showData(List<MorePKInfo> data) {
        mList.clear();
        mList.addAll(data);
        mAdapter.notifyDataSetChanged();
        //少于5条数据时可以创建多人挑战
        if (data.size() < 5) {
            mButton.setVisibility(View.VISIBLE);
        } else {
            mButton.setVisibility(View.GONE);
        }

    }

    @Override
    public void createPk(String result,boolean isSuccess) {
        super.createPk(result, isSuccess);
        if (isSuccess){
            Intent intent=new Intent(getActivity(), MoreChallengeActivity.class);
            intent.putExtra("mId",result);
            startActivity(intent);
        }else {
            Toast.makeText(mContext, "创建失败", Toast.LENGTH_SHORT).show();
        }

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
        mPkPresenter.getMorePKInfoList(GlobalConfig.USERID);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onClick(View v) {
        createPk();
    }

    /**
     * 创建挑战
     */
    private void createPk() {
        mPkPresenter.getCreateResult(GlobalConfig.USERID);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPkPresenter.detachView();
    }
}
