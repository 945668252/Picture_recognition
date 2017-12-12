package com.sendi.picture_recognition.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/5/14.
 */

public class SearchViewPager extends ViewPager {
    public SearchViewPager(Context context) {
        super(context);
    }

    public SearchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentItem()!=0){
            //请求父控件不拦截
            getParent().requestDisallowInterceptTouchEvent(true);
        }else {
            //请求父控件拦截
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        return super.dispatchTouchEvent(ev);
    }
}
