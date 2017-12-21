package com.sendi.picture_recognition.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.User;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;
import com.sendi.picture_recognition.utils.httputils.sqliteutils.SqliteDBUtils;
import com.sendi.picture_recognition.view.activity.MainActivity;
import com.sendi.userdb.UserDao;

public class LoginActivity extends BaseActivity {
    private final String TAG = this.getClass().getName();
    private EditText et_user_name;
    private EditText et_user_psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    private void initData() {
        String userName=getIntent().getStringExtra("userName");
        if (TextUtils.isEmpty(userName))return;
        et_user_name.setText(userName);
    }

    private void initView() {
        et_user_name= (EditText) findViewById(R.id.et_user_name);
        et_user_psw= (EditText) findViewById(R.id.et_user_psw);
    }

    /**
     * 前去注册
     *
     * @param view
     */
    public void toRegister(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();
    }

    /**
     * 登录
     *
     * @param view
     */
    public void login(View view) {
        String userName=et_user_name.getText().toString();
        String psw=et_user_psw.getText().toString();
        Log.i(TAG, "login: "+userName);
        Log.i(TAG, "login: "+psw);
        //登录
        RetrofitFactory.getInstance()
                .getAPI()
                .login(userName, psw)
                .compose(this.<BaseEntity<User>>setThread())
                .subscribe(new BaseObserver<User>() {
                    @Override
                    protected void onSuccess(BaseEntity<User> t) throws Exception {
                        //保存用户信息
                        saveUserInfo(t.getData());
                        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                    @Override
                    protected void onRequestEnd(boolean isSuccess) {
                        super.onRequestEnd(isSuccess);
                        if (!isSuccess){
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 将用户信息保存到本地数据库
     * @param user
     */
    private void saveUserInfo(User user) {
        Log.i(TAG, "saveUserInfo: "+user.getUserNickname());
        UserDao userDao= SqliteDBUtils
                .getInstance(this)
                .getUserDao();
        //添加
        com.sendi.userdb.User mUser=new com.sendi.userdb.User();
        mUser.setUser_id(user.getUserId());//志愿号
        mUser.setUser_pic_url(user.getUser_pic_url());//头像
        mUser.setUser_nickname(user.getUserNickname());//名字
        mUser.setId(user.getId());//用户id
        mUser.setGander(user.getGender());//性别
        mUser.setHobbies(user.getHobbies());//爱好
        mUser.setPhone_number(user.getPhone_number());//手机号码
        userDao.insert(mUser);
       
    }
}
