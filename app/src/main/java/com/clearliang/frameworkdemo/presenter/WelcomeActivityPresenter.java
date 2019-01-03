package com.clearliang.frameworkdemo.presenter;

import com.clearliang.frameworkdemo.view.base.BasePresenter;

/**
 * Created by ClearLiang on 2018/12/27
 * <p>
 * Function :
 */
public class WelcomeActivityPresenter extends BasePresenter<WelcomeActivityPresenter.WelcomeActivityInterface> {
    private WelcomeActivityInterface welcomeActivityInterface;

    public interface WelcomeActivityInterface {
        void showLoading(String msg);
        void hideLoading();
    }


}
