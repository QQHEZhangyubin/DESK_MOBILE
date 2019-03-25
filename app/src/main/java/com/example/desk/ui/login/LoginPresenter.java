package com.example.desk.ui.login;

import com.example.desk.api.APIWrapper;
import com.example.desk.entity.User;
import com.example.desk.mvp.BasePresenterImpl;
import com.example.desk.util.ShareUtils;
import com.example.desk.util.TLog;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter{
    private String userid;
    private String userlogo;
    private String passwordd;
    @Override
    public void login(String username, final String password) {
        APIWrapper.getInstance().loginuser(username,password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<User>() {
            @Override
            public void onCompleted() {
                mView.loginSuccess(userid,userlogo,passwordd);
            }

            @Override
            public void onError(Throwable e) {
                TLog.error(e.toString());
                mView.loginFailed("登录失败");
            }

            @Override
            public void onNext(User user) {
                TLog.log(user.getData().getUserid());
                if (user.getError_code() == 1){
                    mView.loginFailed("用户信息不存在");
                }else if (user.getError_code() == 0){
                   userid = user.getData().getUserid();
                   userlogo = user.getData().getUserlogo();
                   passwordd = password;

                }

            }
        });
    }


}
