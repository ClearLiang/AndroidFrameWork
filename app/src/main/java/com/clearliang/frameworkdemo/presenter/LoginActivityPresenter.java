package com.clearliang.frameworkdemo.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.clearliang.frameworkdemo.model.bean.TokenCheckBean;
import com.clearliang.frameworkdemo.model.bean.UserBean;
import com.clearliang.frameworkdemo.view.base.BasePresenter;

import rx.Subscriber;

/**
 * Created by ClearLiang on 2018/12/27
 * <p>
 * Function :
 */
public class LoginActivityPresenter extends BasePresenter<LoginActivityPresenter.LoginActivityInterface> {

    private LoginActivityInterface loginActivityInterface;

    public interface LoginActivityInterface {

        void showLoading(String s);

        void hideLoading();

        void login(UserBean userBean);

        void checkToken(TokenCheckBean tokenCheckBean);

    }

    //登录
    public void login(String username, String password) {

        loginActivityInterface.showLoading("登录中...");

        addSubscription(apiStores.login(username, password), new Subscriber<UserBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                loginActivityInterface.hideLoading();
                LogUtils.e(e.toString());
            }

            @Override
            public void onNext(UserBean userBean) {
                loginActivityInterface.login(userBean);
                loginActivityInterface.hideLoading();
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
