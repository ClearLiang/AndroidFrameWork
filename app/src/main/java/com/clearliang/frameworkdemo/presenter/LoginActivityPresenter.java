package com.clearliang.frameworkdemo.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.clearliang.frameworkdemo.model.bean.LoginBean;
import com.clearliang.frameworkdemo.model.bean.TokenCheckBean;
import com.clearliang.frameworkdemo.view.base.BaseInterface;
import com.clearliang.frameworkdemo.view.base.BasePresenter;

import rx.Subscriber;

/**
 * Created by ClearLiang on 2018/12/27
 * <p>
 * Function :
 */
public class LoginActivityPresenter extends BasePresenter<LoginActivityPresenter.LoginActivityInterface> {

    private LoginActivityInterface loginActivityInterface;

    public LoginActivityPresenter(LoginActivityInterface loginActivityInterface) {
        this.loginActivityInterface = loginActivityInterface;
    }

    public interface LoginActivityInterface extends BaseInterface {

        void login(LoginBean userBean);

        void checkToken(TokenCheckBean tokenCheckBean);

    }

    //登录
    public void login(String username, String password) {

        loginActivityInterface.showLoading("Loading...");
        addSubscription(apiStores.login(username, password, false), new Subscriber<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                loginActivityInterface.hideLoading();
                LogUtils.e(e.toString());
            }

            @Override
            public void onNext(LoginBean userBean) {
                loginActivityInterface.hideLoading();
                loginActivityInterface.login(userBean);
            }
        });
    }

    // 验证token是否过期
    public void checkToken(String token) {

        loginActivityInterface.showLoading("登录中...");

        addSubscription(apiStores.checkToken(token), new Subscriber<TokenCheckBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                loginActivityInterface.hideLoading();
                LogUtils.e(e.toString());
            }

            @Override
            public void onNext(TokenCheckBean tokenCheckBean) {
                loginActivityInterface.checkToken(tokenCheckBean);
                loginActivityInterface.hideLoading();
            }
        });
    }

}
