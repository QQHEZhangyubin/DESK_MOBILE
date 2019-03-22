package com.example.desk.ui.third;

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
    }

    interface  Presenter extends BasePresenter<View> {
        void enduse();
        void changestatus();
        void Seemystate();
    }
}
