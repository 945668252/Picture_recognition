package com.sendi.picture_recognition.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.base.BaseActivity;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.presenter.abstract_act.AbsGradepresenter;
import com.sendi.picture_recognition.presenter.act.GradePresenter;
import com.sendi.picture_recognition.widget.ProgressCircle;

/**
 * Created by Administrator on 2017/12/8.
 * 重构
 * 个人积分
 */

public class GradeActivity extends BaseActivity {
    private ProgressCircle mProgressCircle;
    private TextView tv_grade;
    private AbsGradepresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        initView();
        initData();
    }

    private void initView() {
        mProgressCircle = (ProgressCircle) findViewById(R.id.pc);
        tv_grade = (TextView) findViewById(R.id.tv_grade);
        mProgressCircle.setProgressBorderWidth(40);
    }

    private void initData() {
        mPresenter = new GradePresenter();
        mPresenter.bindView(this);
        mPresenter.getIntegral(GlobalConfig.USERID);
    }

    public void showIntegral(float integral) {
        mProgressCircle.setProgressCurrent(integral);
        mProgressCircle.startAni();//开始动画
        tv_grade.setText(integral + "");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading(boolean isSuccess) {

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
