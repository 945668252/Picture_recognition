package com.sendi.picture_recognition.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.sendi.picture_recognition.bean.GoodsData;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.view.adapter.GoodsAdapter;
import com.sendi.picture_recognition.presenter.IntegralShopPresenter;
import com.sendi.picture_recognition.view.activity.abs.IntegralShopView;
import com.sendi.picture_recognition.view.adapter.SearchPagerAdapter;
import com.sendi.picture_recognition.widget.popupwindow.CommonUtil;
import com.sendi.picture_recognition.widget.popupwindow.ComonPopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class IntegralShopActivity extends IntegralShopView implements ComonPopupWindow.ViewInterface {

    private final String TAG = this.getClass().getName();

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

    private IntegralShopPresenter mPresenter;

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
        mGoodsDataList = new ArrayList<>();
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


        mPresenter = new IntegralShopPresenter();
        mPresenter.bindView(this);
        mPresenter.shoeGoodsData();
    }

    @Override
    public void showGoodsData(List<GoodsData> goodsData) {
        mGoodsDataList.addAll(goodsData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void submitSuccess(String result,boolean isSuccess) {
        mPopupWindow.dismiss();
        if (isSuccess){
            new CanDialog.Builder(IntegralShopActivity.this)
                    .setTitle("兑换成功")
                    .setMessage(result)
                    .setPositiveButton("继续兑换", true, null)
                    .show();
        }else {
            new CanDialog.Builder(IntegralShopActivity.this)
                    .setTitle("兑换失败")
                    .setMessage(result)
                    .setPositiveButton("返回", true, null)
                    .show();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading(boolean isSuccess) {
        if (mPopupWindow != null)
            mPopupWindow.dismiss();
    }

    @Override
    public void getChildView(View view, int layoutResId, int position) {
        goodsImg = (ImageView) view.findViewById(R.id.iv_goods);
        goodsName = (TextView) view.findViewById(R.id.tv_goods_name);
        goodsIntegral = (TextView) view.findViewById(R.id.tv_goods_integral);
        goodsDescribe = (TextView) view.findViewById(R.id.tv_goods_description);
        et_name = (EditText) view.findViewById(R.id.et_name);
        et_phone = (EditText) view.findViewById(R.id.et_phone);
        et_address = (EditText) view.findViewById(R.id.et_address);
        mButton = (Button) view.findViewById(R.id.bt_submit);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = et_name.getText().toString();
                String phone = et_phone.getText().toString();
                if (phone.length() != 11) {
                    Toast.makeText(IntegralShopActivity.this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String address = et_address.getText().toString();
                String integral = mGoodsDataList.get(IntegralShopActivity.this.position).getIntegration();
                String gId = mGoodsDataList.get(IntegralShopActivity.this.position).getId();

                mPresenter.submitGoodsData(GlobalConfig.USERID, gId, integral, address, phone, name);
            }
        });

        Glide.with(this).load(mGoodsDataList.get(this.position).getPath()).into(goodsImg);
        goodsName.setText(mGoodsDataList.get(this.position).getName());
        goodsIntegral.setText(mGoodsDataList.get(this.position).getIntegration());
        goodsDescribe.setText(mGoodsDataList.get(this.position).getDescribe());
    }

    public void back(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
