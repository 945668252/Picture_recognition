package com.sendi.picture_recognition.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.canyinghao.candialog.CanDialog;
import com.canyinghao.candialog.CanDialogInterface;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.ChallengeData;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.view.adapter.ChallengePagerAdapter;
import com.sendi.picture_recognition.presenter.abstract_act.AbsChallengePresenter;
import com.sendi.picture_recognition.presenter.act.ChallengePresenter;
import com.sendi.picture_recognition.view.activity.abs.AbsChallengeView;
import com.sendi.picture_recognition.view.pager.MoreChallengePager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MoreChallengeActivity extends AbsChallengeView<List<HomePicInfo>> implements MoreChallengePager.OnSaveItemClickListener {

    private ViewPager mViewPager;
    private ChallengePagerAdapter mAdapter;
    private Button mButton;
    private LinearLayout ll_error;
    private List<MoreChallengePager> pagerList;
    private List<HomePicInfo> picInfoList;
    private HashMap<String, List<String>> mHashMap;//记录对应图片被选择过的照片
    private String mId;
    private AbsChallengePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        initView();

        initData();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_challenge);
        mButton = (Button) findViewById(R.id.bt_submit);
        ll_error = (LinearLayout) findViewById(R.id.ll_error);
    }

    private void initData() {
        mHashMap = new HashMap<>();
        pagerList = new ArrayList<>();
        picInfoList = new ArrayList<>();
        mId = getIntent().getStringExtra("mId");
        //loadData
        mPresenter = new ChallengePresenter();
        mPresenter.bindView(this);
        mPresenter.getPkInfo(mId);

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

    @Override
    public void showPkData(List<HomePicInfo> pkData) {
        picInfoList = pkData;
        for (int i = 0; i < picInfoList.size(); i++) {
            pagerList.add(new MoreChallengePager(this, picInfoList.get(i)));
            pagerList.get(i).setSaveListener(MoreChallengeActivity.this);
        }

        mAdapter = new ChallengePagerAdapter(pagerList);

        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void onSuccess(String msg) {
        new CanDialog.Builder(MoreChallengeActivity.this)
                .setTitle("成功提交")
                .setMessage(msg)
                .setNegativeButton("", true, null)
                .setPositiveButton("确认", true, new CanDialogInterface.OnClickListener() {
                    @Override
                    public void onClick(CanDialog dialog, int checkItem, CharSequence text, boolean[] checkItems) {
                        MoreChallengeActivity.this.finish();
                    }
                })
                .setCancelable(false)
                .show();
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

    /**
     * 保存志愿者选中的标签和对应的图片
     * @param view
     * @param position
     * @param selectedTags
     * @param pId
     */
    @Override
    public void onSave(View view, int position, List<String> selectedTags, String pId) {
        mHashMap.put(pId, selectedTags);
    }

    /**
     * 提交标注
     * @param view
     */
    public void submit(View view) {
        List<ChallengeData.ImgAndTags> iatList = new ArrayList<>();
        Iterator<String> iterator = mHashMap.keySet().iterator();

        while (iterator.hasNext()) {
            String picId = iterator.next();
            List<String> tags = mHashMap.get(picId);
            iatList.add(new ChallengeData().new ImgAndTags(picId, tags));
        }

        ChallengeData challengeData = new ChallengeData(GlobalConfig.USERID, mId, iatList);

        mPresenter.submit(challengeData, AbsChallengePresenter.Type.MORE);
    }
    public void back(View view) {
        this.finish();
    }

    /**
     * 重新加载
     * @param view
     */
    public void reLoad(View view) {
        mViewPager.setVisibility(View.VISIBLE);
        ll_error.setVisibility(View.GONE);
        mPresenter.getPkInfo(mId);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
