package com.example.desk.ui.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desk.R;
import com.example.desk.R2;
import com.example.desk.mvp.MVPBaseActivity;
import com.example.desk.ui.register.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    @BindView(R2.id.input_email)
    EditText inputEmail;
    @BindView(R2.id.input_password)
    EditText inputPassword;
    @BindView(R2.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R2.id.link_signup)
    TextView linkSignup;
    @OnClick(R2.id.link_signup)
    public void Link_Signup(){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }
    @OnClick(R2.id.btn_login)
    public void login(){
        Log.d(TAG, "Login");
        final String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();
        if (!mPresenter.valid(email,password)) {
           // onLoginFailed();
            return;
        }
        btnLogin.setEnabled(false);
        mPresenter.login(email,password);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public void loginSuccess(Object user) {
        //TODO:实现成功后的操作
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 3000);
        btnLogin.setEnabled(true);
        finish();
    }

    @Override
    public void loginFailed(String message) {
        //TODO:实现失败后的操作
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        btnLogin.setEnabled(true);
    }

    //用户密码不合法时提示用户
    @Override
    public void pwdnotvalid(String error) {
        //TODO:实现密码不合法后的操作
        inputPassword.setError(error);
    }
    //账号为空的操作
    @Override
    public void emptyusername(String error) {
        //TODO:实现账号为空的操作
        inputEmail.setError("enter a valid email address");
    }
}
