package com.example.desk.ui.myinfo;

import android.content.Context;

import com.example.desk.entity.U2;
import com.example.desk.mvp.BasePresenter;
import com.example.desk.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MyinfoContract {
    interface View extends BaseView {
        void SetU22(U2 u2);
    }

    interface  Presenter extends BasePresenter<View> {
        void getU(String userid);
    }
}
