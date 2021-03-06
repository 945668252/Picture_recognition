package com.sendi.picture_recognition.utils;


import android.app.IntentService;

import com.sendi.picture_recognition.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/6/26.
 * Activity控制器
 */

public class ActivityController {
    public static List<BaseActivity>sActivityList=new ArrayList<>();
    public static void addActivity(BaseActivity activity){
        sActivityList.add(activity);
    }

    public static void finishAll(){
        for (BaseActivity activity:sActivityList) {
            activity.finish();
        }
    }

    public static void remove(BaseActivity activity){
        sActivityList.remove(activity);
    }

}
