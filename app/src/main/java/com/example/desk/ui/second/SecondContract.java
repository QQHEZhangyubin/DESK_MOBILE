package com.example.desk.ui.second;

import com.example.desk.entity.ShuoShuo;
import com.example.desk.mvp.BasePresenter;
import com.example.desk.mvp.BaseView;

import java.util.ArrayList;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SecondContract {
    interface View extends BaseView {
        void Fail1();//从服务器拉取不到说说内容
        void Success1(ArrayList<ShuoShuo> shuoShuoList);//从服务器拉取成功拉取说说内容
    }

    interface  Presenter extends BasePresenter<View> {
        void initData2();//请求网络数据说说内容
    }
}
