package com.clearliang.frameworkdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 99794 on 2018/4/24.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public void setFragments(List<Fragment> fragments) {
        mFragmentList = fragments;
    }

    public MyPagerAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        mFragmentList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {

        return mFragmentList.size();
    }
}
