package com.sendi.picture_recognition.model.act;

import android.content.Context;
import android.content.Intent;

import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.controller.ActivityController;
import com.sendi.picture_recognition.controller.activity.LoginActivity;
import com.sendi.picture_recognition.controller.activity.SettingActivity;
import com.sendi.picture_recognition.model.abstract_act.AbsSettingModel;
import com.sendi.picture_recognition.utils.httputils.sputils.SPUtils;
import com.sendi.picture_recognition.utils.httputils.sqliteutils.SqliteDBUtils;

/**
 * Created by Administrator on 2017/12/8.
 */

public class SettingModel extends AbsSettingModel {
    @Override
    public void initLgType(Context context) {
        GlobalConfig.LANGUAGETYPE = SPUtils.getLanguageType(context);
    }

    @Override
    public String exit(Context context) {
        //从数据库伤处该用户信息
        String userName = SqliteDBUtils
                .getInstance(context)
                .getUserDao()
                .queryBuilder()
                .build()
                .listLazy()
                .get(0)
                .getUser_id();
        SqliteDBUtils
                .getInstance(context)
                .getUserDao()
                .deleteAll();
        //删除历史记录
        SqliteDBUtils
                .getInstance(context)
                .getRecordDataDao()
                .deleteAll();
        //完全退出
        ActivityController.finishAll();
        //将该用户的账号传到登录界面
        return userName;
    }
}
