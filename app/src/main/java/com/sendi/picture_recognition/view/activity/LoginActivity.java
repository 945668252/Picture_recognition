package com.sendi.picture_recognition.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.base.BaseActivity;
import com.sendi.picture_recognition.bean.User;
import com.sendi.picture_recognition.presenter.act.LoginPresenter;

/**
 * Created by Administrator on 2017/12/20.
 */

public class LoginActivity extends BaseActivity {

    private final String TAG = this.getClass().getName();
    private EditText et_user_name;
    private EditText et_user_psw;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new LoginPresenter();
        String userName = getIntent().getStringExtra("userName");
        if (TextUtils.isEmpty(userName)) return;

        et_user_name.setText(userName);
    }

    private void initView() {
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_user_psw = (EditText) findViewById(R.id.et_user_psw);
    }


    /**
     * 登录
     *
     * @param view
     */
    public void login(View view) {
        String userName = et_user_name.getText().toString();
        String psw = et_user_psw.getText().toString();

        mPresenter.login(userName,psw);
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

    public void loginSuccess(User user) {
        //缓存用户数据
        mPresenter.saveUserInfo(user, this);

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
        }
    }
}
