package com.sendi.picture_recognition.view.fragment.home.pk;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.canyinghao.candialog.CanDialog;
import com.canyinghao.candialog.CanDialogInterface;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.UserChartInfo;
import com.sendi.picture_recognition.view.adapter.pk_adapter.ChartAdapter;
import com.sendi.picture_recognition.presenter.pk.PkPresenter;
import com.sendi.picture_recognition.view.activity.SingleChallengeActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 * 重构
 * 排行榜
 */

public class ChartFragment extends AbstractPkFragment<UserChartInfo> {
    private final String TAG = ChartFragment.class.getName();

    private RecyclerView mRecyclerView;
    private ChartAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private List<UserChartInfo> chartList;
    private static int page = 0;
    private PkPresenter mPkPresenter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.chart_pager_layout, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_chart_pk);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_chart);
        mRefreshLayout.setColorSchemeResources(R.color.colorTheme);
        mRefreshLayout.setProgressViewOffset(true, 0, 10);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        mRecyclerView.addOnScrollListener(mController);
        mRefreshLayout.setOnRefreshListener(mController);

        chartList = new LinkedList<>();
        mAdapter = new ChartAdapter(getContext(), chartList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setListener(new ChartAdapter.AdapterItemListener() {
            @Override
            public void onClick(View view, final int position) {
                //弹出窗口选择图片数量
                new CanDialog.Builder(getActivity())
                        .setTitle("请选择图片数量")
                        .setSingleChoiceItems(new String[]{"一张", "二张", "四张", "八张"}, 1, new CanDialogInterface.OnClickListener() {
                            @Override
                            public void onClick(CanDialog dialog, int checkItem, CharSequence text, boolean[] checkItems) {
                                Log.i(TAG, "onClick: " + checkItem);
                            }
                        })
                        .setNegativeButton("取消", true, null)
                        .setPositiveButton("确认", true, new CanDialogInterface.OnClickListener() {
                            @Override
                            public void onClick(CanDialog dialog, int checkItem, CharSequence text, boolean[] checkItems) {
                                Toast.makeText(mContext, checkItem + "", Toast.LENGTH_SHORT).show();
                                int picCount=1<<checkItem;

                                Log.i(TAG, "onClick: picCount"+picCount);

                                Intent intent=new Intent(getActivity(), SingleChallengeActivity.class);
                                intent.putExtra("vId",chartList.get(position).getId());
                                intent.putExtra("picCount",picCount + "");
                                intent.putExtra("type",0);
                                startActivity(intent);
                            }
                        })
                        .setTileAnimator()
                        .setCancelable(true)
                        .show();

            }
        });

        mPkPresenter = new PkPresenter();
        mPkPresenter.bindView(this);
        mPkPresenter.getChartData(page);
    }

    @Override
    public void showData(List<UserChartInfo> data) {
        if (page==0){
            chartList.clear();
        }
        chartList.addAll(data);
        mAdapter.notifyDataSetChanged();
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
        page = 0;
        if (mPkPresenter != null)
            mPkPresenter.getChartData(page);
    }

    @Override
    public void onLoadMore() {
        page += 1;
        if (mPkPresenter != null)
            mPkPresenter.getChartData(page);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mPkPresenter.detachView();
    }
}
