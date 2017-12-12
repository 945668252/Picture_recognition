package com.sendi.picture_recognition.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/5/14.
 */

public class ContentLinearLayout extends LinearLayout {
    public DragLayout mDragLayout;

    public ContentLinearLayout(Context context) {
        super(context);
    }

    public ContentLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDragLayout(DragLayout mDragLayout){
        this.mDragLayout=mDragLayout;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mDragLayout.getStatus()==DragLayout.Status.CLOSE){
            return super.onInterceptTouchEvent(ev);
        }else {
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDragLayout.getStatus()== DragLayout.Status.CLOSE){
            return super.onTouchEvent(event);
        }else {
            if (event.getAction()==MotionEvent.ACTION_UP){
                mDragLayout.close();
            }
        }
        return true;
    }
}
