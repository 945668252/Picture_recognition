package com.sendi.picture_recognition.controller.activity;

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
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.controller.adapter.ChallengePagerAdapter;
import com.sendi.picture_recognition.controller.pager.challenge.MoreChallengePager;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ChallengeData;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.bean.SingleChallengeData;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/6/25.
 */

public class SingleChallengeActivity extends BaseActivity implements MoreChallengePager.OnSaveItemClickListener {
    private final String TAG = ChallengeActivity.class.getName();
    private ViewPager mViewPager;
    private ChallengePagerAdapter mAdapter;
    private Button mButton;
    private LinearLayout ll_error;
    private TextView tv_title;
    private List<MoreChallengePager> pagerList;
    private List<HomePicInfo> picInfoList;
    private List<String> tagsList;
    private List<String> selectedList;
    private HashMap<String, List<String>> mHashMap;//记录对应图片被选择过的照片
    private String mId;
    private String vId;
    private String picCount;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        type = getIntent().getIntExtra("type", 0);
        Log.i(TAG, "onCreate: "+type);
        initView();

        initData();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_challenge);
        mButton = (Button) findViewById(R.id.bt_submit);
        ll_error = (LinearLayout) findViewById(R.id.ll_error);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("双人挑战");
    }

    private void initData() {
        mHashMap = new HashMap<>();
        pagerList = new ArrayList<>();
        picInfoList = new ArrayList<>();

        if (type == 0) {//创建挑战
            vId = getIntent().getStringExtra("vId");
            picCount = getIntent().getStringExtra("picCount");
            Log.i(TAG, "initData: " + mId + " Count:" + picCount);
            loadData();

        } else {
            mId = getIntent().getStringExtra("mId");
            loadAcceptData();//接受挑战
        }


//        List<String>sList=new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            sList.add("标签："+i);
//        }
//        for (int i = 0; i < 10; i++) {
//            pagerList.add(new MoreChallengePager(this,sList,""+i));
//            pagerList.get(i).setSaveListener(ChallengeActivity.this);
//        }
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

    private void loadAcceptData() {
        RetrofitFactory
                .getInstance()
                .getAPI()
                .getSinglePkData(mId)
                .compose(this.<BaseEntity<SingleChallengeData>>setThread())
                .subscribe(new BaseObserver<SingleChallengeData>() {
                    @Override
                    protected void onSuccess(BaseEntity<SingleChallengeData> t) throws Exception {

                        showPkData(t.getData().getPictures());
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void loadData() {
        RetrofitFactory
                .getInstance()
                .getAPI()
                .getPkData(GlobalConfig.USERID, vId, picCount)
                .compose(this.<BaseEntity<SingleChallengeData>>setThread())
                .subscribe(new BaseObserver<SingleChallengeData>() {
                    @Override
                    protected void onSuccess(BaseEntity<SingleChallengeData> t) throws Exception {

                        mId = t.getData().getPkId();
                        showPkData(t.getData().getPictures());
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.i(TAG, "onConnectFailure: ");
                        mViewPager.setVisibility(View.GONE);
                        ll_error.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected void onCodeError(BaseEntity<SingleChallengeData> t) throws Exception {
                        super.onCodeError(t);
                        Log.i(TAG, "onCodeError: ");
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
            pagerList.get(i).setSaveListener(SingleChallengeActivity.this);
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
        Log.i(TAG, "submit: 提交标签");
//        Toast.makeText(this, "提交标签", Toast.LENGTH_SHORT).show();
        List<ChallengeData.ImgAndTags> iatList = new ArrayList<>();
        Iterator<String> iterator = mHashMap.keySet().iterator();

        while (iterator.hasNext()) {
            String picId = iterator.next();
            List<String> tags = mHashMap.get(picId);
            iatList.add(new ChallengeData().new ImgAndTags(picId, tags));
        }
//        int userId=Integer.parseInt(GlobalConfig.USERID);
//        int pkId=Integer.parseInt(mId);
//        Log.i(TAG, "submit: "+userId);
//        Log.i(TAG, "submit: "+pkId);
        ChallengeData challengeData = new ChallengeData(GlobalConfig.USERID, mId, iatList);

        Gson gson = new Gson();
        String str = gson.toJson(challengeData);
        Log.i(TAG, "submit: " + str);

        if (type == 0) {//发起挑战提交数据
            RetrofitFactory
                    .getInstance()
                    .getAPI()
                    .submitPkData(challengeData)
                    .compose(this.<BaseEntity<String>>setThread())
                    .subscribe(new BaseObserver<String>() {
                        @Override
                        protected void onSuccess(BaseEntity<String> t) throws Exception {
                            new CanDialog.Builder(SingleChallengeActivity.this)
                                    .setTitle("成功提交")
                                    .setMessage(t.getData())
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

                        @Override
                        protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                        }
                    });
        } else {
            RetrofitFactory
                    .getInstance()
                    .getAPI()
                    .submitAcceptPkData(challengeData)
                    .compose(this.<BaseEntity<String>>setThread())
                    .subscribe(new BaseObserver<String>() {
                        @Override
                        protected void onSuccess(BaseEntity<String> t) throws Exception {
                            new CanDialog.Builder(SingleChallengeActivity.this)
                                    .setTitle("成功提交")
                                    .setMessage(t.getData())
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

                        @Override
                        protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                        }
                    });
        }

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
