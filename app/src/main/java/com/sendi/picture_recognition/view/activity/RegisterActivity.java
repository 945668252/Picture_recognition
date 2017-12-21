package com.sendi.picture_recognition.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.canyinghao.candialog.CanDialog;
import com.canyinghao.candialog.CanDialogInterface;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.presenter.abstract_act.AbsRegisterPresenter;
import com.sendi.picture_recognition.presenter.act.RegisterPresenter;
import com.sendi.picture_recognition.view.activity.abs.AbsRegisterView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Administrator on 2017/12/20.
 */

public class RegisterActivity extends AbsRegisterView {

    private EditText et_name;
    private EditText et_psw;
    private EditText et_sex;
    private EditText et_hobbies;
    private EditText et_number;
    private EditText et_code;
    private Button bt_get;
    private final int timeCount = 60;//倒计时60 S

    private AbsRegisterPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        mPresenter=new RegisterPresenter();
        mPresenter.bindView(this);
    }

    /**
     * 初始化View
     */
    private void initView() {
        et_name = (EditText) findViewById(R.id.et_register_user_name);
        et_psw = (EditText) findViewById(R.id.et_register_psw);
        et_sex = (EditText) findViewById(R.id.et_register_sex);
        et_hobbies = (EditText) findViewById(R.id.et_register_like);
        et_number = (EditText) findViewById(R.id.et_register_phone_number);
        et_code = (EditText) findViewById(R.id.et_register_code);
        bt_get = (Button) findViewById(R.id.bt_get_code);
    }

    /**
     * 获取验证码
     *
     * @param view
     */
    public void getCode(View view) {
        String mobileNumber = et_number.getText().toString();
        mPresenter.getCode(mobileNumber);
    }

    /**
     * 提交注册信息
     *
     * @param view
     */
    public void register(View view) {
        String name = et_name.getText().toString();
        String psw = et_psw.getText().toString();
        String hobbies = et_hobbies.getText().toString();
        String gender = et_sex.getText().toString();
        String sex;
        if (gender.equals("男")) {
            sex = "1";
        } else {
            sex = "0";
        }
        String mobil = et_number.getText().toString();
        String code = et_code.getText().toString();

        mPresenter.register(name, psw, hobbies,
                sex, mobil, code);
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

    @Override
    public void registerSuccess() {
        new CanDialog.Builder(RegisterActivity.this)
                .setMessage("注册成功")
                .setNegativeButton("", false, null)
                .setPositiveButton("去登录", true, new CanDialogInterface.OnClickListener() {
                    @Override
                    public void onClick(CanDialog dialog, int checkItem, CharSequence text, boolean[] checkItems) {
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .setCancelable(true)
                .show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading(boolean isSuccess) {

    }
    /**
     * 跳转到登录页
     *
     * @param view
     */
    public void toLogin(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }
}
