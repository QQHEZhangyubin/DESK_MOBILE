package com.example.desk.ui.register;

import com.example.desk.entity.User;
import com.example.desk.mvp.BasePresenter;
import com.example.desk.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RegisterContract {
    interface View extends BaseView {
        //在View层回调，根据Presenter逻辑调用
        void emptymessage(String error);//注册信息存在空项时回调
        void errorpwd(String error);//不合适的密码
        void erroremail(String error);//不合适的邮箱

        void registersuccess(User user);//注册成功
        void registerfaith(String error);//注册失败
    }

    interface  Presenter extends BasePresenter<View> {
        boolean validate(User user);//验证用户注册信息是否合法
        void register(User user);//提交信息到服务器
    }
}
