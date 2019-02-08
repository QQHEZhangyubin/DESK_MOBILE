package com.example.desk.ui.first;

import android.content.Context;

import com.example.desk.entity.Seat;
import com.example.desk.mvp.BasePresenterImpl;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FirstPresenter extends BasePresenterImpl<FirstContract.View> implements FirstContract.Presenter{

    @Override
    public List<Seat> getSeatData() {
        //TODO:网络请求服务端，返回每个自习室的id，可用座位，以及总座位
        mView.Error("请求服务端数据失败！");
        return null;
    }
}
