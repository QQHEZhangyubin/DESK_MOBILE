package com.example.desk.ui.third;

import android.content.Context;

import com.example.desk.entity.MyState;
import com.example.desk.mvp.BasePresenter;
import com.example.desk.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ThirdContract {
    interface View extends BaseView {
        void EndUseResult(String change1);
        void ChangeStatus(String change1);
        void SeeMyState(MyState myState);

        void Touixiangsuccess(String uploadtouxiangmessage);

        void Touxiangfail(String failmessage);

        void JIESHUZANLISUCCESS(String change1);

        void FAILZANLI(String s);


    }

    interface  Presenter extends BasePresenter<View> {
        void enduse(String userid);
        void changestatus(String userid);
        void Seemystate(String userid);
        void UploadTouxiang(String filepath,String userid);
        void  changestatus2(String userid);

    }
}
