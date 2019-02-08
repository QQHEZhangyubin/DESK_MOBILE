package com.example.desk.ui.register;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desk.R;
import com.example.desk.entity.User;
import com.example.desk.mvp.MVPBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegisterActivity extends MVPBaseActivity<RegisterContract.View, RegisterPresenter> implements RegisterContract.View {
    private static final String TAG = "SignupActivity";
    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_class)
    EditText inputClass;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.input_birthday)
    EditText inputBirthday;
    @BindView(R.id.boy_id)
    RadioButton boyId;
    @BindView(R.id.girl_id)
    RadioButton girlId;
    @BindView(R.id.radioGroup_sex_id)
    RadioGroup radioGroupSexId;
    @BindView(R.id.btn_signup)
    AppCompatButton btnSignup;
    @BindView(R.id.link_login)
    TextView linkLogin;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        final User.DataBean usr = User.getInstance().getData();
        radioGroupSexId.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id= group.getCheckedRadioButtonId();
                switch (id){
                    case R.id.boy_id:
                        usr.setGender(boyId.getText().toString());
                        break;
                    case R.id.girl_id:
                        user.setGender(girlId.getText().toString());
                        break;
                }
            }
        });


    }

    @OnClick({R.id.btn_signup, R.id.link_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                Log.d(TAG, "Signup");
                String uid = inputName.getText().toString();
                String eamil = inputEmail.getText().toString();
                String classs = inputClass.getText().toString();
                String pwd = inputPassword.getText().toString();
                String birhday = inputBirthday.getText().toString();
                user.setUid(uid);
                user.setBirthday(birhday);
                user.setClasss(classs);
                user.setEmail(eamil);
                user.setPassword(pwd);
                if (!mPresenter.validate(user)) {
                    return;
                }
                btnSignup.setEnabled(false);
                final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Creating Account...");
                progressDialog.show();
                mPresenter.register(user);
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                },3000);
                break;
            case R.id.link_login:
                finish();
                break;
        }
    }
    //注册信息存在空项
    @Override
    public void emptymessage(String error) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
        btnSignup.setEnabled(true);
    }
    //注册密码不符合要求
    @Override
    public void errorpwd(String error) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
        btnSignup.setEnabled(true);
    }
    //不符合邮箱格式
    @Override
    public void erroremail(String error) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
        btnSignup.setEnabled(true);
    }
    //注册成功
    @Override
    public User registersuccess(User user) {
        Toast.makeText(this,"注册成功！",Toast.LENGTH_SHORT).show();
        btnSignup.setEnabled(true);
        return null;
    }
    //注册失败
    @Override
    public void registerfaith(String error) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
        btnSignup.setEnabled(true);
    }
}
