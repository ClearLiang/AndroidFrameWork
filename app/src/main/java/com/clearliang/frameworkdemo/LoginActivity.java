package com.clearliang.frameworkdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.clearliang.frameworkdemo.model.bean.TokenCheckBean;
import com.clearliang.frameworkdemo.model.bean.UserBean;
import com.clearliang.frameworkdemo.presenter.LoginActivityPresenter;
import com.clearliang.frameworkdemo.utils.RSAUtil;
import com.clearliang.frameworkdemo.view.activity.MainActivity;
import com.clearliang.frameworkdemo.view.base.BaseActivity;
import com.clearliang.frameworkdemo.view.widget.LineTextView;

import java.security.PrivateKey;
import java.security.PublicKey;

import rx.functions.Action1;

/**
 * Created by ClearLiang on 2018/12/27
 * <p>
 * Function :
 */
public class LoginActivity extends BaseActivity<LoginActivityPresenter> implements LoginActivityPresenter.LoginActivityInterface{

    private EditText etUsername, etPassword;
    private ImageView ivBg;
    private TextView tvRegister, tvForget;
    private CheckBox cbSave;
    private Button btnLogin;

    @Override
    protected LoginActivityPresenter createPresenter() {
        return new LoginActivityPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {


        setClick(btnLogin, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
//                openActivity(MainActivity.class);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        String currentToken = SPUtils.getInstance(SP_USERINFO).getString(SP_TOKEN);
        //mPresenter.checkToken(currentToken);//验证token
    }

    private void initView() {
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        ivBg = (ImageView) findViewById(R.id.iv_bg);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        tvForget = (TextView) findViewById(R.id.tv_forget);
        cbSave = (CheckBox) findViewById(R.id.cb_save);
        btnLogin = (Button) findViewById(R.id.btn_login);

        LineTextView ltvUsername = (LineTextView) findViewById(R.id.ltv_username);
        LineTextView ltvPassword = (LineTextView) findViewById(R.id.ltv_password);

        ltvUsername.setText("用户名");
        ltvUsername.setTestSize(18);
        ltvPassword.setText("密码");
        ltvPassword.setTestSize(18);

        ivBg.setBackgroundResource(R.drawable.bg_login_3);

        String username = SPUtils.getInstance("UserInfo").getString(SP_USERNAME);
        etUsername.setText(username);

        String pwd = SPUtils.getInstance("UserInfo").getString(SP_PASSWORD);
        String priKey = SPUtils.getInstance("RSAKey").getString("PrivateKey");
        if ((!TextUtils.isEmpty(pwd)) && (!TextUtils.isEmpty(priKey))) {
            PrivateKey privateKey = null;
            try {
                privateKey = RSAUtil.getRSAUtil().getPrivateKey(priKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String password = RSAUtil.getRSAUtil().myDecryption(pwd, privateKey);
            etPassword.setText(password);
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void login(UserBean userBean) {
        if (cbSave.isChecked()) {
            SPUtils.getInstance("UserInfo").put(SP_TOKEN, userBean.getToken());
            saveLoginInfo(etPassword.getText().toString().trim());
        }
    }

    @Override
    public void checkToken(TokenCheckBean tokenCheckBean) {
        if (tokenCheckBean.getCode() == 1) {
            ToastUtils.showShort(R.string.string_login_overdue);
        } else {
            openActivity(MainActivity.class);
        }
    }

    private void saveLoginInfo(String data) {
        RSAUtil.getRSAUtil().generateRSAKeyPair();

        String pubString = SPUtils.getInstance("RSAKey").getString("PublicKey");

        PublicKey publicKey = null;
        try {
            publicKey = RSAUtil.getRSAUtil().getPublicKey(pubString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String encryptData = RSAUtil.getRSAUtil().myEncrypt(data, publicKey);

        //LogUtils.e("加密后", encryptData);
        SPUtils.getInstance("UserInfo").put(SP_USERNAME, etUsername.getText().toString().trim());
        SPUtils.getInstance("UserInfo").put(SP_PASSWORD, encryptData);
    }

    @Override
    public void showLoading(String msg) {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }
}
