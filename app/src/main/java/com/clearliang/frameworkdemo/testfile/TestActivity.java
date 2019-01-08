package com.clearliang.frameworkdemo.testfile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.widget.Button;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.service.MyAccessibilityService;
import com.clearliang.frameworkdemo.utils.MoveViewUtil;
import com.clearliang.frameworkdemo.utils.UpdateUtil;
import com.clearliang.frameworkdemo.view.base.BaseActivity;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import rx.functions.Action1;


public class TestActivity extends BaseActivity<TestPresenter> implements TestPresenter.DataInterface {
    private Button btn;
    private Button btnLeft;
    private Button btnRight;

    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initData() {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initEvent() {
        MoveViewUtil.getInstance()
                .initMove(this, btn)
                .setOnTouchListener(new MoveViewUtil.OnTouchListener() {
                    @Override
                    public void onTouchDown(MotionEvent motionEvent, int startX, int startY) {
                        btn.setBackgroundColor(getResources().getColor(R.color.app_color_description));
                    }

                    @Override
                    public void onTouchMove(MotionEvent motionEvent) {
                        if (btn.getLeft() < 200) {
                            btn.setBackgroundColor(getResources().getColor(R.color.app_color_theme_1));
                        } else if (btn.getRight() > ScreenUtils.getScreenWidth() - 200) {
                            btn.setBackgroundColor(getResources().getColor(R.color.app_color_theme_1));
                        } else if (btn.getTop() < 200) {
                            btn.setBackgroundColor(getResources().getColor(R.color.app_color_theme_1));
                        } else if (btn.getBottom() > ScreenUtils.getScreenHeight() - 200) {
                            btn.setBackgroundColor(getResources().getColor(R.color.app_color_theme_1));
                        } else {
                            btn.setBackgroundColor(getResources().getColor(R.color.tab_panel_bg));
                        }
                    }

                    @Override
                    public void onTouchUp(MotionEvent motionEvent) {

                    }
                });

        setClick(btnLeft, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                UpdateUtil.getUpdateUtil().
                        checkVersion(TestActivity.this, "1.2", "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk");
            }
        });

        setClick(btnRight, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (!OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(TestActivity.this,
                        MyAccessibilityService.class.getName())){// 判断服务是否开启
                    OpenAccessibilitySettingHelper.jumpToSettingPage(TestActivity.this);// 跳转到开启页面
                }else {
                    ToastUtils.showShort("服务已开启");
                }
            }
        });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

    }


    private void initView() {
        btn = (Button) findViewById(R.id.btn);
        btnLeft = (Button) findViewById(R.id.btn_left);
        btnRight = (Button) findViewById(R.id.btn_right);

    }


}
