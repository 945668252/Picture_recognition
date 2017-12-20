package com.sendi.picture_recognition.controller.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.controller.adapter.TagAdapter;
import com.sendi.picture_recognition.utils.httputils.displayutils.DisplayMetricsUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Administrator on 2017/5/25.
 */

public class TestActivity extends Activity {

    private TextView mTvResult;
    private double firstNum = 0;
    private char currentSign = '+';
    private StringBuffer currentNum = new StringBuffer();
    private boolean isPoint = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        initView();
    }

    private void initView() {
        mTvResult = (TextView) findViewById(R.id.tv_result);
    }

    public void init() {
        currentNum.delete(0, currentNum.length());
        isPoint = false;
    }

    public double stringToDouble() {
        if (currentNum.length() == 0) {
            return 0;
        }
        double result = Double.parseDouble(currentNum.toString());

        return result;
    }

    public double calcu() {
        double result = 0;
        switch (currentSign) {
            case '+':
                result = firstNum + stringToDouble();
                break;
            case '-':
                result = firstNum - stringToDouble();
                break;
            case '*':
                result = firstNum * stringToDouble();
                break;
            case '/':
                result = firstNum / stringToDouble();
                break;
        }
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(2);
        result = Double.parseDouble(format.format(result));
        return result;
    }

    public void display(){
        mTvResult.setText(currentNum.toString());
    }

    /**
     * 按数字
     *
     * @param view
     */
    public void onDigitalClick(View view) {
        Button button= (Button) view;

        char text=button.getText().charAt(0);

        currentNum.append(text);

        display();
    }

    /**
     * 加号
     *
     * @param view
     */
    public void onAdd(View view) {
        double result=calcu();
        mTvResult.setText(String.valueOf(result));
        firstNum=result;
        currentSign='+';
        init();
    }

    /**
     * 减号
     *
     * @param view
     */
    public void onSub(View view) {
        double result=calcu();
        mTvResult.setText(String.valueOf(result));
        firstNum=result;
        currentSign='-';
        init();
    }

    /**
     * 乘号
     *
     * @param view
     */
    public void onMul(View view) {
        double result=calcu();
        mTvResult.setText(String.valueOf(result));
        firstNum=result;
        currentSign='*';
        init();
    }

    /**
     * 除号
     *
     * @param view
     */
    public void onDiv(View view) {
        double result=calcu();
        mTvResult.setText(String.valueOf(result));
        firstNum=result;
        currentSign='/';
        init();
    }

    /**
     * 小数点
     *
     * @param view
     */
    public void onPointClick(View view) {
        if (isPoint)
            return;
        if (currentNum.length()==0)
            return;

        Button button= (Button) view;
        char text=button.getText().charAt(0);
        currentNum.append(text);
        isPoint=true;
        display();
    }

    /**
     * back
     * @param view
     */
    public void onDel(View view) {
        firstNum=0;
        if (currentNum.length()>=1){
            currentNum.delete(0,currentNum.length());
        }
//            currentNum.delete(currentNum.length()-1,currentNum.length());
        if (currentNum.length()==0){
            init();
            display();
        }
        Log.i("TAG", "onDel: "+currentNum.length());
        mTvResult.setText(currentNum);
    }

    /**
     * =
     * @param view
     */
    public void onEqu(View view) {
        double result=calcu();
        mTvResult.setText(String.valueOf(result));
        firstNum=result;
        currentSign='+';
        init();
    }

//    private void initView() {
//        mTextView01= (TextView) findViewById(R.id.text01);
//        mTextView02= (TextView) findViewById(R.id.text02);
//    }
//
//    public void onToast(View view) {
//        mTextView01.setVisibility(View.VISIBLE);
//
//        Animation animation1=AnimationUtils.loadAnimation(this,R.anim.text_anim);
//        final Animation animation2=AnimationUtils.loadAnimation(this,R.anim.text_anim);
//        mTextView01.startAnimation(animation1);
//
//        animation1.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                mTextView02.setVisibility(View.VISIBLE);
//
//                mTextView02.startAnimation(animation2);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//
//    }


}

