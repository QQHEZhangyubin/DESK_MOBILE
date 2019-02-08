package com.example.desk.ui.register;

import android.util.Patterns;
import android.widget.Toast;

import com.example.desk.api.RegisterRequestServer;
import com.example.desk.entity.Message;
import com.example.desk.entity.User;
import com.example.desk.mvp.BasePresenterImpl;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RegisterPresenter extends BasePresenterImpl<RegisterContract.View> implements RegisterContract.Presenter{

    @Override
    public boolean validate(User user) {
        //TODO:验证用户注册的信息是否符合要求
        boolean valid = true;
        if (!user.getUid().isEmpty() && !user.getCollege().isEmpty() && !user.getClasss().isEmpty()
                && !user.getBirthday().isEmpty() && !user.getEmail().isEmpty() && !user.getPassword().isEmpty()
                && !user.getGender().isEmpty()){
            if (!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()){
                mView.erroremail("邮箱信息存在问题！");
                valid = false;
            }
            if (user.getPassword().length()<4||user.getPassword().length()>10){
                mView.errorpwd("密码需要在4-10位之间！");
                valid = false;
            }
        }else {
            mView.emptymessage("注册填写信息存在空项！");
            valid = false;
        }
        return valid;
    }

    @Override
    public boolean register(User user) {
        //TODO：提交用户注册信息到服务器
         boolean flag = true;
        mView.registerfaith("注册失败，暂时连接不到服务器！");
        mView.registersuccess(user);
        ///////////////////////
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterRequestServer registerRequestServer = retrofit.create(RegisterRequestServer.class);

        Call<Message> call = registerRequestServer.getCall(user.getUid());
        //发送网络请求(同步)
        try {
            Response<Message> response = call.execute();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        }

        ////////

        return flag;
    }
}
