package com.clearliang.frameworkdemo.testfile;

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
public class TestActivityPresenter extends BasePresenter<TestActivityPresenter.TestActivityInterface>{
    private TestActivityInterface testActivityInterface;

    public TestActivityPresenter(TestActivityInterface testActivityInterface) {
        this.testActivityInterface = testActivityInterface;
    }

    public interface TestActivityInterface extends BaseInterface {

        void login(LoginBean userBean);
    }

    //登录
    public void login(String username, String password) {

        testActivityInterface.showLoading("Loading...");
        addSubscription(apiStores.login(username, password, false), new Subscriber<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                testActivityInterface.hideLoading();
                LogUtils.e(e.toString());
            }

            @Override
            public void onNext(LoginBean userBean) {
                testActivityInterface.hideLoading();
                testActivityInterface.login(userBean);
            }
        });
    }
}
