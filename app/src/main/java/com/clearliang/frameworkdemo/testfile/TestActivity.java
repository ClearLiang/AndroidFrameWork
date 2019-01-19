package com.clearliang.frameworkdemo.testfile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.clearliang.frameworkdemo.LoginActivity;
import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.model.bean.PictureBean;
import com.clearliang.frameworkdemo.utils.MoveViewUtil;
import com.clearliang.frameworkdemo.view.base.BaseActivity;
import com.clearliang.frameworkdemo.view.widget.Countdown;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;


public class TestActivity extends BaseActivity<TestPresenter> implements TestPresenter.DataInterface {
    private Button btn;
    private Button btnLeft;
    private Button btnRight;
    private List<PictureBean> mList;
    private Countdown countdown;
    private Countdown countdown2;
    private Button btnLeft1;
    private Button btnRight1;

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
        mList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PictureBean pictureBean = new PictureBean();
            pictureBean.setPath("我是" + i);
            mList.add(pictureBean);
        }
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
                countdown.startTimer(new Countdown.OnStateListener() {
                    @Override
                    public void onTimeListener(int remaining) {
                        LogUtils.e("剩余时间：", remaining);
                    }
                });
                //LogUtils.e(JSON.toJSONString(mList));
                //UpdateUtil.getUpdateUtil().checkVersion(TestActivity.this, "1.2", "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk");
            }
        });

        setClick(btnRight, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                countdown.setDelayTime(269);
            }
        });

        countdown.getRelativeLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.e("点击了RelativeLayout");
            }
        });

        countdown2.getTimer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.e("点击了TextView");
            }
        });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_PICTURE_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String listString = bundle.getString("data");
                    List<PictureBean> picList = JSON.parseArray(listString, PictureBean.class);
                    LogUtils.e(JSON.toJSONString(picList));
                }
                break;
        }
    }

    private void initView() {
        btn = (Button) findViewById(R.id.btn);
        btnLeft = (Button) findViewById(R.id.btn_left);
        btnRight = (Button) findViewById(R.id.btn_right);

        countdown = (Countdown) findViewById(R.id.countdown);
        countdown.setBorderWidth(2);
        countdown.setBorderColor(getResources().getColor(R.color.app_color_theme_5));
        countdown.setBgColor(getResources().getColor(R.color.app_color_theme_2));

        countdown.setTextSize(18);
        countdown.setTextColor(getResources().getColor(R.color.qmui_config_color_black));

        countdown2 = (Countdown) findViewById(R.id.countdown2);
        countdown2.setBorderWidth(2);
        countdown2.setBorderColor(getResources().getColor(R.color.app_color_theme_2));
        countdown2.setBgColor(getResources().getColor(R.color.app_color_theme_4));
        countdown2.setDelayTime(60);
        countdown2.setTextSize(18);
        countdown2.setTextColor(getResources().getColor(R.color.qmui_config_color_black));
        btnLeft1 = (Button) findViewById(R.id.btn_left1);
        btnRight1 = (Button) findViewById(R.id.btn_right1);
    }


}
