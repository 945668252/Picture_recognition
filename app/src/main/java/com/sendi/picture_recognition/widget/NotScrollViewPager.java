package com.sendi.picture_recognition.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/6/21.
 * 不能滑动
 */

public class NotScrollViewPager extends ViewPager {
    private int startX;
    private int startY;

    public NotScrollViewPager(Context context) {
        super(context);
    }

    public NotScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX= (int) ev.getRawX();
                startY= (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int x= (int) ev.getRawX();
                int y= (int) ev.getRawY();
                int dx=Math.abs(x-startX);
                int dy=Math.abs(y-startY);
                if (dx>dy){
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startX=0;
                startY=0;
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        return super.onTouchEvent(ev);
    }
}
