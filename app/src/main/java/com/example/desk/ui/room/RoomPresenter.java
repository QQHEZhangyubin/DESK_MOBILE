package com.example.desk.ui.room;

import android.content.Context;

import com.example.desk.entity.Desk;
import com.example.desk.mvp.BasePresenterImpl;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RoomPresenter extends BasePresenterImpl<RoomContract.View> implements RoomContract.Presenter{

    @Override
    public List<Desk> getDataTwo(String roomid) {
        //TODO:根据roomid获得该自习室所有座位情况
        return null;
    }

    @Override
    public void getDataThree(Desk desk) {
        //TODO:根据点击的具体座位查看座位状态，再决定是否可以抢座
        mView.ErrorOne("这个位置的主人现在离开了一会，不怕被发现的话，可以暂时坐会！");
        mView.ErrorTwo("当前座位正在被他人使用，换个位置试试吧！");
        mView.Success(desk);
    }

    @Override
    public void QiangZuo(String rqcode, Desk desk) {
        if (rqcode.equals("")){
            //TODO:根据点击的具体座位抢座
            mView.Success2("座位已抢到手，赶紧开始繁忙的学习生活吧！");
        }else {
            mView.ErrorThree("扫描错误的二维码！");
        }
    }
}
