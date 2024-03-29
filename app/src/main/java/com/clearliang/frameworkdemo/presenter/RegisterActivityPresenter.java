package com.clearliang.frameworkdemo.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.clearliang.frameworkdemo.model.bean.LoginBean;
import com.clearliang.frameworkdemo.view.base.BaseInterface;
import com.clearliang.frameworkdemo.view.base.BasePresenter;

import rx.Subscriber;

/**
 * Created by ClearLiang on 2019/1/28
 * <p>
 * Function :
 */
public class RegisterActivityPresenter extends BasePresenter<RegisterActivityPresenter.RegisterActivityInterface> {
    private RegisterActivityInterface registerActivityInterface;

    public RegisterActivityPresenter(RegisterActivityInterface registerActivityInterface) {
        this.registerActivityInterface = registerActivityInterface;
    }

    public interface RegisterActivityInterface extends BaseInterface {

        void login(LoginBean userBean);
    }

    //登录
    public void login(String username, String password) {

        registerActivityInterface.showLoading("Loading...");
        addSubscription(apiStores.login(username, password, false), new Subscriber<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                registerActivityInterface.hideLoading();
                LogUtils.e(e.toString());
            }

            @Override
            public void onNext(LoginBean userBean) {
                registerActivityInterface.hideLoading();
                registerActivityInterface.login(userBean);
            }
        });
    }
}
