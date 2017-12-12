package com.sendi.picture_recognition.widget.popupwindow;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2017/6/16.
 * 公共PopupWindow
 */

public class ComonPopupWindow extends PopupWindow {
    final PopupController mController;

    private ComonPopupWindow(Context context) {
        mController = new PopupController(context, this);
    }

    @Override
    public int getWidth() {
        return mController.mPopupView.getMeasuredWidth();
    }

    @Override
    public int getHeight() {
        return mController.mPopupView.getMeasuredHeight();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mController.setBackgroundLevel(1.0f);
    }

    @Override
    public boolean isShowing() {
        return super.isShowing();
    }

    public interface ViewInterface{
        void getChildView(View view,int layoutResId,int position);
    }

    public static class Builder{
       private final PopupController.PopupParams mParams;
        private ViewInterface listener;

        public Builder(Context context){
            mParams=new PopupController.PopupParams(context);
        }
        public Builder setView(int layoutResId){
            mParams.pView=null;
            mParams.layoutResId=layoutResId;
            return this;
        }

        public Builder setView(View view){
            mParams.pView=view;
            mParams.layoutResId=0;
            return this;
        }

        public Builder setViewOnclickListener(ViewInterface listener){
            this.listener=listener;
            return this;
        }

        public Builder setWidthAndHeight(int width,int height){
            mParams.mWidth=width;
            mParams.mHeight=height;
            return this;
        }

        public Builder setBackgroundLevel(float level){
            mParams.isShowBg=true;
            mParams.bg_level=level;
            return this;
        }

        public Builder setOutsideTouchable(boolean touchable){
            mParams.isTouchable=touchable;
            return this;
        }

        public Builder setAnimationStyle(int animationStyle){
            mParams.isShowAnim=true;
            mParams.animationStyle=animationStyle;
            return this;
        }

        public ComonPopupWindow create(int position){
            final ComonPopupWindow popupWindow=new ComonPopupWindow(mParams.pContext);
            mParams.apply(popupWindow.mController);
            if (listener!=null&&mParams.layoutResId!=0){
                listener.getChildView(popupWindow.mController.mPopupView,mParams.layoutResId,position);
            }
            CommonUtil.measureWidthAndHeight(popupWindow.mController.mPopupView);
            return popupWindow;
        }
    }
}
