package com.example.desk.ui.room;

import com.example.desk.entity.Desk;
import com.example.desk.mvp.BasePresenter;
import com.example.desk.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RoomContract {
    interface View extends BaseView {
        void ErrorOne(String error);//当前座位正在被他人使用
        void ErrorTwo(String error);//当前座位处于暂离状态，不能被你使用
        void Success2(String info);//成功抢到该座位

        void Success3(List<Desk> deskList);


    }

    interface  Presenter extends BasePresenter<View> {
        void getDataTwo(String roomid);//根据roomid获得该自习室所有座位情况
        void QiangZuo(String rqcode, Desk tempdesk,String userid);//根据二维码data最后抢座位
    }
}
