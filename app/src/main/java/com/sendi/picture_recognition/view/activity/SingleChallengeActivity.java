package com.sendi.picture_recognition.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canyinghao.candialog.CanDialog;
import com.canyinghao.candialog.CanDialogInterface;
import com.google.gson.Gson;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.ChallengeData;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.bean.SingleChallengeData;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.controller.activity.ChallengeActivity;
import com.sendi.picture_recognition.controller.adapter.ChallengePagerAdapter;
import com.sendi.picture_recognition.controller.pager.challenge.MoreChallengePager;
import com.sendi.picture_recognition.presenter.abstract_act.AbsChallengePresenter;
import com.sendi.picture_recognition.presenter.act.ChallengePresenter;
import com.sendi.picture_recognition.view.activity.abs.AbsChallengeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Administrator on 2017/12/7.
 */

public class SingleChallengeActivity extends AbsChallengeView<SingleChallengeData> implements MoreChallengePager.OnSaveItemClickListener {
    private final String TAG = ChallengeActivity.class.getName();
    private ViewPager mViewPager;
    private ChallengePagerAdapter mAdapter;
    private Button mButton;
    private LinearLayout ll_error;
    private TextView tv_title;
    private List<MoreChallengePager> pagerList;
    private List<HomePicInfo> picInfoList;
    private HashMap<String, List<String>> mHashMap;//记录对应图片被选择过的照片
    private String mId;
    private String vId;
    private String picCount;
    private int type;
    private AbsChallengePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        type = getIntent().getIntExtra("type", 0);
        Log.i(TAG, "onCreate: " + type);
        initView();

        initData();
    }

    private void initData() {
        mHashMap = new HashMap<>();
        pagerList = new ArrayList<>();
        picInfoList = new ArrayList<>();

        mPresenter = new ChallengePresenter();
        mPresenter.bindView(this);

        if (type == 0) {//创建挑战
            vId = getIntent().getStringExtra("vId");
            picCount = getIntent().getStringExtra("picCount");
            Log.i(TAG, "initData: " + mId + " Count:" + picCount);
            loadData();

        } else {
            mId = getIntent().getStringExtra("mId");
            loadAcceptData();//接受挑战
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == pagerList.size() - 1) {
                    mButton.setVisibility(View.VISIBLE);
                    mButton.setEnabled(true);
                } else {
                    mButton.setVisibility(View.INVISIBLE);
                    mButton.setEnabled(false);
                }

                pagerList.get(position).initData();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void loadData() {
        mPresenter.getPkInfo(GlobalConfig.USERID, vId, picCount);
    }

    private void loadAcceptData() {
        mPresenter.getSinglePkData(mId);
    }


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_challenge);
        mButton = (Button) findViewById(R.id.bt_submit);
        ll_error = (LinearLayout) findViewById(R.id.ll_error);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("双人挑战");
    }


    @Override
    public void showPkData(SingleChallengeData pkData) {
        picInfoList = pkData.getPictures();
        for (int i = 0; i < picInfoList.size(); i++) {
            pagerList.add(new MoreChallengePager(this, picInfoList.get(i)));
            pagerList.get(i).setSaveListener(this);
        }

        mAdapter = new ChallengePagerAdapter(pagerList);

        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void onSuccess(String msg) {
        new CanDialog.Builder(SingleChallengeActivity.this)
                .setTitle("成功提交")
                .setMessage(msg)
                .setNegativeButton("", true, null)
                .setPositiveButton("确认", true, new CanDialogInterface.OnClickListener() {
                    @Override
                    public void onClick(CanDialog dialog, int checkItem, CharSequence text, boolean[] checkItems) {
                        SingleChallengeActivity.this.finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

    public void submit(View view) {
        List<ChallengeData.ImgAndTags> iatList = new ArrayList<>();
        Iterator<String> iterator = mHashMap.keySet().iterator();

        while (iterator.hasNext()) {
            String picId = iterator.next();
            List<String> tags = mHashMap.get(picId);
            iatList.add(new ChallengeData().new ImgAndTags(picId, tags));
        }
        ChallengeData challengeData = new ChallengeData(GlobalConfig.USERID, mId, iatList);

        if (type == 0)//发起挑战
            mPresenter.submit(challengeData, AbsChallengePresenter.Type.SINGLE);
        else
            mPresenter.submit(challengeData, AbsChallengePresenter.Type.ACCEPT);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading(boolean isSuccess) {
        if (!isSuccess) {
            mViewPager.setVisibility(View.GONE);
            ll_error.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSave(View view, int position, List<String> selectedTags, String pId) {
        mHashMap.put(pId, selectedTags);
    }

    public void back(View view) {
        this.finish();
    }

    /**
     * 重新加载
     *
     * @param view
     */
    public void reLoad(View view) {
        mViewPager.setVisibility(View.VISIBLE);
        ll_error.setVisibility(View.GONE);
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
