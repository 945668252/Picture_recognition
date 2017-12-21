package com.sendi.picture_recognition.view.fragment.home;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.bean.ImgInfo;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.view.activity.MakeTagActivity;
import com.sendi.picture_recognition.view.adapter.HomeFragmentAdapter;
import com.sendi.picture_recognition.presenter.home.HomePresenter;
import com.sendi.picture_recognition.utils.httputils.parseutils.SplitString;
import com.sendi.picture_recognition.widget.DiffuseView;

import java.util.ArrayList;
import java.util.List;

import me.yuqirong.cardswipelayout.CardConfig;
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;

/**
 * Created by Administrator on 2017/12/5.
 */

public class HomeFragment extends AbstractHomeFragment {

    private final static String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private LinearLayout ll_error;
    private Button bt_reload;
    private DiffuseView mDiffuseView;
    private List<ImgInfo> mImgInfos;
    private HomeFragmentAdapter mAdapter;
    private HomePresenter mPresenter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.home_fragment_layout, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_home);
        ll_error = (LinearLayout) view.findViewById(R.id.ll_error);
        bt_reload = (Button) view.findViewById(R.id.bt_reload);
        mDiffuseView = (DiffuseView) view.findViewById(R.id.diffuse_view);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        bt_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDiffuseView.setVisibility(View.VISIBLE);
                ll_error.setVisibility(View.GONE);
                mDiffuseView.start();
                mPresenter.loadPicData(GlobalConfig.USERID, GlobalConfig.LANGUAGETYPE);
            }
        });
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        mImgInfos = new ArrayList<>();
        mAdapter = new HomeFragmentAdapter(mContext, mImgInfos);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new HomePresenter();
        mPresenter.bindView(this);
        mPresenter.loadPicData(GlobalConfig.USERID, GlobalConfig.LANGUAGETYPE);
    }

    @Override
    public void showPic(List<HomePicInfo> homePicInfoList) {
        for (int i = 0; i < homePicInfoList.size(); i++) {
            String[] tags = SplitString.StringToArray(homePicInfoList.get(i).getTags());
            mImgInfos.add(new ImgInfo(tags, homePicInfoList.get(i).getPic_url(),
                    homePicInfoList.get(i).getPic_id()));
        }
        mAdapter.notifyDataSetChanged();

        CardItemTouchHelperCallback call = new CardItemTouchHelperCallback(mRecyclerView.getAdapter(), mImgInfos);

        call.setOnSwipedListener(new OnSwipeListener() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
//                HomeFragmentAdapter.HomeViewHolder homeViewHolder = (HomeFragmentAdapter.HomeViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 2.0f);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Object o, int direction) {
//                HomeFragmentAdapter.HomeViewHolder homeViewHolder = (HomeFragmentAdapter.HomeViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1f);
                if (direction == CardConfig.SWIPED_RIGHT) {
                    ImgInfo imgInfo = (ImgInfo) o;
                    Log.i(TAG, "onSwiped: " + imgInfo.getUrl());
                    Intent intent = new Intent(getActivity(), MakeTagActivity.class);
                    intent.putExtra("imgInfo", imgInfo);
                    startActivity(intent);
                }
            }

            @Override
            public void onSwipedClear() {
                if (mPresenter != null)
                    mPresenter.loadPicData(GlobalConfig.USERID, GlobalConfig.LANGUAGETYPE);
            }
        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(call);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(mRecyclerView, touchHelper);
        mRecyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(mRecyclerView);
    }


    @Override
    public void showMorePic(List<HomePicInfo> homePicInfoList) {

    }

    @Override
    public void showLoading() {
//显示扩散圆
        mDiffuseView.start();
    }

    @Override
    public void hideLoading(boolean isSuccess) {
        mDiffuseView.stop();
        mDiffuseView.setVisibility(View.GONE);
        if (!isSuccess){
            ll_error.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }

}
