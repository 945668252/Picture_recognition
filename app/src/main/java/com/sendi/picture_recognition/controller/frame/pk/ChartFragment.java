package com.sendi.picture_recognition.controller.frame.pk;

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
import com.sendi.picture_recognition.controller.LoadDataScrollController;
import com.sendi.picture_recognition.controller.activity.SingleChallengeActivity;
import com.sendi.picture_recognition.controller.adapter.pk_adapter.ChartAdapter;
import com.sendi.picture_recognition.controller.frame.BaseFragment;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.UserChartInfo;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */

public class ChartFragment extends BaseFragment implements LoadDataScrollController.OnRecyclerRefreshListener {

    private final String TAG = ChartFragment.class.getName();

    private RecyclerView mRecyclerView;
    private ChartAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private List<String> mList;
    private List<UserChartInfo> chartList;
    public LoadDataScrollController mController;
    private static int page = 0;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.chart_pager_layout, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_chart_pk);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_chart);
        mRefreshLayout.setColorSchemeResources(R.color.colorTheme);
        mRefreshLayout.setProgressViewOffset(true, 0, 10);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();

        mController = new LoadDataScrollController(this);
        mRecyclerView.addOnScrollListener(mController);
        mRefreshLayout.setOnRefreshListener(mController);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        loadData(false);

        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add("啊森弟");
        }

    }

    /**
     * 加载数据
     */
    private void loadData(final boolean isLoadMore) {
        RetrofitFactory
                .getInstance()
                .getAPI()
                .getChartData(page)
                .compose(mActivity.<BaseEntity<List<UserChartInfo>>>setThread())
                .subscribe(new BaseObserver<List<UserChartInfo>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<UserChartInfo>> t) throws Exception {
                        if (t.getData().size() == 0){
                            Log.i(TAG, "onSuccess: null");
                            return;
                        }
                        showData(t.getData(), isLoadMore);
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    /**
     * 展示数据
     *
     * @param data
     * @param isLoadMore
     */
    private void showData(List<UserChartInfo> data, boolean isLoadMore) {
        if (isLoadMore) {
            chartList.addAll(data);
            mAdapter.notifyDataSetChanged();
            return;
        }else {
            chartList = new ArrayList<>();
            chartList = data;
            mAdapter = new ChartAdapter(mContext, chartList);
            mAdapter.setListener(new ChartAdapter.AdapterItemListener() {
                @Override
                public void onClick(View view, final int position) {
                    //弹出窗口选择图片数量
                    new CanDialog.Builder(mActivity)
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
                                    int picCount;
                                    switch (checkItem) {
                                        case 0:
                                            picCount = 1;
                                            break;
                                        case 1:
                                            picCount = 2;
                                            break;
                                        case 2:
                                            picCount = 4;
                                            break;
                                        case 3:
                                            picCount = 8;
                                            break;
                                        default:
                                            picCount = checkItem;
                                            break;
                                    }

                                    Intent intent=new Intent(mActivity, SingleChallengeActivity.class);
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

            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onRefresh() {
        loadData(false);
        mRefreshLayout.setRefreshing(false);
        mController.setLoadDataStatus(false);
    }

    @Override
    public void onLoadMore() {
        Log.i(TAG, "onLoadMore: page"+page);
        page += 1;
        loadData(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        page=0;
    }
}
