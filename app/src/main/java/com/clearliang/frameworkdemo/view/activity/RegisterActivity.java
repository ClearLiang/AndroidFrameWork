package com.clearliang.frameworkdemo.view.activity;

import android.os.Bundle;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.model.bean.LoginBean;
import com.clearliang.frameworkdemo.presenter.RegisterActivityPresenter;
import com.clearliang.frameworkdemo.view.base.BaseActivity;

import rx.functions.Action1;

/**
 * Created by ClearLiang on 2019/1/28
 * <p>
 * Function :
 */
public class RegisterActivity extends BaseActivity<RegisterActivityPresenter.RegisterActivityInterface, RegisterActivityPresenter>
        implements RegisterActivityPresenter.RegisterActivityInterface {

    private Button btnRegister;

    @Override
    protected RegisterActivityPresenter createPresenter() {
        return new RegisterActivityPresenter(this);
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
        setClick(btnRegister, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mPresenter.login("13750831617", "123123123aA");
                //getPresenter().login("13750831617","123123123aA");
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

    @Override
    public void showLoading(String msg) {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    private void initView() {
        btnRegister = (Button) findViewById(R.id.btn_register);
    }
}
