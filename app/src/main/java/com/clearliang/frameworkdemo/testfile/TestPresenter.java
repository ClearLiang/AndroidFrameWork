package com.clearliang.frameworkdemo.testfile;

import com.clearliang.frameworkdemo.model.bean.UserBean;
import com.clearliang.frameworkdemo.view.base.BasePresenter;

import rx.Subscriber;

/**
 * Created by ClearLiang on 2018/12/29
 * <p>
 * Function :
 */
public class TestPresenter extends BasePresenter<TestPresenter.DataInterface> {
    private DataInterface mDataInterface;

    public interface DataInterface {
        /*void getMsg1(String a);

        void getMsg2(int b);

        void getMsg3(boolean c);

        void getMsg4(UserBean userBean);*/
    }

    public void getMsg() {

        addSubscription(apiStores.login("", ""), new Subscriber<UserBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //mDataInterface.getMsg1(e.toString());
            }

            @Override
            public void onNext(UserBean userBean) {
                //mDataInterface.getMsg4(userBean);
            }
        });
    }

}
