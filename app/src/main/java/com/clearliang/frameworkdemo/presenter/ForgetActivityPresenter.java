package com.clearliang.frameworkdemo.presenter;

import com.clearliang.frameworkdemo.view.base.BasePresenter;

/**
 * Created by ClearLiang on 2018/12/28
 * <p>
 * Function :
 */
public class ForgetActivityPresenter extends BasePresenter<ForgetActivityPresenter.ForgetActivityInterface> {
    private ForgetActivityInterface forgetActivityInterface;

    public ForgetActivityPresenter(ForgetActivityInterface forgetActivityInterface) {
        this.forgetActivityInterface = forgetActivityInterface;
    }

    public interface ForgetActivityInterface {

    }


}
