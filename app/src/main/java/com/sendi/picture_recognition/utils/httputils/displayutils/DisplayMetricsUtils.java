package com.sendi.picture_recognition.utils.httputils.displayutils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Administrator on 2017/5/19.
 */

public class DisplayMetricsUtils {

    public static int getWidth(Context context,float scale){
        DisplayMetrics dm=context.getResources().getDisplayMetrics();
        return (int) (dm.widthPixels*scale);
    }

    public static int getHeight(Context context,float scale){
        DisplayMetrics dm=context.getResources().getDisplayMetrics();
        return (int) (dm.heightPixels*scale);
    }
}
