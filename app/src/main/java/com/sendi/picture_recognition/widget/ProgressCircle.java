package com.sendi.picture_recognition.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.FloatEvaluator;
import com.nineoldandroids.animation.IntEvaluator;
import com.sendi.picture_recognition.R;

/**
 * Created by Administrator on 2017/5/28.
 */

public class ProgressCircle extends View implements ValueAnimator.AnimatorUpdateListener{
    private final String TAG="ASENDI";
    private Paint mPaint;
    private Paint mShadowPaint;
    private int mProgressBorderWidth=20;  //进度条的大小
    private int mProgressShadowWidth=3;
    private int mProgressBorderColor; //进度颜色
    private int mProgressShadowColor; //背景颜色
    private float mProgressCurrent;// 当前进度
    private float mProgressTotal;//最大进度

    private final float mStartProgress=-90f;  //从最顶端开始
    private final int mTotalProgressAngle=360;     //一整个的圆弧
    private float mDeltaProgressAngle=0f;//当前进度角度
    private float mFinalDeltaProgressAngle; //记录当前进度的角度比例
    private IntEvaluator mEvaluator;//估值器

    public ProgressCircle(Context context) {
        this(context,null);
    }

    public ProgressCircle(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typeArray=context.obtainStyledAttributes(attrs,
                R.styleable.ProgressCircle,defStyleAttr,0);
        mProgressBorderWidth=typeArray.getDimensionPixelSize(R.styleable.ProgressCircle_progress_border_width
                                                                ,10);
        mProgressBorderColor=typeArray.getColor(R.styleable.ProgressCircle_progress_border_color,
                getResources().getColor(R.color.colorWhite));
        mProgressShadowColor=typeArray.getColor(R.styleable.ProgressCircle_progress_shadow_color,
                getResources().getColor(R.color.colorWhite));
        mProgressCurrent=typeArray.getFloat(R.styleable.ProgressCircle_progress_current,0f);
        mProgressTotal=typeArray.getFloat(R.styleable.ProgressCircle_progress_total,360f);
    }

    private float getCurrentDelta(float progressCurrent , float progressTotal) {
        float percent=progressCurrent/progressTotal;//获得比例
        float currentDelta=percent*mTotalProgressAngle;//获得相对应的角度
        Log.i(TAG, "getCurrentDelta: "+currentDelta);
        return currentDelta;
    }


    public void startAni(){
        ValueAnimator valueAnimator=ValueAnimator.ofInt(1,(int) mProgressTotal);
        valueAnimator.addUpdateListener(this);
        mEvaluator=new IntEvaluator();
        mProgressCurrent=getProgressCurrent();
        mProgressTotal=getProgressTotal();
        mFinalDeltaProgressAngle=getCurrentDelta(mProgressCurrent,mProgressTotal);
        valueAnimator.setDuration(4000).start();
    }

    public int getProgressBorderWidth() {
        return mProgressBorderWidth;
    }

    public void setProgressBorderWidth(int progressBorderWidth) {
        mProgressBorderWidth = progressBorderWidth;
    }

    public int getProgressBorderColor() {
        return mProgressBorderColor;
    }

    public void setProgressBorderColor(int progressBorderColor) {
        mProgressBorderColor = progressBorderColor;
    }

    public int getProgressShadowColor() {
        return mProgressShadowColor;
    }

    /**
     * 设置整个圆弧颜色
     * @param progressShadowColor
     */
    public void setProgressShadowColor(int progressShadowColor) {
        mProgressShadowColor = progressShadowColor;
    }

    /**
     * 圆弧的进度
     * @return
     */
    public float getProgressCurrent() {
        return mProgressCurrent;
    }

    public void setProgressCurrent(float progressCurrent) {
        mProgressCurrent = progressCurrent;
    }

    /**
     * 整个圆弧的进度
     * @return
     */
    public float getProgressTotal() {
        return mProgressTotal;
    }

    public void setProgressTotal(float progressTotal) {
        mProgressTotal = progressTotal;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpaceSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSpaceSize=MeasureSpec.getSize(heightMeasureSpec);

        int sideLength=Math.min(widthSpaceSize,heightSpaceSize);

        setMeasuredDimension(sideLength,sideLength);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initPaint();
        RectF mBigOval=new RectF(mProgressBorderWidth/2
                ,mProgressBorderWidth/2
                ,getWidth()-(mProgressBorderWidth-1)/2
                ,getHeight()-(mProgressBorderWidth-1)/2);
        canvas.drawArc(mBigOval,mStartProgress,mTotalProgressAngle,false,mShadowPaint);
        canvas.drawArc(mBigOval,mStartProgress,mDeltaProgressAngle,false,mPaint);
        Log.i("TAG", "onDraw: ");
        super.onDraw(canvas);
    }

    private void initPaint() {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mProgressBorderColor);//设置颜色
        mPaint.setStrokeWidth(mProgressBorderWidth);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置圆角

        mShadowPaint=new Paint();
        mShadowPaint.setAntiAlias(true);//消除锯齿
        mShadowPaint.setStyle(Paint.Style.STROKE);
        mShadowPaint.setColor(mProgressShadowColor);//设置颜色
        mShadowPaint.setStrokeWidth(mProgressShadowWidth);
        mShadowPaint.setStrokeJoin(Paint.Join.ROUND);
        mShadowPaint.setStrokeCap(Paint.Cap.ROUND);//设置圆角
    }


    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        //当前进度
        int currentValue= (int) animation.getAnimatedValue();
        Log.i("SENDI", "onAnimationUpdate: currentValue:"+currentValue);

        float fraction=animation.getAnimatedFraction();//获得比例

        Log.i(TAG, "onAnimationUpdate: fraction:"+fraction);
        mDeltaProgressAngle=mEvaluator.evaluate(fraction,0, (int) mFinalDeltaProgressAngle);
        Log.i("TAG", "onAnimationUpdate: mDeltaProgress:"+mDeltaProgressAngle);
        invalidate();
    }
}
