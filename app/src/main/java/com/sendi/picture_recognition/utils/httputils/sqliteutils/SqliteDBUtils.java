package com.sendi.picture_recognition.utils.httputils.sqliteutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;
import com.sendi.userdb.DaoMaster;
import com.sendi.userdb.DaoSession;
import com.sendi.userdb.RecordDataDao;
import com.sendi.userdb.UserDao;

/**
 * Created by Administrator on 2017/5/20.
 */

public class SqliteDBUtils {
    public static SqliteDBUtils mSqliteDbUtils;
    public static DaoSession daoSession;

    private SqliteDBUtils(Context context) {
        SQLiteDatabase db = new DaoMaster.DevOpenHelper(context, "person.db", null)
                .getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession=daoMaster.newSession();
    }

    public static SqliteDBUtils getInstance(Context context){
        if (mSqliteDbUtils==null){
            synchronized (RetrofitFactory.class){
                if (mSqliteDbUtils==null){
                    mSqliteDbUtils=new SqliteDBUtils(context);
                }
            }
        }
        return mSqliteDbUtils;
    }
    //个人信息
    public  UserDao getUserDao() {
        UserDao userDao = daoSession.getUserDao();
        return userDao;
    }
    //历史记录
    public RecordDataDao getRecordDataDao(){
        RecordDataDao recordDataDao=daoSession.getRecordDataDao();
        return recordDataDao;
    }
}
