package com.clearliang.frameworkdemo.testfile;

import android.accessibilityservice.AccessibilityService;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.model.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ClearLiang on 2019/1/7
 * <p>
 * Function :
 */
public class AccessibilityActivity extends AppCompatActivity {

    private TextView tv1;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private LinearLayout activityAccessibilityMain;
    private CheckBox normalSampleCheckbox;
    private RadioButton normalSampleRadiobutton;
    private ToggleButton normalSampleTogglebutton;
    private Button normalSampleBack;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private AccessibilityOperator accessibilityOperator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);

        initView();
        initEvent();
        accessibilityOperator = AccessibilityOperator.getInstance();

    }

    private void initEvent() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(AccessibilityActivity.this,
                        AccessibilityService.class.getName())) {// 判断服务是否开启
                    OpenAccessibilitySettingHelper.jumpToSettingPage(AccessibilityActivity.this);// 跳转到开启页面
                } else {
                    ToastUtils.showShort("服务已开启");
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pakageName = "com.qmuiteam.qmuidemo";
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
                intent.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
                startActivity(intent);
                ActivityManager activityMgr = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
                activityMgr.killBackgroundProcesses(pakageName);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        // 执行延时任务
        clickText();
    }

    /**
     * 按文本点击
     */
    private void clickText() {
        clickTextItem("微信红包", 1);
        //clickTextItem("微信红包", 2);
        //clickTextItem("微信红包", 3);
        //clickTextItem("微信红包", 4);
    }

    /**
     * 文本单个延时点击
     *
     * @param text 文本内容
     * @param num  延时倍数
     */
    private void clickTextItem(final String text, int num) {
        mHandler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void run() {
                final boolean isSuccess = accessibilityOperator.clickText(text);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        popToast(isSuccess, text);
                    }
                });
            }
        }, 2000 * num);
    }

    /**
     * 弹出吐司
     *
     * @param isSuccess
     * @param msg
     */
    private void popToast(boolean isSuccess, String msg) {
        if (isSuccess) {
            ToastUtils.showShort(msg + "点击成功");
        } else {
            ToastUtils.showShort(msg + "点击失败");
        }
    }

    private void initView() {
        tv1 = (TextView) findViewById(R.id.tv_1);
        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);
        btn4 = (Button) findViewById(R.id.btn_4);
        activityAccessibilityMain = (LinearLayout) findViewById(R.id.activity_accessibility_main);
        normalSampleCheckbox = (CheckBox) findViewById(R.id.normal_sample_checkbox);
        normalSampleRadiobutton = (RadioButton) findViewById(R.id.normal_sample_radiobutton);
        normalSampleTogglebutton = (ToggleButton) findViewById(R.id.normal_sample_togglebutton);
        normalSampleBack = (Button) findViewById(R.id.normal_sample_back);


    }
}
