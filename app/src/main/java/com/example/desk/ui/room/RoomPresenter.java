package com.example.desk.ui.room;

import com.example.desk.api.APIWrapper;
import com.example.desk.entity.Desk;
import com.example.desk.entity.T4;
import com.example.desk.entity.User;
import com.example.desk.mvp.BasePresenterImpl;
import com.example.desk.util.TLog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String location = null;
        String classroom = null;
        deskList.clear();
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
        Matcher m = p.matcher( roomid );
        if (m.find()){
           location = m.group();
        }
        if (m.find()){
            classroom = m.group();
        }
        TLog.error("location = "+location + ", classroom = " +classroom);
        APIWrapper.getInstance().QueryEmptySeat(location,classroom)
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
                            TLog.error(desk.getClassroom());
                            deskList.add(desk);
                        }
                    }
                });
    }


    @Override
    public void QiangZuo(String rqcode, Desk tempdesk,String userid) {
        TLog.error("rqcord = =" + rqcode);
        APIWrapper.getInstance().ChooseSeat(tempdesk.getLocation(),tempdesk.getClassroom(),tempdesk.getSeatnumber()+"",userid,rqcode)
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
}
