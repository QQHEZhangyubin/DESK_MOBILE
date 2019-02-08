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
        void loginSuccess(Object user);
        void loginFailed(String message);
        void pwdnotvalid(String error);//输入密码不合法
        void emptyusername(String error);//用户名为空

    }

    interface  Presenter extends BasePresenter<View> {
        //在View层调用，在Presenter中实现
        void login(String username,String password);
        //检查用户名和密码是否合格
        boolean valid(String username,String password);
    }
}
