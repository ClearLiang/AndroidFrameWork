package com.clearliang.frameworkdemo.view.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.model.greendao.DaoMaster;
import com.clearliang.frameworkdemo.model.greendao.DaoSession;
import com.previewlibrary.ZoomMediaLoader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;


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

        ZoomMediaLoader.getInstance().init(new TestImageLoader());//初始化图片视频查看

        //是否开启debug模式，true表示打开debug模式，false表示关闭调试模式
        //Bugly.init(getApplicationContext(), getResources().getString(R.string.bugly_app_id), false);

        setDatabase();

        instance = this;

        //initUpgradeDialog();
    }

    // bugly更新设置
    private void initUpgradeDialog() {

        //自定义初始化开关
        Beta.autoInit = true;

        //true表示初始化时自动检查升级; false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
        Beta.autoCheckUpgrade = true;

        //设置升级检查周期为60s(默认检查周期为0s)，60s内SDK不重复向后台请求策略);
        //Beta.upgradeCheckPeriod = 60 * 1000;

        //设置启动延时为1s（默认延时3s），APP启动1s后初始化SDK，避免影响APP启动速度;
        Beta.initDelay = 1 * 1000;

        //设置通知栏大图标，largeIconId为项目中的图片资源;
        Beta.largeIconId = R.mipmap.icon_head_1;

        //设置状态栏小图标，smallIconId为项目中的图片资源Id;
        Beta.smallIconId = R.mipmap.icon_tabbar_lab;

        //设置更新弹窗默认展示的banner，defaultBannerId为项目中的图片资源Id;
        //当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading“;
        //Beta.defaultBannerId = R.mipmap.icon_tabbar_util;

        //设置sd卡的Download为更新资源保存目录;
        //后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        //已经确认过的弹窗在APP下次启动自动检查更新时会再次显示;
        Beta.showInterruptedStrategy = true;

        //只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗; 不设置会默认所有activity都可以显示弹窗;
        //Beta.canShowUpgradeActs.add(TestActivity.class);

        //设置Wifi下自动下载
        //Beta.autoDownloadOnWifi = true;

        /*在application中初始化时设置监听，监听策略的收取*/
        /*Beta.upgradeListener = new UpgradeListener() {

            @Override
            public void onUpgrade(int ret, UpgradeInfo strategy, boolean isManual, boolean isSilence) {
                if (strategy != null) {
                    LogUtils.e("bugly", "需要更新,存在更新策略");
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Intent i = new Intent();
                            i.setClass(getApplicationContext(), UpgradeActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    }, 3000);

                } else {
                    LogUtils.e("bugly", "不需要更新,没有更新策略");
                }
            }
        };*/

        /* 设置更新状态回调接口 */
        Beta.upgradeStateListener = new UpgradeStateListener() {
            @Override
            public void onUpgradeSuccess(boolean isManual) {
                Toast.makeText(getApplicationContext(), "UPGRADE_SUCCESS", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpgradeFailed(boolean isManual) {
                Toast.makeText(getApplicationContext(), "UPGRADE_FAILED", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpgrading(boolean isManual) {
                Toast.makeText(getApplicationContext(), "UPGRADE_CHECKING", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadCompleted(boolean b) {
                Toast.makeText(getApplicationContext(), "UPGRADE_COMPLETED", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpgradeNoVersion(boolean isManual) {
                Toast.makeText(getApplicationContext(), "UPGRADE_NO_VERSION", Toast.LENGTH_SHORT).show();
            }
        };

        Bugly.init(getApplicationContext(), getResources().getString(R.string.bugly_app_id), false);
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
