package com.sendi.picture_recognition.controller.frame;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.controller.activity.MakeTagActivity;
import com.sendi.picture_recognition.view.adapter.HomeFragmentAdapter;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.bean.ImgInfo;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;
import com.sendi.picture_recognition.utils.httputils.parseutils.SplitString;
import com.sendi.picture_recognition.widget.DiffuseView;


import java.util.ArrayList;
import java.util.List;

import me.yuqirong.cardswipelayout.CardConfig;
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;

/**
 * Created by Administrator on 2017/4/28.
 * 首页
 */

public class HomeFragment extends BaseFragment {
    private final static String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private LinearLayout ll_error;
    private Button bt_reload;
    private DiffuseView mDiffuseView;
    private List<ImgInfo> mImgInfos;
    private HomeFragmentAdapter mAdapter;

    public HomeFragment(){}

    @Override
    protected View initView() {
        Log.i(TAG, "initView: 首页被初始化了");
        View view = View.inflate(mContext, R.layout.home_fragment_layout, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_home);
        ll_error = (LinearLayout) view.findViewById(R.id.ll_error);
        bt_reload= (Button) view.findViewById(R.id.bt_reload);
        mDiffuseView= (DiffuseView) view.findViewById(R.id.diffuse_view);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        bt_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDiffuseView.setVisibility(View.VISIBLE);
                ll_error.setVisibility(View.GONE);
                mDiffuseView.start();
                loadPicData();
            }
        });
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.i(TAG, "initData: " + "hahah");
        mImgInfos = new ArrayList<>();
        //从服务器拉取数据
        loadPicData();

    }

    private void loadPicData() {
        RetrofitFactory
                .getInstance()
                .getAPI()
                .getHomePicData(GlobalConfig.USERID, GlobalConfig.LANGUAGETYPE)
                .compose(mActivity.<BaseEntity<List<HomePicInfo>>>setThread())
                .subscribe(new BaseObserver<List<HomePicInfo>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<HomePicInfo>> t) throws Exception {
                        Log.i(TAG, "onSuccess: " + t.getData().toString());
                        for (int i = 0; i < t.getData().size(); i++) {
//                            stringToArray(strs, t.getData().get(i).getTags());
                            String[] tags=SplitString.StringToArray(t.getData().get(i).getTags());
                            mImgInfos.add(new ImgInfo(tags, t.getData().get(i).getPic_url(),
                                    t.getData().get(i).getPic_id()));
                        }

                        showPic();//首頁展示

                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.i(TAG, "onConnectFailure: " + "执行到了");
                        if (isNetWorkError){
                            Toast.makeText(mContext, "网络连接出错", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(mContext, "网络出错", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected void onRequestStart() {
                        super.onRequestStart();
                        //显示扩散圆
                        mDiffuseView.start();
                    }

                    @Override
                    protected void onRequestEnd(boolean isSuccess) {
                        super.onRequestEnd(isSuccess);
                        if (!isSuccess){
                            ll_error.setVisibility(View.VISIBLE);
                        }
                        mDiffuseView.stop();
                        mDiffuseView.setVisibility(View.GONE);
                    }
                });
    }

    private void showPic() {
        mAdapter = new HomeFragmentAdapter(mContext, mImgInfos);
        Log.i(TAG, "onSuccess: mAdapter");
        if (mAdapter!=null){
            mRecyclerView.setAdapter(mAdapter);

            CardItemTouchHelperCallback call = new CardItemTouchHelperCallback(mRecyclerView.getAdapter(), mImgInfos);

            call.setOnSwipedListener(new OnSwipeListener() {
                @Override
                public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                    HomeFragmentAdapter.HomeViewHolder homeViewHolder = (HomeFragmentAdapter.HomeViewHolder) viewHolder;
                    viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 2.0f);
//                    if (direction == CardConfig.SWIPING_LEFT) {
//                        homeViewHolder.dislikeImageView.setAlpha(Math.abs(ratio));
//                    } else if (direction == CardConfig.SWIPING_RIGHT) {
//                        homeViewHolder.likeImageView.setAlpha(Math.abs(ratio));
//                    } else {
//                        homeViewHolder.dislikeImageView.setAlpha(0f);
//                        homeViewHolder.likeImageView.setAlpha(0f);
//                    }
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, Object o, int direction) {
                    HomeFragmentAdapter.HomeViewHolder homeViewHolder = (HomeFragmentAdapter.HomeViewHolder) viewHolder;
                    viewHolder.itemView.setAlpha(1f);
//                    homeViewHolder.dislikeImageView.setAlpha(Math.abs(0f));
//                    homeViewHolder.dislikeImageView.setAlpha(Math.abs(0f));
                    if (direction == CardConfig.SWIPED_RIGHT) {
                        ImgInfo imgInfo = (ImgInfo) o;
                        Log.i(TAG, "onSwiped: " + imgInfo.getUrl());
                        Intent intent = new Intent(mContext, MakeTagActivity.class);
                        intent.putExtra("imgInfo", imgInfo);
                        startActivity(intent);
                    } else if (direction == CardConfig.SWIPED_LEFT) {
//                    Toast.makeText(mContext, "不打标签", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onSwipedClear() {
                    loadMorePic();
                }
            });
            final ItemTouchHelper touchHelper = new ItemTouchHelper(call);
            final CardLayoutManager cardLayoutManager = new CardLayoutManager(mRecyclerView, touchHelper);
            mRecyclerView.setLayoutManager(cardLayoutManager);
            touchHelper.attachToRecyclerView(mRecyclerView);
        }
    }

    /**
     * 加载更多的图片
     */
    private void loadMorePic() {
        RetrofitFactory
                .getInstance()
                .getAPI()
                .getHomePicData(GlobalConfig.USERID, GlobalConfig.LANGUAGETYPE)
                .compose(mActivity.<BaseEntity<List<HomePicInfo>>>setThread())
                .subscribe(new BaseObserver<List<HomePicInfo>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<HomePicInfo>> t) throws Exception {
                        Log.i(TAG, "onSuccess: " + t.getData().toString());
                        for (int i = 0; i < t.getData().size(); i++) {
//                            stringToArray(strs, t.getData().get(i).getTags());
                            String[] tags= SplitString.StringToArray(t.getData().get(i).getTags());
                            mImgInfos.add(new ImgInfo(tags, t.getData().get(i).getPic_url(), t.getData().get(i).getPic_id()));
                            //更新
                            mAdapter.notifyDataSetChanged();
                        }
                        Log.i(TAG, "onSuccess: " + mImgInfos.size()+" 再次加载");
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    /**
     * 将获得的标签转换为字符串
     */
//    private void stringToArray(String[] tags, String tagString) {
//        strs = tagString.split(",");
//        Log.i(TAG, "stringToArray: " + strs.length);
//    }
}
