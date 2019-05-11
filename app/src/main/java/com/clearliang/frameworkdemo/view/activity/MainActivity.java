package com.clearliang.frameworkdemo.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.clearliang.frameworkdemo.LoginActivity;
import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.adapter.MyPagerAdapter;
import com.clearliang.frameworkdemo.presenter.MainActivityPresenter;
import com.clearliang.frameworkdemo.view.base.AppManager;
import com.clearliang.frameworkdemo.view.base.BaseActivity;
import com.clearliang.frameworkdemo.view.fragment.HomeTabFragment;
import com.clearliang.frameworkdemo.view.fragment.MainTabFragment;
import com.clearliang.frameworkdemo.view.fragment.UserTabFragment;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/frameworkdemo/view/activity/MainActivity")
public class MainActivity extends BaseActivity<MainActivityPresenter.MainActivityInterface, MainActivityPresenter> implements
        MainActivityPresenter.MainActivityInterface, NavigationView.OnNavigationItemSelectedListener {
    private List<Fragment> mFragments = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private AutoRelativeLayout arlParent;
    private QMUITopBar topbar;
    private QMUIViewPager pager;
    private QMUITabSegment tabs;
    private NavigationView navView;

    @Override
    protected MainActivityPresenter createPresenter() {
        return new MainActivityPresenter(this);
    }

    @Override
    protected void initData() {
        MainTabFragment mainTabFragment = MainTabFragment.newInstance("消息 Arguments");
        HomeTabFragment homeTabFragment = HomeTabFragment.newInstance("主页 Arguments");
        UserTabFragment userTabFragment = UserTabFragment.newInstance("我的 Arguments");
        mFragments.add(mainTabFragment);
        mFragments.add(homeTabFragment);
        mFragments.add(userTabFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEvent() {
        //抽屉内item监听
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initSetting();
        //initTopBar();
        initTabAndPager();
    }

    private void initSetting() {
        arlParent.setBackgroundColor(setColorAlpha(R.color.app_color_theme_6, 0.8f));

    }


    private void initTopBar() {
        topbar.setBackgroundColor(ContextCompat.getColor(this, R.color.app_color_theme_6));
        topbar.addLeftImageButton(R.drawable.vector_drawable_menu_, R.id.topbar_left_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        topbar.setTitle("沉浸式状态栏示例");
    }

    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        arlParent = (AutoRelativeLayout) findViewById(R.id.arl_parent);
        topbar = (QMUITopBar) findViewById(R.id.topbar);
        pager = (QMUIViewPager) findViewById(R.id.pager);
        tabs = (QMUITabSegment) findViewById(R.id.tabs);
        navView = (NavigationView) findViewById(R.id.nav_view);

        /*TextView tvHeader = (TextView) navView.getHeaderView(0).findViewById(R.id.tv_introduce);
        TextView tvIntro = (TextView) navView.getHeaderView(0).findViewById(R.id.tv_name);
        tvHeader.setText("这里是用户名");
        tvIntro.setText("这里是用户的描述");*/


    }

    @Override
    public void showLoading(String msg) {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            new QMUIDialog.MessageDialogBuilder(this)
                    .setTitle("标题")
                    .setMessage("是否需要退出？")
                    .addAction("取消", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            dialog.dismiss();
                        }
                    })
                    .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            dialog.dismiss();
                            AppManager.getAppManager().finishAllActivity();
                            openActivity(LoginActivity.class);
                        }
                    })
                    .create(R.style.QMUI_Dialog).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_1://
                break;

            case R.id.nav_2://
                break;

            case R.id.nav_3://
                break;

            case R.id.nav_4://
                break;

            case R.id.nav_5://
                break;

            case R.id.nav_6://
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initTabAndPager() {
        int normalColor = QMUIResHelper.getAttrColor(this, R.attr.qmui_config_color_gray_6);
        int selectColor = QMUIResHelper.getAttrColor(this, R.attr.qmui_config_color_blue);
        tabs.setDefaultNormalColor(normalColor);
        tabs.setDefaultSelectedColor(selectColor);

        QMUITabSegment.Tab component = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_component),
                ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_component_selected),
                "消息", false
        );

        QMUITabSegment.Tab util = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_util),
                ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_util_selected),
                "主页", false
        );

        QMUITabSegment.Tab lab = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_lab),
                ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_lab_selected),
                "我的", false
        );

        tabs.addTab(component)
                .addTab(util)
                .addTab(lab);

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments);

        pager.setAdapter(adapter);
        pager.setCurrentItem(1, false);
        tabs.setupWithViewPager(pager, false);
        tabs.setMode(QMUITabSegment.MODE_FIXED);
        tabs.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
                tabs.hideSignCountView(index);
                pager.setCurrentItem(index, false);
                //LogUtils.e("onTabSelected", index);
            }

            @Override
            public void onTabUnselected(int index) {
                //LogUtils.e("onTabUnselected", index);
            }

            @Override
            public void onTabReselected(int index) {
                tabs.hideSignCountView(index);
                // 在当前页点击当前页
                //LogUtils.e("onTabReselected", index);
            }

            @Override
            public void onDoubleTap(int index) {
                //LogUtils.e("onDoubleTap", index);
            }
        });

    }

}
