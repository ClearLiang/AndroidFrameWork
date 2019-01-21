package com.clearliang.frameworkdemo.presenter;

import com.clearliang.frameworkdemo.view.base.BasePresenter;

/**
 * Created by ClearLiang on 2018/12/28
 * <p>
 * Function :
 */
public class UserTabFragmentPresenter extends BasePresenter<UserTabFragmentPresenter.UserTabFragmentInterface> {
    private UserTabFragmentInterface userTabFragmentInterface;

    public UserTabFragmentPresenter(UserTabFragmentInterface userTabFragmentInterface) {
        this.userTabFragmentInterface = userTabFragmentInterface;
    }

    public interface UserTabFragmentInterface {

    }

}
