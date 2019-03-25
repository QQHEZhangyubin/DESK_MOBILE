package com.example.desk.ui.myinfo;

import android.content.Context;

import com.example.desk.api.APIWrapper;
import com.example.desk.entity.U2;
import com.example.desk.entity.User;
import com.example.desk.mvp.BasePresenterImpl;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MyinfoPresenter extends BasePresenterImpl<MyinfoContract.View> implements MyinfoContract.Presenter{


   private U2 u22;
    @Override
    public void getU(String userid) {
        APIWrapper.getInstance().SeeMe(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<U2>() {
                    @Override
                    public void onCompleted() {
                        mView.SetU22(u22);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(U2 u2) {
                        u22 = u2;
                    }
                });

    }
}
