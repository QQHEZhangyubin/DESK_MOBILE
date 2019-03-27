package com.example.desk.ui.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desk.MainActivity;
import com.example.desk.R;
import com.example.desk.mvp.MVPBaseActivity;
import com.example.desk.ui.register.RegisterActivity;
import com.example.desk.util.ShareUtils;
import com.example.desk.util.StaticClass;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {
    @BindView(R.id.input_login_xuehao)
    EditText inputxuehao;
    @BindView(R.id.input_login_password)
    EditText inputloginPassword;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R.id.link_signup)
    TextView linkSignup;
    @BindView(R.id.keep_password)
    CheckBox keepPassword;

    private ProgressDialog progressDialog;

    @OnClick(R.id.link_signup)
    public void Link_Signup() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    public void login() {
        String userid = inputxuehao.getText().toString();
        String password = inputloginPassword.getText().toString();
        btnLogin.setEnabled(false);
        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("登录中...");
        progressDialog.show();
        mPresenter.login(userid, password);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            String xuehao = intent.getStringExtra("xuehao");
            String mima = intent.getStringExtra("mima");
            inputxuehao.setText(xuehao);
            inputloginPassword.setText(mima);
        }
        boolean isKeep = ShareUtils.getBoolean(getApplicationContext(),StaticClass.Keeppass,false);
        keepPassword.setChecked(isKeep);

        if (isKeep){
            String name = ShareUtils.getString(getApplicationContext(),StaticClass.userid,"");
            String pwd = ShareUtils.getString(getApplicationContext(),StaticClass.password,"");
            inputxuehao.setText(name);
            inputloginPassword.setText(pwd);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存状态
        ShareUtils.putBoolean(getApplicationContext(),StaticClass.Keeppass,keepPassword.isChecked());

        if (keepPassword.isChecked()){
            ShareUtils.putString(getApplicationContext(),StaticClass.userid,inputxuehao.getText().toString().trim());
            ShareUtils.putString(getApplicationContext(),StaticClass.password,inputloginPassword.getText().toString().trim());
        }else {
            ShareUtils.deleShare(getApplicationContext(),StaticClass.userid);
            ShareUtils.deleShare(getApplicationContext(),StaticClass.password);
        }
    }

    @Override
    public void loginSuccess(String userid, String userlogo, String passwordd) {
        ShareUtils.deleAll(getApplicationContext());
        ShareUtils.putString(getApplicationContext(), StaticClass.userid, userid);
        ShareUtils.putString(getApplicationContext(), StaticClass.userlogo, userlogo);
        ShareUtils.putString(getApplicationContext(), StaticClass.password, passwordd);
        progressDialog.dismiss();
        btnLogin.setEnabled(true);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginFailed(String message) {
        progressDialog.dismiss();
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
        btnLogin.setEnabled(true);
    }

    @OnClick(R.id.keep_password)
    public void onViewClicked() {

    }
}
