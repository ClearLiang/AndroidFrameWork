package com.clearliang.frameworkdemo.testfile;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.clearliang.frameworkdemo.model.bean.UserBean;
import com.clearliang.frameworkdemo.view.base.BasePresenter;

import java.io.File;
import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;
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

    public void downloadFile() {

        String DOWNLOAD_APP_URL="https://dldir1.qq.com/qqfile/qq/QQ9.0.3/23719/QQ9.0.3.exe";
        addSubscription(apiStores.downloadFile(DOWNLOAD_APP_URL), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //mDataInterface.getMsg1(e.toString());
            }

            @Override
            public void onNext(final ResponseBody response) {
                LogUtils.e("信息:",response.contentLength());
            }
        });
    }

}
