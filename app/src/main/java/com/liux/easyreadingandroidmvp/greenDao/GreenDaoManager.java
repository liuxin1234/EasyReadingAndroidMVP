package com.liux.easyreadingandroidmvp.greenDao;

import android.database.sqlite.SQLiteDatabase;

import com.liux.easyreadingandroidmvp.application.EasyReadApplication;

/**
 * Created by
 * 项目名称：com.liux.easyreading.greenDao
 * 项目日期：2018/3/30
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class GreenDaoManager {
    private static final String DB_NAME = "easyReading.db";
    private static GreenDaoManager mInstance;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public static GreenDaoManager getInstance(){
        if (mInstance == null){
            synchronized (GreenDaoManager.class){
                if (mInstance == null){
                    mInstance = new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    private GreenDaoManager(){
        if (mInstance == null){
            //创建数据库user.db"
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(EasyReadApplication.getContext(), DB_NAME, null);
            //获取可写数据库
            SQLiteDatabase db = helper.getWritableDatabase();
            //获取数据库对象
            DaoMaster daoMaster = new DaoMaster(db);
            //获取Dao对象管理者
            mDaoSession = daoMaster.newSession();
        }
    }


    public DaoSession getDaoSession(){
        return mDaoSession;
    }

    public DaoMaster getDaoMaster(){
        return mDaoMaster;
    }
}
