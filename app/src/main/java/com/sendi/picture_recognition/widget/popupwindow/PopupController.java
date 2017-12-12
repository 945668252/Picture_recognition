package com.sendi.picture_recognition.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2017/6/16.
 */

public class PopupController {
    private int layoutResId;//布局Id
    private Context mContext;
    private PopupWindow mPopupWindow;
    public View mPopupView;//弹窗布局的View
    private View mView;
    private Window mWindow;

    PopupController(Context context ,PopupWindow popupWindow){
        this.mContext=context;
        this.mPopupWindow=popupWindow;
    }

    public void setView(int layoutResId){
        mView=null;
        this.layoutResId=layoutResId;
        installContent();
    }
    private void setView(View view){
        mView=view;
        this.layoutResId=0;
        installContent();
    }

    private void installContent() {
        if (layoutResId!=0){
            mPopupView= LayoutInflater.from(mContext).inflate(layoutResId,null);
        }else if (mView!=null){
            mPopupView=mView;
        }
        mPopupWindow.setContentView(mPopupView);
    }

    /**
     * 设置宽高
     * @param width
     * @param height
     */
    public void setWidthAndHeight(int width,int height){
        if (width==0||height==0){
            //如果没有宽高，则使用默认值
            mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        }else {
            mPopupWindow.setWidth(width);
            mPopupWindow.setHeight(height);
        }
    }

    /**
     * 设置背景灰色程度
     * @param level
     */
    public void setBackgroundLevel(float level){
        mWindow=((Activity)mContext).getWindow();
        WindowManager.LayoutParams params=mWindow.getAttributes();
        params.alpha=level;
        mWindow.setAttributes(params);
    }

    /**
     * 设置动画
     * @param animationStyle
     */
    private void setAnimationStyle(int animationStyle){
        mPopupWindow.setAnimationStyle(animationStyle);
    }
    private void setOutsideTouchable(boolean touchable){
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setOutsideTouchable(touchable);//设置其他区域是否可以点击
        mPopupWindow.setFocusable(touchable);
    }

    static class PopupParams{
        public int layoutResId;
        public Context pContext;
        public int mWidth,mHeight;//宽高
        public boolean isShowBg,isShowAnim;
        public float bg_level;//屏幕背景灰色程度
        public int animationStyle;//动画ID
        public View pView;
        public boolean isTouchable=true;

        public PopupParams(Context context){
            this.pContext=context;
        }

        public void  apply(PopupController controller){
            if (pView!=null){
                controller.setView(pView);
            }else if (layoutResId!=0){
                controller.setView(layoutResId);
            }else {
                throw new IllegalArgumentException("PopupView's contentView is null");
            }
            controller.setWidthAndHeight(mWidth,mHeight);
            controller.setOutsideTouchable(isTouchable);//设置可以点击
            if (isShowBg){
                //设置背景
                controller.setBackgroundLevel(bg_level);
            }
            if (isShowAnim){
                controller.setAnimationStyle(animationStyle);
            }
        }
    }
}
