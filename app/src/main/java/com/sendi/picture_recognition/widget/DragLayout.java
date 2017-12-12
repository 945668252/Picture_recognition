package com.sendi.picture_recognition.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Administrator on 2017/5/14.
 */

public class DragLayout extends FrameLayout {
    private final String TAG="ASENDI";
    private ViewDragHelper mDragHelper;

    private ViewGroup mLeftContent;
    private ViewGroup mMainContent;

    private int mHeight;
    private int mWidth;
    private int mRange;

    private Status mStatus=Status.CLOSE;
    private OnDragStatusChangeListener mListener;

    //状态
    public static enum Status{
        CLOSE,OPEN,DRAGING;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setOnDragStatusChangeListener(OnDragStatusChangeListener listener) {
        mListener = listener;
    }

    public DragLayout(Context context) {
        this(context,null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragHelper=ViewDragHelper.create(this,mCallback);
    }

    ViewDragHelper.Callback mCallback=new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mRange;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            if (child==mMainContent){
                left=leftFix(left);
            }
            return left;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);

            int newLeft=left;
            if (changedView==mLeftContent){
                newLeft=mMainContent.getLeft()+dx;
            }

            newLeft=leftFix(newLeft);
            if (changedView==mLeftContent){
                mLeftContent.layout(0,0,0+mWidth,0+mHeight);
                mMainContent.layout(newLeft,0,newLeft+mWidth,0+mHeight);
            }

            dispatchDragEvent(newLeft);

            invalidate();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (xvel==0&& mMainContent.getLeft()>mRange/2.0f){
                open();
            }else if (xvel>0){
                open();
            }else {
                close();
            }
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }
    };

    public void close() {
        close(true);
    }

    public void open() {
        open(true);
    }

    private void close(boolean isSmooth) {
        int finalLeft=0;
        if (isSmooth){
            if (mDragHelper.smoothSlideViewTo(mMainContent,finalLeft,0)){
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }else {
            mMainContent.layout(finalLeft,0,finalLeft+mWidth,0+mHeight);
        }
    }

    private void open(boolean isSmooth) {
        int finalLeft=mRange;
        if (isSmooth){
            if (mDragHelper.smoothSlideViewTo(mMainContent,finalLeft,0));{
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }else {
            mMainContent.layout(finalLeft,0,finalLeft+mWidth,0+mHeight);
        }
    }

    //平滑滑动
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    protected void dispatchDragEvent(int newLeft) {
        float percent = newLeft * 1.0f / mRange;
        Log.i(TAG, "dispatchDragEvent: " + percent);

        if (mListener!=null){
            mListener.onDraging(percent);
        }

        //更新状态执行回调
        Status mLastStatus=mStatus;
        mStatus=updateStatus(percent);
        if (mStatus!=mLastStatus){
            //状态发生变化
            if (mStatus==Status.CLOSE){
                if (mListener!=null){
                    mListener.onClose();
                }
            }else if (mStatus==Status.OPEN){
                if (mListener!=null){
                    mListener.onOpen();
                }
            }
        }


//        伴随动画：
        animViews(percent);
    }

    private Status updateStatus(float percent) {
        if (percent==0f){
            return Status.CLOSE;
        }else if (percent==1.0f){
            return Status.OPEN;
        }
        return Status.DRAGING;
    }

    private void animViews(float percent){
//        1，左面板：平移动画、缩放动画、透明度动画
        //缩放动画
        ViewHelper.setScaleX(mLeftContent, evaluate(percent,0.8f,1.0f));
        ViewHelper.setScaleY(mLeftContent, 1.0f);
        //平移动画 -mWidth/2.0f ->0.0f
        ViewHelper.setTranslationX(mLeftContent,evaluate(percent,-mWidth/2.0f,0));
        //透明度
        ViewHelper.setAlpha(mLeftContent,evaluate(percent,0.5f,1.0f));

//        2.主面板： 缩放动画

        ViewHelper.setScaleX(mMainContent,evaluate(percent,1.0f,1.0f));
        ViewHelper.setScaleY(mMainContent,evaluate(percent,1.0f,1.0f));
//        3.背景：   亮度变化（颜色变化）
//        getBackground().setColorFilter((Integer) evaluateColor(percent, Color.BLACK,Color.TRANSPARENT), PorterDuff.Mode.SRC_OVER);
    }
//估值器
    public Object evaluateColor(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int)((startA + (int)(fraction * (endA - startA))) << 24) |
                (int)((startR + (int)(fraction * (endR - startR))) << 16) |
                (int)((startG + (int)(fraction * (endG - startG))) << 8) |
                (int)((startB + (int)(fraction * (endB - startB))));
    }

    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }


    private int leftFix(int left) {
        if (left < 0) {
            return 0;
        } else if (left > mRange) {
            return mRange;
        }
        return left;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
            return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
                try {
                    mDragHelper.processTouchEvent(event);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount()<2){
            throw new IllegalStateException("布局至少有两个孩子,Your viewGroup must have two children at least");
        }
        if (!(getChildAt(0) instanceof ViewGroup && getChildAt(1) instanceof ViewGroup)) {
            throw new IllegalArgumentException("子View必须是ViewGroup的子类，Your children must be instance of ViewGroup.");
        }

        mLeftContent= (ViewGroup) getChildAt(0);
        mMainContent= (ViewGroup) getChildAt(1);
    }
//测量尺寸
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mHeight=getMeasuredHeight();
        mWidth=getWidth();

        mRange= (int) (mWidth*0.7f);
    }

    public interface OnDragStatusChangeListener{
        void onClose();
        void onOpen();
        void onDraging(float percent);
    }
}
