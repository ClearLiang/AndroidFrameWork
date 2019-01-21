package com.clearliang.frameworkdemo.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.presenter.MainTabFragmentPresenter;
import com.clearliang.frameworkdemo.view.base.BaseFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;

/**
 * Created by ClearLiang on 2018/12/28
 * <p>
 * Function :
 */
public class MainTabFragment extends BaseFragment<MainTabFragmentPresenter> implements MainTabFragmentPresenter.MainTabFragmentInterface {
    private Button mButton;
    private QMUITopBar topbar;

    @Override
    protected MainTabFragmentPresenter createPresenter() {
        return new MainTabFragmentPresenter(this);
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

    public static MainTabFragment newInstance(String msg) {

        Bundle args = new Bundle();
        args.putString("msg", msg);
        MainTabFragment fragment = new MainTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_main, container, false);

        initView(view);
        initTopBar();
        return view;
    }

    private void initView(View view) {
        mButton = (Button) view.findViewById(R.id.btn);
        topbar = (QMUITopBar) view.findViewById(R.id.topbar);
    }

    private void initTopBar() {
        topbar.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.app_color_theme_6));
        topbar.addRightTextButton("一点", R.id.topbar_right_button_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        topbar.setTitle("Main界面");
    }

    @Override
    public void getMsg(String msg) {

    }

    @Override
    public void showLoading(String s) {

    }

    @Override
    public void hideLoading() {

    }
}
