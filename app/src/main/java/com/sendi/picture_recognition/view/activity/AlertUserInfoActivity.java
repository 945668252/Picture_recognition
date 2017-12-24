package com.sendi.picture_recognition.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.presenter.abstract_act.AbsAlertUserInfoPresenter;
import com.sendi.picture_recognition.presenter.act.AlertUserInfoPresenter;
import com.sendi.picture_recognition.view.activity.abs.AbsUserInfoView;
import com.sendi.userdb.User;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 修改用户信息
 */

public class AlertUserInfoActivity extends AbsUserInfoView implements View.OnClickListener {


    private final String TAG = this.getClass().getName();
    private TextView title;

    private EditText et_name;

    private LinearLayout ll_phone;
    private EditText et_phone_number;
    private EditText et_code;
    private Button bt_get;

    private LinearLayout ll_sex;
    private TextView tv_boy;
    private TextView tv_girl;

    private EditText et_hobby;

    private String flag = "1";

    private String genderId = "1";

    private AbsAlertUserInfoPresenter mPresenter;

    private final int timeCount = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_user_info);
        flag = getIntent().getStringExtra("flag");
        Log.i(TAG, "onCreate: " + flag);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.tv_title);
        et_name = (EditText) findViewById(R.id.et_name);
        ll_phone = (LinearLayout) findViewById(R.id.ll_phone);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_code = (EditText) findViewById(R.id.et_code);
        bt_get = (Button) findViewById(R.id.bt_get_code);
        ll_sex = (LinearLayout) findViewById(R.id.ll_sex);
        tv_boy = (TextView) findViewById(R.id.tv_boy);
        tv_girl = (TextView) findViewById(R.id.tv_girl);
        et_hobby = (EditText) findViewById(R.id.et_hobbies);
    }

    private void initData() {
        mPresenter = new AlertUserInfoPresenter();
        mPresenter.bindView(this);
        mPresenter.initUser(getApplicationContext());
    }

    @Override
    public void showUserInfo(User user) {
        switch (flag) {
            case "1"://名字
                et_name.setVisibility(View.VISIBLE);
                break;
            case "3"://手机号码
                title.setText("手机号码");
                ll_phone.setVisibility(View.VISIBLE);
                break;
            case "4"://性别
                title.setText("性别");
                genderId = getIntent().getStringExtra("genderId");
                Log.i(TAG, "initData: " + genderId);
                switchGender(genderId);
                ll_sex.setVisibility(View.VISIBLE);
                break;
            case "5"://喜爱的事物
                title.setText("热爱的事物");
                et_hobby.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void switchGender(String genderId) {
        if (genderId.equals("1")) {
            tv_boy.setTextColor(getResources().getColor(R.color.colorWhite));
            tv_boy.setBackgroundColor(getResources().getColor(R.color.colorTheme));
            tv_girl.setTextColor(getResources().getColor(R.color.colorTheme));
            tv_girl.setBackgroundDrawable(getResources().getDrawable(R.drawable.make_tag_item_bg));
        } else {
            tv_girl.setTextColor(getResources().getColor(R.color.colorWhite));
            tv_girl.setBackgroundColor(getResources().getColor(R.color.colorTheme));
            tv_boy.setTextColor(getResources().getColor(R.color.colorTheme));
            tv_boy.setBackgroundDrawable(getResources().getDrawable(R.drawable.make_tag_item_bg));
        }
    }

    private void setListener() {
        tv_boy.setOnClickListener(this);
        tv_girl.setOnClickListener(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading(boolean isSuccess) {

    }

    @Override
    public void alertUserPic(String path) {

    }

    @Override
    public void getCodeSuccess() {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(timeCount + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return timeCount - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        bt_get.setEnabled(false);
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        bt_get.setText("剩余" + aLong + "秒");
                        if (aLong == 0) {
                            bt_get.setEnabled(true);
                            bt_get.setText("获取验证码");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //提交信息
    public void submitInfo(View view) {
        switch (flag) {
            case "1"://名字
                final String name = et_name.getText().toString();
                mPresenter.alertUerNickname(name, GlobalConfig.USERID);
                break;
            case "3"://手机号码&验证码
                final String newPhoneNumber = et_phone_number.getText().toString();
                String code = et_code.getText().toString();
                mPresenter.alertUserPhoneNumber(newPhoneNumber, code, GlobalConfig.USERID);
                break;
            case "4"://性别
                mPresenter.alertUserGender(genderId, GlobalConfig.USERID);
                break;
            case "5"://喜爱的事物
                final String hobbies = et_hobby.getText().toString();
                mPresenter.alertUserHobbies(hobbies, GlobalConfig.USERID);
                break;
            default:
                break;
        }

    }

    public void getCode(View view) {
        String phoneNumber = et_phone_number.getText().toString();
        mPresenter.getCode(phoneNumber);
    }


    @Override
    public void onClick(View v) {
        //切换性别
        switch (v.getId()) {
            case R.id.tv_boy:
                Log.i(TAG, "onClick: ");
                genderId = "1";
                break;
            case R.id.tv_girl:
                genderId = "2";
                break;
        }
        switchGender(genderId);
    }

    public void back(View view) {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
