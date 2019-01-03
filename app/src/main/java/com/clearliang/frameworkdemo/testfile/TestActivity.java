package com.clearliang.frameworkdemo.testfile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.utils.MoveViewUtil;
import com.clearliang.frameworkdemo.view.base.BaseActivity;
import com.clearliang.frameworkdemo.view.widget.EditCompleListener;
import com.clearliang.frameworkdemo.view.widget.PayEdit;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;


public class TestActivity extends BaseActivity<TestPresenter> implements TestPresenter.DataInterface {
    private Button btn;
    private Button btnLeft;
    private Button btnRight;
    private PayEdit payEt;

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
                payEt.getCurrentEdit().setFocusable(true);
                payEt.getCurrentEdit().setFocusableInTouchMode(true);
                payEt.getCurrentEdit().requestFocus();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(payEt.getCurrentEdit(),0);
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
        payEt = (PayEdit) findViewById(R.id.pay_et);

        payEt.setEditCompleListener(new EditCompleListener() {
            @Override
            public void onNumCompleted(String num) {
                LogUtils.e(num);
            }
        });

        payEt.getCurrentEdit().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                Editable editable = payEt.getCurrentEdit().getText();
                int start = payEt.getCurrentEdit().getSelectionStart();
                if ((keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_UP)) {
                    editable.insert(start, Character.toString((char) keyCode));
                    return true;
                }
                return false;
            }
        });

       /* *//*系统键盘删除键的监听,自定义键盘无用*//*
        View.OnKeyListener onKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                Editable editable = payEt.getCurrentEdit().getText();
                int start = payEt.getCurrentEdit().getSelectionStart();
                if ((keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_UP)) {
                    editable.insert(start, Character.toString((char) keyCode));
                    return true;
                }
                return false;
            }
        };*/
    }


}
