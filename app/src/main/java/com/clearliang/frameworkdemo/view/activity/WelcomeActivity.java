package com.clearliang.frameworkdemo.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clearliang.frameworkdemo.LoginActivity;
import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.presenter.WelcomeActivityPresenter;
import com.clearliang.frameworkdemo.view.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * Created by ClearLiang on 2018/12/27
 * <p>
 * Function :欢迎界面
 */
public class WelcomeActivity extends BaseActivity<WelcomeActivityPresenter> implements WelcomeActivityPresenter.WelcomeActivityInterface {

    private RelativeLayout alBg;
    private TextView tvTime;

    @Override
    protected WelcomeActivityPresenter createPresenter() {
        return new WelcomeActivityPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        setClick(tvTime, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                openActivity(LoginActivity.class);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //这里新用到了interval操作符，它是用来在给定的时间间隔发射从0开始的整数序列。这里1s发射一次。
                Observable.interval(1, TimeUnit.SECONDS, rx.android.schedulers.AndroidSchedulers.mainThread())
                        .take(6)
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onCompleted() {
                                openActivity(LoginActivity.class);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {
                                tvTime.setText("取消 " + (5 - aLong));
                            }
                        });
            }
        }, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

    }


    private void initView() {
        alBg = (RelativeLayout) findViewById(R.id.al_bg);
        tvTime = (TextView) findViewById(R.id.tv_time);

    }

    @Override
    public void showLoading(String msg) {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }
}
