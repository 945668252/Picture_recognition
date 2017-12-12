package com.sendi.picture_recognition.utils.httputils.sputils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/5/23.
 */

public class SPUtils {

    public static void setLanguageType(Context context,String type){
        SharedPreferences sp=context.getSharedPreferences("setting_sp",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("type",type).commit();
    }

    public static String getLanguageType(Context context){
        SharedPreferences sp=context.getSharedPreferences("setting_sp",Context.MODE_PRIVATE);
        String type=sp.getString("type","0");
        return type;
    }
}
