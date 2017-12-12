package com.sendi.picture_recognition.controller.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.utils.httputils.sputils.SPUtils;
import com.sendi.picture_recognition.utils.httputils.sqliteutils.SqliteDBUtils;
import com.sendi.picture_recognition.view.activity.MainActivity;
import com.sendi.userdb.DaoMaster;
import com.sendi.userdb.DaoSession;
import com.sendi.userdb.User;
import com.sendi.userdb.UserDao;

import java.util.List;

public class SplashActivity extends BaseActivity {
    private final String TAG=this.getClass().getName();
    private SQLiteDatabase db;
    private UserDao mUserDao;
    private DaoSession mSession;
    private DaoMaster mDaoMaster;
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isFinishing()){
                return;
            }else {
                toMainOrLogin();
            }
        }
    };

    /**
     * 判断是否有用户登陆过
     */
    private void toMainOrLogin() {
        List<User>list=mUserDao.queryBuilder().listLazy();
        if (list.size()!=0) {//直接跳转到主界面
            Log.i(TAG, "toMainOrLogin: "+list);
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }else {//跳转到登录界面
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        }
        finish();
    }

    private void openDb() {
        mUserDao= SqliteDBUtils
                .getInstance(this)
                .getUserDao();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        initListener();
        openDb();
        GlobalConfig.LANGUAGETYPE= SPUtils.getLanguageType(this);//初始化语言设置
        mHandler.sendMessageDelayed(mHandler.obtainMessage(),2000);
    }

    /**
     * 初始化语音引擎
     */
    private void initListener() {
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=592695f2");
    }

}
