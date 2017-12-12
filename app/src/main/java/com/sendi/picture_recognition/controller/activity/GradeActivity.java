package com.sendi.picture_recognition.controller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;
import com.sendi.picture_recognition.widget.ProgressCircle;


public class GradeActivity extends BaseActivity {
    private ProgressCircle mProgressCircle;
    private TextView tv_grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        initView();
        initData();
    }

    private void initData() {
        //先从数据库拉取
        RetrofitFactory
                .getInstance()
                .getAPI()
                .getIntegral(GlobalConfig.USERID)
                .compose(this.<BaseEntity<Float>>setThread())
                .subscribe(new BaseObserver<Float>() {
                    @Override
                    protected void onSuccess(BaseEntity<Float> t) throws Exception {
                        mProgressCircle.setProgressCurrent(t.getData().floatValue());
                        mProgressCircle.startAni();//开始动画
                        tv_grade.setText(t.getData()+"");
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void initView() {
        mProgressCircle= (ProgressCircle) findViewById(R.id.pc);
        tv_grade= (TextView) findViewById(R.id.tv_grade);
        mProgressCircle.setProgressBorderWidth(40);
    }

    public void back(View view) {
        finish();
    }
}
