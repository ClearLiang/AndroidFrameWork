package com.clearliang.frameworkdemo.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.view.activity.MainActivity;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;
import static com.clearliang.frameworkdemo.view.base.GlobalConstants.DOWN_ERROR;
import static com.clearliang.frameworkdemo.view.base.GlobalConstants.GET_UNDATAINFO_ERROR;
import static com.clearliang.frameworkdemo.view.base.GlobalConstants.UPDATA_CLIENT;

/**
 * Created by ClearLiang on 2019/1/4
 * <p>
 * Function :软件更新工具
 */
public class UpdateUtil {
    private QMUIDialog dialog;
    private Context context;
    private String newAppVersion;

    private static UpdateUtil updateUtil;

    private UpdateUtil() {
    }

    public static UpdateUtil getUpdateUtil() {
        if(updateUtil == null){
            synchronized (UpdateUtil.class){
                if(updateUtil == null){
                    updateUtil = new UpdateUtil();
                }
            }
        }
        return updateUtil;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATA_CLIENT:
                    //对话框通知用户升级程序
                    downLoadApk(String.valueOf(msg.obj));
                    break;
                case GET_UNDATAINFO_ERROR:
                    //服务器超时
                    ToastUtils.showShort("获取服务器更新信息失败");
                    LoginMain();
                    break;
                case DOWN_ERROR:
                    //下载apk失败
                    ToastUtils.showShort("下载新版本失败");
                    LoginMain();
                    break;
            }
        }
    };

    /*
     * 从服务器获取xml解析并进行比对版本号
     */
    public void checkVersion(final Context context, String newAppVersion, final String path){
        this.context = context;
        this.newAppVersion = newAppVersion;
        String oldVersion = AppUtils.getAppVersionName();
        LogUtils.e("当前版本",oldVersion);
        LogUtils.e("新版本",newAppVersion);
        if(newAppVersion.equals(AppUtils.getAppVersionName())){
            dialog = new QMUIDialog.MessageDialogBuilder(context)
                    .setTitle("提示")
                    .setMessage("已是最新版本，无需更新!")
                    .addAction("确定", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            dialog.show();
            dialog.setCanceledOnTouchOutside(true);

        }else {
            new QMUIDialog.MessageDialogBuilder(context)
                    .setTitle("提示")
                    .setMessage("检测到新版本，点击更新!")
                    .addAction("取消", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            dialog.dismiss();
                        }
                    })
                    .addAction("确定", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            Message msg = new Message();
                            msg.what = UPDATA_CLIENT;
                            msg.obj = path;
                            handler.sendMessage(msg);
                        }
                    })
                    .create().show();
            //dialog.show();
            //dialog.setCanceledOnTouchOutside(true);
        }

    }

    /*
     * 从服务器中下载APK
     */
    private void downLoadApk(final String path) {
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(path,pd);
                    sleep(2000);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private File getFileFromServer(String path, ProgressDialog pd) throws Exception{
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            URL url = new URL(path);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len ;
            int total=0;
            while((len =bis.read(buffer))!=-1){
                fos.write(buffer, 0, len);
                total+= len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        }
        else{
            return null;
        }
    }

    //安装apk
    private void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /*
     * 进入程序的主界面
     */
    private void LoginMain() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

}
