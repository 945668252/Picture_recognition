package com.sendi.picture_recognition.controller.frame.pk;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.controller.LoadDataScrollController;
import com.sendi.picture_recognition.controller.activity.ChallengeActivity;
import com.sendi.picture_recognition.controller.adapter.pk_adapter.MorePKAdapter;
import com.sendi.picture_recognition.controller.frame.BaseFragment;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.MorePKInfo;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 * 多人挑战
 */

public class MoreFragment extends BaseFragment implements View.OnClickListener, LoadDataScrollController.OnRecyclerRefreshListener {
    private final String TAG = MoreFragment.class.getName();

    private RecyclerView mRecyclerView;
    private Button mButton;
    private MorePKAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private List<MorePKInfo> mList;
    public LoadDataScrollController mController;


    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.more_pager_layout, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_more_pk);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_more);
        mButton = (Button) view.findViewById(R.id.bt_more_pk);
        mRefreshLayout.setColorSchemeResources(R.color.colorTheme);
        mRefreshLayout.setProgressViewOffset(true, 0, 10);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.i(TAG, "initData: More 初始化");
        mController = new LoadDataScrollController(this);
        mButton.setOnClickListener(this);
        mRecyclerView.addOnScrollListener(mController);
        mRefreshLayout.setOnRefreshListener(mController);
        mList = new ArrayList<>();

        loadData();

    }

    /**
     * 加载数据
     */
    private void loadData() {
        RetrofitFactory
                .getInstance()
                .getAPI()
                .getMorePKInfoList(GlobalConfig.USERID)
                .compose(mActivity.<BaseEntity<List<MorePKInfo>>>setThread())
                .subscribe(new BaseObserver<List<MorePKInfo>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<MorePKInfo>> t) throws Exception {
                        Log.i(TAG, "onSuccess: "+t.toString());
                        if (t.getData()==null)
                        {
                            Log.i(TAG, "onSuccess: ");
                            mButton.setVisibility(View.VISIBLE);
                            return;
                        }
                        showData(t.getData());
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void showData(List<MorePKInfo> data) {
        mList=data;
        mAdapter = new MorePKAdapter(mContext, mList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        if (mList.size() < 5) {
            mButton.setVisibility(View.VISIBLE);
//            mRefreshLayout.setVisibility(View.GONE);
        } else {
            mButton.setVisibility(View.GONE);
//            mRefreshLayout.setVisibility(View.VISIBLE);
        }

        mAdapter.setListener(new MorePKAdapter.AdapterItemListener() {
            @Override
            public void onClick(View view, int position) {
                Log.i(TAG, "onClick: "+position);
                Intent intent=new Intent(mActivity, ChallengeActivity.class);
                intent.putExtra("mId",mList.get(position).getId());
                startActivity(intent);
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

    @Override
    public void onClick(View v) {
        createPK();
    }

    /**
     * 创建PK
     */
    private void createPK() {
        Log.i("TAG", "createPK: ");
        RetrofitFactory
                .getInstance()
                .getAPI()
                .getCreateResult(GlobalConfig.USERID)
                .compose(mActivity.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        Log.i(TAG, "onSuccess: "+t);
                        Intent intent=new Intent(mActivity, ChallengeActivity.class);
                        intent.putExtra("mId",t.getData());
                        startActivity(intent);
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                    @Override
                    protected void onCodeError(BaseEntity<String> t) throws Exception {
                        super.onCodeError(t);
//                        Log.i(TAG, "onCodeError: ");
                    }
                });
    }
}
