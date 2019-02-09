package com.example.desk.ui.first;

import com.example.desk.api.APIWrapper;
import com.example.desk.entity.Seat;
import com.example.desk.mvp.BasePresenterImpl;
import com.example.desk.util.TLog;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FirstPresenter extends BasePresenterImpl<FirstContract.View> implements FirstContract.Presenter{

    private List<Seat> seatList = new ArrayList<>();
    @Override
    public void getSeatData() {
        //TODO:网络请求服务端，返回每个自习室的id，可用座位，以及总座位
        mView.Error("请求服务端数据失败！");
        APIWrapper.getInstance().QuerySeatInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Seat>>() {
                    @Override
                    public void onCompleted() {
                        mView.Success(seatList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        TLog.error(e.toString());
                        mView.Error("请求不到数据");
                    }

                    @Override
                    public void onNext(List<Seat> seats) {
                        for (Seat seat : seats){
                            seatList.add(seat);
                        }
                    }
                });

    }
}
