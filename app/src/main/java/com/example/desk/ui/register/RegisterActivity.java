package com.example.desk.ui.register;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.desk.ui.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegisterActivity extends MVPBaseActivity<RegisterContract.View, RegisterPresenter> implements RegisterContract.View {

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
    @BindView(R.id.input_college)
    EditText inputCollege;
    private User.DataBean dataBean;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        dataBean = new User.DataBean();//实例化一个databean
        radioGroupSexId.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.boy_id:
                        dataBean.setGender(boyId.getText().toString());
                        break;
                    case R.id.girl_id:
                        dataBean.setGender(girlId.getText().toString());
                        break;
                }
            }
        });
    }

    @OnClick({R.id.btn_signup, R.id.link_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                User user = new User();
                String uid = inputName.getText().toString();
                String eamil = inputEmail.getText().toString();
                String classs = inputClass.getText().toString();
                String pwd = inputPassword.getText().toString();
                String birhday = inputBirthday.getText().toString();
                String college = inputCollege.getText().toString();
                dataBean.setUserid(uid);
                dataBean.setBirthday(birhday);
                dataBean.setClasss(classs);
                dataBean.setEmail(eamil);
                dataBean.setPassword(pwd);
                dataBean.setCollege(college);
                user.setData(dataBean);
                if (!mPresenter.validate(user)) {
                    return;
                }
                btnSignup.setEnabled(false);
                progressDialog = new ProgressDialog(RegisterActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("创建账户...");
                progressDialog.show();
                mPresenter.register(user);
                break;
            case R.id.link_login:
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    //注册信息存在空项
    @Override
    public void emptymessage(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        btnSignup.setEnabled(true);
    }

    //注册密码不符合要求
    @Override
    public void errorpwd(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        btnSignup.setEnabled(true);
    }

    //不符合邮箱格式
    @Override
    public void erroremail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        btnSignup.setEnabled(true);
    }

    //注册成功
    @Override
    public void registersuccess(User user) {
        progressDialog.dismiss();
        Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
        btnSignup.setEnabled(true);
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        intent.putExtra("xuehao",user.getData().getUserid());
        intent.putExtra("mima",user.getData().getPassword());
        startActivity(intent);
        finish();
    }

    //注册失败
    @Override
    public void registerfaith(String error) {
        progressDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        btnSignup.setEnabled(true);
    }

}
