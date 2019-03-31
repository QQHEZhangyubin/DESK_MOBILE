package com.example.desk.ui.softupdate;

import android.content.Context;

import com.example.desk.entity.Soft2;
import com.example.desk.mvp.BasePresenter;
import com.example.desk.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SoftupdateContract {
    interface View extends BaseView {

        void HadNewSoft(Soft2 softT);
    }

    interface  Presenter extends BasePresenter<View> {
        void CheckUpdate();
    }
}
