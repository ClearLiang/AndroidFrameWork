package com.clearliang.frameworkdemo.view.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.clearliang.frameworkdemo.model.greendao.DaoMaster;
import com.clearliang.frameworkdemo.model.greendao.DaoSession;
import com.mob.MobSDK;
import com.previewlibrary.ZoomMediaLoader;


/**
 * @作者 ClearLiang
 * @日期 2018/4/13
 * @描述 APP全局
 * 这里可以加上对于Android 设备特有硬件的操作，如扫码、拍照、录像、数据库创建
 **/

public class BaseApplication extends Application {
    private static SQLiteDatabase db;
    private static DaoSession mDaoSession;
    private static BaseApplication instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);//突破Android64K方法数
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);//初始化BlankJ的工具类
        LogUtils.getConfig()
                .setLogSwitch(true)//log开关
                .setGlobalTag(getClass().getSimpleName());//全局tag

        MobSDK.init(this);

        ZoomMediaLoader.getInstance().init(new TestImageLoader());//初始化图片视频查看

        setDatabase();

        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    private void setDatabase() {
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "frameWork-db", null);
        db = mHelper.getWritableDatabase();
        DaoMaster mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static SQLiteDatabase getDb() {
        return db;
    }


}
