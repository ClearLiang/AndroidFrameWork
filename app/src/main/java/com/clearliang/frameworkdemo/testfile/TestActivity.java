package com.clearliang.frameworkdemo.testfile;

import android.os.Bundle;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.model.bean.LoginBean;
import com.clearliang.frameworkdemo.view.base.BaseActivity;
import com.clearliang.frameworkdemo.view.widget.Countdown;

import rx.functions.Action1;

/**
 * Created by ClearLiang on 2019/1/28
 * <p>
 * Function :
 */
public class TestActivity extends BaseActivity<TestActivityPresenter.TestActivityInterface, TestActivityPresenter>
        implements TestActivityPresenter.TestActivityInterface {
    private Button btn;
    private Button btnLeft;
    private Button btnRight;
    private Button btnLeft1;
    private Button btnRight1;
    private Countdown countdown;
    private Countdown countdown2;

    @Override
    public void showLoading(String msg) {
        showLoadingDialog(msg);
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    protected TestActivityPresenter createPresenter() {
        return new TestActivityPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        setClick(btn, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mPresenter.login("13750831617", "123123123aA");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    @Override
    public void login(LoginBean userBean) {
        LogUtils.e(JSON.toJSONString(userBean));
    }

    private void initView() {
        btn = (Button) findViewById(R.id.btn);
        btnLeft = (Button) findViewById(R.id.btn_left);
        btnRight = (Button) findViewById(R.id.btn_right);
        btnLeft1 = (Button) findViewById(R.id.btn_left1);
        btnRight1 = (Button) findViewById(R.id.btn_right1);
        countdown = (Countdown) findViewById(R.id.countdown);
        countdown2 = (Countdown) findViewById(R.id.countdown2);
    }
}
