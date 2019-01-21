package com.clearliang.frameworkdemo.presenter;

import com.clearliang.frameworkdemo.view.base.BasePresenter;

/**
 * Created by ClearLiang on 2018/12/28
 * <p>
 * Function :
 */
public class MainTabFragmentPresenter extends BasePresenter<MainTabFragmentPresenter.MainTabFragmentInterface> {
    private MainTabFragmentInterface mainTabFragmentInterface;

    public MainTabFragmentPresenter(MainTabFragmentInterface mainTabFragmentInterface) {
        this.mainTabFragmentInterface = mainTabFragmentInterface;
    }

    public interface MainTabFragmentInterface {

    }

}
