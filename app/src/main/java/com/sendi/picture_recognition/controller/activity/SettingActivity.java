package com.sendi.picture_recognition.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.sendi.picture_recognition.controller.ActivityController;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.utils.httputils.sputils.SPUtils;
import com.sendi.picture_recognition.utils.httputils.sqliteutils.SqliteDBUtils;

public class SettingActivity extends Activity {

    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);
        initView();
        initData();
    }

    private void initData() {
        GlobalConfig.LANGUAGETYPE = SPUtils.getLanguageType(this);
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
                SPUtils.setLanguageType(SettingActivity.this, GlobalConfig.LANGUAGETYPE);
//                GlobalConfig.LANGUAGETYPE=languageType;
            }
        });
        if (GlobalConfig.LANGUAGETYPE.equals("0")) {

            mRadioGroup.check(R.id.setting_rb_chinese);//默认选中中文
        } else {
            mRadioGroup.check(R.id.setting_rb_english);//选中英文
        }
    }

    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_setting);
    }


    public void back(View view) {
        finish();
    }

    /**
     * 退出当前账号
     *
     * @param view
     */
    public void exit(View view) {
        //从数据库伤处该用户信息
        String userName = SqliteDBUtils
                .getInstance(this)
                .getUserDao()
                .queryBuilder()
                .build()
                .listLazy()
                .get(0)
                .getUser_id();
        SqliteDBUtils
                .getInstance(this)
                .getUserDao()
                .deleteAll();
        //删除历史记录
        SqliteDBUtils
                .getInstance(this)
                .getRecordDataDao()
                .deleteAll();
        //完全退出
        ActivityController.finishAll();
        //将该用户的账号传到登录界面
        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
        finish();
    }
}
