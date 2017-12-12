package com.sendi.picture_recognition.controller.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;
import com.sendi.picture_recognition.utils.httputils.sqliteutils.SqliteDBUtils;
import com.sendi.userdb.User;
import com.sendi.userdb.UserDao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class AlertUserInfoActivity extends BaseActivity implements View.OnClickListener {
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

    private String userId;

    private UserDao userDao;
    private User mUser;

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

    private void setListener() {
        tv_boy.setOnClickListener(this);
        tv_girl.setOnClickListener(this);
    }

    private void initData() {

        userDao = SqliteDBUtils
                .getInstance(this)
                .getUserDao();
        List<User> list = userDao.queryBuilder().listLazy();
        mUser = list.get(0);
        userId = GlobalConfig.USERID;

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

    private void initView() {
        title = (TextView) findViewById(R.id.tv_title);
        et_name = (EditText) findViewById(R.id.et_name);
        ll_phone = (LinearLayout) findViewById(R.id.ll_phone);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_code = (EditText) findViewById(R.id.et_code);
        bt_get= (Button) findViewById(R.id.bt_get_code);
        ll_sex = (LinearLayout) findViewById(R.id.ll_sex);
        tv_boy = (TextView) findViewById(R.id.tv_boy);
        tv_girl = (TextView) findViewById(R.id.tv_girl);
        et_hobby = (EditText) findViewById(R.id.et_hobbies);

    }

    public void back(View view) {
        this.finish();
    }

    //提交信息
    public void submitInfo(View view) {
        switch (flag) {
            case "1"://名字
                final String name = et_name.getText().toString();
                RetrofitFactory
                        .getInstance()
                        .getAPI()
                        .alertUerNickname(name, userId)
                        .compose(this.<BaseEntity<String>>setThread())
                        .subscribe(new BaseObserver<String>() {
                            @Override
                            protected void onSuccess(BaseEntity<String> t) throws Exception {
                                //修改本地
                                Log.i(TAG, "onSuccess: " + t.getData());
                                mUser.setUser_nickname(name);
                                userDao.update(mUser);
                                //返回资料界面
                                finish();
                            }

                            @Override
                            protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                            }
                        });
                break;
            case "3"://手机号码&验证码
                final String newPhoneNumber = et_phone_number.getText().toString();
                String code = et_code.getText().toString();
                RetrofitFactory
                        .getInstance()
                        .getAPI()
                        .alertPhoneNumber(newPhoneNumber, code, mUser.getUser_id())
                        .compose(this.<BaseEntity<String>>setThread())
                        .subscribe(new BaseObserver<String>() {
                            @Override
                            protected void onSuccess(BaseEntity<String> t) throws Exception {
                                //修改本地
                                mUser.setPhone_number(newPhoneNumber);
                                userDao.update(mUser);
                                //返回资料界面
                                finish();
                            }

                            @Override
                            protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                            }

                            @Override
                            protected void onCodeError(BaseEntity<String> t) throws Exception {
                                super.onCodeError(t);
                                Toast.makeText(AlertUserInfoActivity.this, t.getData()+"", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case "4"://性别
//                genderId
                RetrofitFactory
                        .getInstance()
                        .getAPI()
                        .alertGender(genderId, userId)
                        .compose(this.<BaseEntity<String>>setThread())
                        .subscribe(new BaseObserver<String>() {
                            @Override
                            protected void onSuccess(BaseEntity<String> t) throws Exception {
                                //修改本地
                                mUser.setGander(genderId);
                                userDao.update(mUser);
                                //返回资料界面
                                finish();
                            }

                            @Override
                            protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                            }
                        });
                break;
            case "5"://喜爱的事物
                final String hobbies = et_hobby.getText().toString();
                RetrofitFactory
                        .getInstance()
                        .getAPI()
                        .alertHobbies(hobbies, userId)
                        .compose(this.<BaseEntity<String>>setThread())
                        .subscribe(new BaseObserver<String>() {
                            @Override
                            protected void onSuccess(BaseEntity<String> t) throws Exception {
                                //修改本地
                                mUser.setHobbies(hobbies);
                                userDao.update(mUser);
                                //返回资料界面
                                finish();
                            }

                            @Override
                            protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                            }
                        });
                break;
            default:
                break;
        }

    }

    //获取验证码
    public void getCode(View view) {
        String phoneNumber = et_phone_number.getText().toString();
        RetrofitFactory
                .getInstance()
                .getAPI()
                .getCode(phoneNumber)
                .compose(this.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
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
                                        bt_get.setText("剩余"+aLong+"秒");
                                        if (aLong==0){
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
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

    }

    @Override
    public void onClick(View v) {
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

}
