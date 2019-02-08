package com.example.desk.ui.login;

import android.content.Intent;

import com.example.desk.api.RegisterRequestServer;
import com.example.desk.entity.User;
import com.example.desk.mvp.BasePresenterImpl;
import com.example.desk.ui.register.RegisterActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter{
    @Override
    public void login(String username, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RegisterRequestServer request = retrofit.create(RegisterRequestServer.class);
        Call<User> call = request.getCall(username, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getError_code() == 0){
                    User.ClearUser();
                    User u = User.getInstance();
                    u.setData(response.body().getData());
                    mView.loginSuccess(u);
                }else {
                    mView.loginFailed("不存在该用户！");
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mView.loginFailed("登录失败，"+t.getMessage());
            }
        });
    }

    @Override
    public boolean valid(String username, String password) {
        boolean valid = true;
        if (username.isEmpty()) {
            //inputEmail.setError("enter a valid email address");
            mView.emptyusername("enter a valid email address");
            valid = false;
        } else {
            //inputEmail.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
           // inputPassword.setError("between 4 and 10 alphanumeric characters");
            mView.pwdnotvalid("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            //inputPassword.setError(null);
        }
        return valid;
    }
}
