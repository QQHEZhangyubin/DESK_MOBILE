package com.example.desk.ui.softupdate;

import android.content.Context;

import com.example.desk.api.APIWrapper;
import com.example.desk.entity.Soft2;
import com.example.desk.mvp.BasePresenterImpl;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SoftupdatePresenter extends BasePresenterImpl<SoftupdateContract.View> implements SoftupdateContract.Presenter{

    private Soft2 softT;
    @Override
    public void CheckUpdate() {
        APIWrapper.getInstance().CheckSoftWareUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Soft2>() {
                    @Override
                    public void onCompleted() {
                        mView.HadNewSoft(softT);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Soft2 soft2) {
                        softT = soft2;
                    }
                });
    }
}
