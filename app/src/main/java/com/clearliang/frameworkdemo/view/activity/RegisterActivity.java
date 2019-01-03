package com.clearliang.frameworkdemo.view.activity;

import android.os.Bundle;

import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.presenter.RegisterActivityPresenter;
import com.clearliang.frameworkdemo.view.base.BaseActivity;

/**
 * Created by ClearLiang on 2018/12/28
 * <p>
 * Function :
 */
public class RegisterActivity extends BaseActivity<RegisterActivityPresenter> implements RegisterActivityPresenter.RegisterActivityInterface {
    @Override
    protected RegisterActivityPresenter createPresenter() {
        return new RegisterActivityPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
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

}
