package com.clearliang.frameworkdemo.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.presenter.HomeTabFragmentPresenter;
import com.clearliang.frameworkdemo.view.base.BaseFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;

/**
 * Created by ClearLiang on 2018/12/28
 * <p>
 * Function :
 */
public class HomeTabFragment extends BaseFragment<HomeTabFragmentPresenter> implements HomeTabFragmentPresenter.HomeTabFragmentInterface {
    private Button mButton;
    private QMUITopBar topbar;
    private DrawerLayout drawer;


    @Override
    protected HomeTabFragmentPresenter createPresenter() {
        return new HomeTabFragmentPresenter(this);
    }

    public static HomeTabFragment newInstance(String msg) {

        Bundle args = new Bundle();
        args.putString("msg", msg);
        HomeTabFragment fragment = new HomeTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initEvent() {
        final String msg = getArguments().getString("msg");
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ToastUtils.showShort(msg);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_home, container, false);

        initView(view);
        initTopBar();
        return view;
    }

    private void initView(View view) {
        mButton = (Button) view.findViewById(R.id.btn);
        topbar = (QMUITopBar) view.findViewById(R.id.topbar);
        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
    }

    private void initTopBar() {
        topbar.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.app_color_theme_6));
        topbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        topbar.addRightTextButton("通讯录", R.id.topbar_right_button_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        topbar.setTitle("Home界面");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
