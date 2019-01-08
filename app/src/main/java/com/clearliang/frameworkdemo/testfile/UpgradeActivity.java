package com.clearliang.frameworkdemo.testfile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.utils.TranUtil;
import com.clearliang.frameworkdemo.view.widget.LineTextView;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.download.DownloadListener;
import com.tencent.bugly.beta.download.DownloadTask;

/**
 * Created by ClearLiang on 2019/1/4
 * <p>
 * Function :
 */
@SuppressLint("SetTextI18n")
public class UpgradeActivity extends Activity {
    private TextView tv, title, version, size, time, content;
    private Button cancel, start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_upgrade);

        initView();
        initUpdate();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /*注销下载监听*/
        Beta.unregisterDownloadListener();
    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
        title = (TextView) findViewById(R.id.title);
        version = (TextView) findViewById(R.id.version);
        size = (TextView) findViewById(R.id.size);
        time = (TextView) findViewById(R.id.time);
        content = (TextView) findViewById(R.id.content);
        cancel = (Button) findViewById(R.id.cancel);
        start = (Button) findViewById(R.id.start);

        LineTextView tv1 = (LineTextView) findViewById(R.id.tv1);
        LineTextView title1 = (LineTextView) findViewById(R.id.title1);
        LineTextView version1 = (LineTextView) findViewById(R.id.version1);
        LineTextView size1 = (LineTextView) findViewById(R.id.size1);
        LineTextView time1 = (LineTextView) findViewById(R.id.time1);
        LineTextView content1 = (LineTextView) findViewById(R.id.content1);

        tv1.setText("当前下载:");
        title1.setText("版本名:");
        version1.setText("版本号:");
        size1.setText("文件大小:");
        time1.setText("更新时间:");
        content1.setText("更新内容:");
    }

    private void initUpdate() {
        /*获取下载任务，初始化界面信息*/
        updateBtn(Beta.getStrategyTask());

        tv.setText(TranUtil.toMbString(Beta.getStrategyTask().getSavedLength()) + "MB");

        /*获取策略信息，初始化界面信息*/
        title.setText(Beta.getUpgradeInfo().title);
        version.setText(Beta.getUpgradeInfo().versionName);
        size.setText(TranUtil.toMbString(Beta.getUpgradeInfo().fileSize) + "MB");
        time.setText(TranUtil.translateToNowTime(Beta.getUpgradeInfo().publishTime));
        content.setText(Beta.getUpgradeInfo().newFeature);

    }

    private void initEvent() {

        /*为下载按钮设置监听*/
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadTask task = Beta.startDownload();
                updateBtn(task);
                if (task.getStatus() == DownloadTask.DOWNLOADING) {
                    //finish();
                }
            }
        });

        /*为取消按钮设置监听*/
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Beta.cancelDownload();
                finish();
            }
        });

        /*注册下载监听，监听下载事件*/
        Beta.registerDownloadListener(new DownloadListener() {
            @Override
            public void onReceive(DownloadTask task) {
                updateBtn(task);
                tv.setText(TranUtil.toMbString(task.getSavedLength()) + "MB");
            }

            @Override
            public void onCompleted(DownloadTask task) {
                updateBtn(task);
                tv.setText(TranUtil.toMbString(task.getSavedLength()) + "MB");
            }

            @Override
            public void onFailed(DownloadTask task, int code, String extMsg) {
                updateBtn(task);
                tv.setText("failed");
            }
        });
    }

    private void updateBtn(DownloadTask task) {

        /*根据下载任务状态设置按钮*/
        switch (task.getStatus()) {
            case DownloadTask.INIT:
            case DownloadTask.DELETED:
            case DownloadTask.FAILED: {
                start.setText("开始下载");
            }
            break;
            case DownloadTask.COMPLETE: {
                start.setText("安装");
            }
            break;
            case DownloadTask.DOWNLOADING: {
                start.setText("暂停");
            }
            break;
            case DownloadTask.PAUSED: {
                start.setText("继续下载");
            }
            break;
        }
    }



}
