package com.sendi.picture_recognition.controller.activity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.canyinghao.candialog.CanDialog;
import com.canyinghao.candialog.CanDialogInterface;
import com.google.gson.Gson;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.controller.adapter.ChallengePagerAdapter;
import com.sendi.picture_recognition.controller.pager.challenge.MoreChallengePager;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ChallengeData;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 思路：1.创建选择的n个数据模型对象（图片url、图片id、标签集）
 * 2.创建列表保存这n个数据模型对象
 * 3.在对应的第n个对象进行标签集合的操作
 * 4.Map<图片id,List<标签>>
 */
public class ChallengeActivity extends BaseActivity implements MoreChallengePager.OnSaveItemClickListener {

    private final String TAG = ChallengeActivity.class.getName();
    private ViewPager mViewPager;
    private ChallengePagerAdapter mAdapter;
    private Button mButton;
    private LinearLayout ll_error;
    private List<MoreChallengePager> pagerList;
    private List<HomePicInfo> picInfoList;
    private List<String> tagsList;
    private List<String> selectedList;
    private HashMap<String, List<String>> mHashMap;//记录对应图片被选择过的照片
    private String mId;

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
        Log.i(TAG, "initData: " + mId);
        loadData();

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
        RetrofitFactory
                .getInstance()
                .getAPI()
                .getMorePKInfo(mId)
                .compose(this.<BaseEntity<List<HomePicInfo>>>setThread())
                .subscribe(new BaseObserver<List<HomePicInfo>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<HomePicInfo>> t) throws Exception {
                        Log.i(TAG, "onSuccess: " + t.toString());
                        showPkData(t.getData());
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mViewPager.setVisibility(View.GONE);
                        ll_error.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void showPkData(List<HomePicInfo> list) {
        //图片url
        //标签
        picInfoList = list;
        for (int i = 0; i < picInfoList.size(); i++) {
            pagerList.add(new MoreChallengePager(this, picInfoList.get(i)));
            pagerList.get(i).setSaveListener(ChallengeActivity.this);
        }

        mAdapter = new ChallengePagerAdapter(pagerList);

        mViewPager.setAdapter(mAdapter);
    }

    /**
     * 进行保存
     *
     * @param view
     * @param position 选中标签的位置
     * @param pId      图片的id
     */
    @Override
    public void onSave(View view, int position, List<String> selectedTags, String pId) {
        Log.i(TAG, "onSave: index" + position + "pId:" + pId + " tags:" + selectedTags);
        mHashMap.put(pId, selectedTags);
    }

    /**
     * 提交竞赛
     *
     * @param view
     */
    public void submit(View view) {
        Log.i(TAG, "submit: ");
        List<ChallengeData.ImgAndTags> iatList = new ArrayList<>();
        Iterator<String> iterator = mHashMap.keySet().iterator();

        while (iterator.hasNext()) {
            String picId = iterator.next();
            List<String> tags = mHashMap.get(picId);
            iatList.add(new ChallengeData().new ImgAndTags(picId, tags));
        }

//        ChallengeData challengeData=new ChallengeData(GlobalConfig.USERID,mId,iatList);
//        int userId=Integer.parseInt();
//        int pkId=Integer.parseInt(mId);
        ChallengeData challengeData = new ChallengeData(GlobalConfig.USERID, mId, iatList);

        Gson gson = new Gson();
        String str = gson.toJson(challengeData);
        Log.i(TAG, "submit: " + str);

        RetrofitFactory
                .getInstance()
                .getAPI()
                .submitMorePkData(challengeData)
                .compose(this.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        new CanDialog.Builder(ChallengeActivity.this)
                                .setTitle("成功提交")
                                .setMessage(t.getData())
                                .setNegativeButton("", true, null)
                                .setPositiveButton("确认", true, new CanDialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(CanDialog dialog, int checkItem, CharSequence text, boolean[] checkItems) {
                                        ChallengeActivity.this.finish();
                                    }
                                })
                                .setCancelable(false)
                                .show();
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

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
}
