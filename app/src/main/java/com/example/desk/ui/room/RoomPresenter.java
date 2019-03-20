package com.example.desk.ui.room;

import com.example.desk.api.APIWrapper;
import com.example.desk.entity.Desk;
import com.example.desk.entity.T4;
import com.example.desk.entity.User;
import com.example.desk.mvp.BasePresenterImpl;
import com.example.desk.util.TLog;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RoomPresenter extends BasePresenterImpl<RoomContract.View> implements RoomContract.Presenter{
    private List<Desk> deskList = new ArrayList<>();
    private String detatil;
    @Override
    public void getDataTwo(String roomid) {
        //TODO:根据roomid获得该自习室所有座位情况
        APIWrapper.getInstance().QueryEmptySeat("","")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Desk>>() {
                    @Override
                    public void onCompleted() {
                        mView.Success3(deskList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        TLog.error(e.toString());
                        mView.ErrorOne("连接不到网络");
                    }

                    @Override
                    public void onNext(List<Desk> desks) {
                        for (Desk desk : desks){
                            deskList.add(desk);
                        }
                    }
                });
    }

    @Override
    public void getDataThree(Desk desk) {
        //TODO:根据点击的具体座位查看座位状态，再决定是否可以抢座
        APIWrapper.getInstance().ChooseSeat(desk.getLocation(),desk.getClassroom(),desk.getSeatnumber()+"",User.getInstance().getData().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T4>() {
                    @Override
                    public void onCompleted() {
                        if (detatil.equals("座位已抢到手，赶紧开始繁忙的学习生活吧！")){
                            mView.Success2(detatil);
                        }else {
                            mView.ErrorTwo(detatil);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        TLog.error(e.toString());
                        mView.ErrorTwo("连接网络失败");
                    }

                    @Override
                    public void onNext(T4 t4) {
                        detatil = t4.getDetail();
                    }
                });

    }

    @Override
    public void QiangZuo(String rqcode) {
        if (rqcode.equals("")){
            //TODO:根据点击的具体座位抢座
            mView.Success4("座位已抢到手，赶紧开始繁忙的学习生活吧！");//终于抢到位置了
        }else {
            mView.ErrorThree("扫描错误的二维码！");
        }
    }
}
