package com.clearliang.frameworkdemo.view.activity;

import android.os.Bundle;

import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.presenter.ForgetActivityPresenter;
import com.clearliang.frameworkdemo.view.base.BaseActivity;

/**
 * Created by ClearLiang on 2018/12/28
 * <p>
 * Function :
 */
public class ForgetActivity extends BaseActivity<ForgetActivityPresenter> implements ForgetActivityPresenter.ForgetActivityInterface {
    @Override
    protected ForgetActivityPresenter createPresenter() {
        return new ForgetActivityPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getMsg1(String msg) {

    }
}
