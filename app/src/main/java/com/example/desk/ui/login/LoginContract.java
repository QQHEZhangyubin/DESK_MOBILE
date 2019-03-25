package com.example.desk.ui.login;

import com.example.desk.mvp.BasePresenter;
import com.example.desk.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginContract {
    interface View extends BaseView {
        //在View层回调，根据Presenter逻辑调用
        void loginSuccess(String userid, String userlogo, String passwordd);
        void loginFailed(String message);
    }

    interface  Presenter extends BasePresenter<View> {
        //在View层调用，在Presenter中实现
        void login(String username,String password);
    }
}
