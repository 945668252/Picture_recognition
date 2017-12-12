package com.sendi.picture_recognition.controller.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.controller.adapter.TagAdapter;
import com.sendi.picture_recognition.utils.httputils.displayutils.DisplayMetricsUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/5/25.
 */

public class TestActivity extends Activity {

    private TextView mTextView01;
    private TextView mTextView02;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        initView();
    }

    private void initView() {
        mTextView01= (TextView) findViewById(R.id.text01);
        mTextView02= (TextView) findViewById(R.id.text02);
    }

    public void onToast(View view) {
        mTextView01.setVisibility(View.VISIBLE);

        Animation animation1=AnimationUtils.loadAnimation(this,R.anim.text_anim);
        final Animation animation2=AnimationUtils.loadAnimation(this,R.anim.text_anim);
        mTextView01.startAnimation(animation1);

        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTextView02.setVisibility(View.VISIBLE);

                mTextView02.startAnimation(animation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


}

