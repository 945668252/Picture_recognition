package com.sendi.picture_recognition.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.base.BaseActivity;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.controller.activity.LoginActivity;
import com.sendi.picture_recognition.presenter.act.SettingPresenter;

/**
 * Created by Administrator on 2017/12/8.
 */

public class SettingActivity extends BaseActivity {

    private RadioGroup mRadioGroup;
    private SettingPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);
        initView();
        initData();
    }

    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_setting);
    }

    private void initData() {
        mPresenter = new SettingPresenter();
        mPresenter.bindView(this);
        mPresenter.initLgType(this);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.setting_rb_chinese:
                        GlobalConfig.LANGUAGETYPE = "0";
                        break;
                    case R.id.setting_rb_english:
                        GlobalConfig.LANGUAGETYPE = "1";
                        break;
                }
            }
        });

        if (GlobalConfig.LANGUAGETYPE.equals("0")) {

            mRadioGroup.check(R.id.setting_rb_chinese);//默认选中中文
        } else {
            mRadioGroup.check(R.id.setting_rb_english);//选中英文
        }
    }

    /**
     * 退出账号
     *
     * @param view
     */
    public void exit(View view) {
        mPresenter.exit(this);
    }

    public void toLogin(String userName) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
        finish();
    }

    public void back(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading(boolean isSuccess) {

    }
}
