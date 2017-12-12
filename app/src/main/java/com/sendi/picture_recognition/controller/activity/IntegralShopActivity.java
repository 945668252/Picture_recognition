package com.sendi.picture_recognition.controller.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.canyinghao.candialog.CanDialog;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.controller.adapter.GoodsAdapter;
import com.sendi.picture_recognition.view.adapter.SearchPagerAdapter;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.GoodsData;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;
import com.sendi.picture_recognition.widget.popupwindow.CommonUtil;
import com.sendi.picture_recognition.widget.popupwindow.ComonPopupWindow;

import java.util.List;

public class IntegralShopActivity extends BaseActivity implements ComonPopupWindow.ViewInterface {

    private final String TAG = IntegralShopActivity.class.getName();

    private RecyclerView mRecyclerView;

    private GoodsAdapter mAdapter;

    private List<GoodsData> mGoodsDataList;

    private ComonPopupWindow mPopupWindow;

    private View upView;

    private ImageView goodsImg;
    private TextView goodsName;
    private TextView goodsIntegral;
    private TextView goodsDescribe;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_address;
    private Button mButton;

    private static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);
        initView();
        initData();

    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_goods);
        upView = View.inflate(this, R.layout.edit_info_popup_layout, null);

    }

    private void initData() {
        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData() {
        RetrofitFactory
                .getInstance()
                .getAPI()
                .getGoodsData()
                .compose(this.<BaseEntity<List<GoodsData>>>setThread())
                .subscribe(new BaseObserver<List<GoodsData>>() {
                    @Override
                    protected void onSuccess(BaseEntity<List<GoodsData>> t) throws Exception {
                        Log.i(TAG, "onSuccess: " + t.toString());
                        showData(t.getData());
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
     */
    private void showData(List<GoodsData> data) {
        mGoodsDataList = data;
        mAdapter = new GoodsAdapter(this, mGoodsDataList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        CommonUtil.measureWidthAndHeight(upView);
        mAdapter.setListener(new SearchPagerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (mPopupWindow != null && mPopupWindow.isShowing()) return;
                IntegralShopActivity.this.position = position;
                mPopupWindow = new ComonPopupWindow.Builder(IntegralShopActivity.this)
                        .setView(R.layout.edit_info_popup_layout)
                        .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                        .setAnimationStyle(R.style.AnimUp)
                        .setViewOnclickListener(IntegralShopActivity.this)
                        .setBackgroundLevel(0.5f)
                        .setOutsideTouchable(true)
                        .create(position);
                mPopupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
            }
        });
    }

    @Override
    public void getChildView(View view, int layoutResId, int position) {
        goodsImg = (ImageView) view.findViewById(R.id.iv_goods);
        goodsName = (TextView) view.findViewById(R.id.tv_goods_name);
        goodsIntegral = (TextView) view.findViewById(R.id.tv_goods_integral);
        goodsDescribe= (TextView) view.findViewById(R.id.tv_goods_description);
        et_name = (EditText) view.findViewById(R.id.et_name);
        et_phone = (EditText) view.findViewById(R.id.et_phone);
        et_address = (EditText) view.findViewById(R.id.et_address);
        mButton = (Button) view.findViewById(R.id.bt_submit);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = et_name.getText().toString();
                String phone = et_phone.getText().toString();
                if (phone.length()!=11){
                    Toast.makeText(IntegralShopActivity.this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String address = et_address.getText().toString();
                String integral = mGoodsDataList.get(IntegralShopActivity.this.position).getIntegration();
                String gId = mGoodsDataList.get(IntegralShopActivity.this.position).getId();
                //提交数据
                RetrofitFactory
                        .getInstance()
                        .getAPI()
                        .submitGoodsData(GlobalConfig.USERID, gId, integral, address, phone, name)
                        .compose(IntegralShopActivity.this.<BaseEntity<String>>setThread())
                        .subscribe(new BaseObserver<String>() {
                            @Override
                            protected void onSuccess(BaseEntity<String> t) throws Exception {
                                Toast.makeText(IntegralShopActivity.this, "兑换成功", Toast.LENGTH_SHORT).show();
                                Log.i(TAG, "onSuccess: ");
                                mPopupWindow.dismiss();
                                new CanDialog.Builder(IntegralShopActivity.this)
//                                        .setIcon(R.mipmap.shop_icon)
//                                        .setIcon(R.drawable.shop_icon)
                                        .setTitle("兑换成功")
                                        .setMessage("恭喜你，兑换成功！")
                                        .setPositiveButton("继续兑换", true, null)
                                        .show();
                            }

                            @Override
                            protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {
                                Log.i(TAG, "onConnectFailure: "+isNetWorkError);
                            }

                            @Override
                            protected void onCodeError(BaseEntity<String> t) throws Exception {
                                super.onCodeError(t);
                                Log.i(TAG, "onCodeError: ");
                                mPopupWindow.dismiss();
                                new CanDialog.Builder(IntegralShopActivity.this)
//                                        .setIcon(R.mipmap.shop_icon)
//                                        .setIcon(R.drawable.shop_icon)
                                        .setMessage(t.getData().toString())
                                        .setPositiveButton("确认", true, null)
                                        .show();
                            }
                        });

            }
        });

        Glide.with(this).load(mGoodsDataList.get(this.position).getPath()).into(goodsImg);
        goodsName.setText(mGoodsDataList.get(this.position).getName());
        goodsIntegral.setText(mGoodsDataList.get(this.position).getIntegration());
        goodsDescribe.setText(mGoodsDataList.get(this.position).getDescribe());
        Log.i(TAG, "getChildView: " + mGoodsDataList.get(this.position).getName());
        Log.i(TAG, "getChildView: " + mGoodsDataList.get(this.position).getDescribe());
    }

    /**
     * 返回
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }
}
