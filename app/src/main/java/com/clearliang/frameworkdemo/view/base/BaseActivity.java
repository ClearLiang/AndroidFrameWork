package com.clearliang.frameworkdemo.view.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.utils.EventBusUtil;
import com.jakewharton.rxbinding.view.RxView;
import com.qmuiteam.qmui.util.QMUIColorHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * @作者 ClearLiang
 * @日期 2018/4/13
 * @描述 Activity的基类
 **/

abstract public class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements GlobalConstants {
    protected Bundle mBundle = new Bundle();
    protected T mPresenter;
    protected boolean isTouchEvent = true;
    protected QMUITipDialog qmuiTipDialog;

    protected abstract T createPresenter();

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initEvent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);//沉浸式

        if (getLayoutId() != 0)
            setContentView(getLayoutId());

        mPresenter = createPresenter();
        mPresenter.attachView(this);
        AppManager.getAppManager().addActivity(this);
        //LogUtils.i(getClass().getSimpleName());

        //注册EventBus
        if (isRegisterEventBus() && (!EventBusUtil.isRegistered(this))) {
            EventBusUtil.register(this);
        }
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //LogUtils.d("onStart");
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //LogUtils.d("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //LogUtils.d("onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.datachView();
        // LogUtils.d("onDestroy");
        AppManager.getAppManager().finishActivity(this);
        EventBusUtil.unregister(this);
    }

    protected void openActivity(Class<?> mClass) {
        openActivity(mClass, null);
    }

    protected void openActivity(Class<?> mClass, Bundle mBundle) {
        Intent mIntent = new Intent(this, mClass);
        if (null != mBundle) {
            mIntent.putExtras(mBundle);
        }
        // LogUtils.d("openActivity with bundle");
        startActivity(mIntent);
        // 设置开关Activity的动画
        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }

    protected void openActivityForResult(Class<?> mClass, int requestCode) {
        openActivityForResult(mClass, requestCode, null);
    }

    protected void openActivityForResult(Class<?> mClass, int requestCode, Bundle bundle) {
        Intent mIntent = new Intent(this, mClass);
        if (bundle != null) {
            mIntent.putExtras(bundle);
        }
        startActivityForResult(mIntent, requestCode);
        // 设置开关Activity的动画
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    protected void openService(Class<?> mClass, Bundle bundle) {
        Intent intent = new Intent(this, mClass);
        intent.putExtras(bundle);
        startService(intent);
    }

    @Override
    public void finish() {
        super.finish();
        //LogUtils.d("finish");
        // 设置开关Activity的动画
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * Rx点击事件防抖动
     */
    protected void setClick(View view, Action1<Void> action1) {
        RxView.clicks(view).throttleFirst(1, TimeUnit.SECONDS).subscribe(action1);
    }

    /**
     * Rx点击事件 定时器
     */
    protected void setDelayClick(final View view, final long delayTime, final Observer<Long> action2) {
        RxView.clicks(view)
                .throttleFirst(delayTime, TimeUnit.SECONDS)
                .subscribeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        //这里新用到了interval操作符，它是用来在给定的时间间隔发射从0开始的整数序列。这里1s发射一次。
                        Observable.interval(1, TimeUnit.SECONDS, rx.android.schedulers.AndroidSchedulers.mainThread())
                                .take((int) delayTime)
                                .subscribe(action2);
                    }
                });
    }

    /**
     * Rx点击事件 定时器
     */
    protected void setDelayClick(final View view, final long delayTime, Action1<Void> action1, final Observer<Long> action2) {
        RxView.clicks(view)
                .throttleFirst(delayTime, TimeUnit.SECONDS)
                .subscribeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .doOnNext(action1)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        //这里新用到了interval操作符，它是用来在给定的时间间隔发射从0开始的整数序列。这里1s发射一次。
                        Observable.interval(1, TimeUnit.SECONDS, rx.android.schedulers.AndroidSchedulers.mainThread())
                                .take((int) delayTime)
                                .subscribe(action2);
                    }
                });
    }

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    protected void showLoadingDialog(){
        showLoadingDialog(getResources().getString(R.string.string_loading));
    }

    protected void showLoadingDialog(String msg){
        qmuiTipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(msg)
                .create();
        qmuiTipDialog.show();
        qmuiTipDialog.setCanceledOnTouchOutside(false);
    }

    protected void hideLoadingDialog(){
        qmuiTipDialog.dismiss();
    }

    /**
     * 设置颜色的 alpha 值
     * */
    protected int setColorAlpha(int colorId,float alpha){
        return QMUIColorHelper.setColorAlpha(getResources().getColor(colorId), alpha);
    }

}
