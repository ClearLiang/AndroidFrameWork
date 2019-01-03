package com.clearliang.frameworkdemo.view.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.event.Event;
import com.clearliang.frameworkdemo.utils.EventBusUtil;
import com.jakewharton.rxbinding.view.RxView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * Created by ClearLiang on 2018/9/25
 * <p>
 * Function :重写BaseFragment实现懒加载
 *
 * Fragment预加载问题的解决方案：
 * 1.可以懒加载的Fragment
 * 2.切换到其他页面时停止加载数据（可选）
 * Created by yuandl on 2016-11-17.
 * blog ：http://blog.csdn.net/linglongxin24/article/details/53205878
 */

public abstract  class LazyLoadFragment<V, T extends BasePresenter<V>> extends Fragment implements GlobalConstants {
    /**
     * 视图是否已经初初始化
     */
    protected boolean isInit = false;
    protected boolean isLoad = false;

    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }

        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    /**
     * 视图销毁的时候将Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
        isInit = false;
        isLoad = false;

    }

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void lazyLoad();

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected void stopLoad() {
    }

    protected Bundle mBundle = new Bundle();
    protected T mPresenter;
    protected Activity mActivity;
    protected QMUITipDialog loadingDialog;

    protected abstract T createPresenter();

    protected abstract void initEvent();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) getActivity());

        isInit = true;
        isCanLoadData();

        initEvent();

    }

    protected void openActivity(Class<?> mClass) {
        LogUtils.d("openActivity");
        openActivity(mClass, null);
    }

    protected void openActivity(Class<?> mClass, Bundle mBundle) {
        Intent mIntent = new Intent(getActivity(), mClass);
        if (null != mBundle) {
            mIntent.putExtras(mBundle);
        }
        getActivity().startActivity(mIntent);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    protected void setClick(View view, Action1<Void> action1) {
        RxView.clicks(view).throttleFirst(1, TimeUnit.SECONDS).subscribe(action1);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册EventBus
        if (isRegisterEventBus() && (!EventBusUtil.isRegistered(this))) {
            EventBusUtil.register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventBusCom_sticky(Event event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    protected void receiveStickyEvent(Event event) {

    }

    protected boolean isRegisterEventBus() {
        return false;
    }

    public void showDialog(String message) {
        loadingDialog = new QMUITipDialog.Builder(mActivity)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(message)
                .create();
        loadingDialog.show();
    }

    public void disMissDialog() {
        loadingDialog.dismiss();
    }
}
