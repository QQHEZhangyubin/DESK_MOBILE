package com.example.desk.ui.third;

import android.content.Context;

import com.example.desk.api.APIWrapper;
import com.example.desk.entity.MyState;
import com.example.desk.entity.Status;
import com.example.desk.entity.T3;
import com.example.desk.entity.T5;
import com.example.desk.mvp.BasePresenterImpl;
import com.example.desk.util.StaticClass;
import com.example.desk.util.TLog;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ThirdPresenter extends BasePresenterImpl<ThirdContract.View> implements ThirdContract.Presenter{

    private String change1;
    private MyState myState1;
    private String uploadtouxiangmessage;
    @Override
    public void enduse(String userid) {
        APIWrapper.getInstance().EndUse(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T5>() {
                    @Override
                    public void onCompleted() {
                        mView.EndUseResult(change1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        TLog.error(e.getMessage());
                        mView.EndUseResult("与服务器建立联系失败！");
                    }

                    @Override
                    public void onNext(T5 t5) {
                        change1 = t5.getChange1();
                    }
                });
    }

    @Override
    public void changestatus(String userid) {
        APIWrapper.getInstance().ChangeStatus(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T5>() {
                    @Override
                    public void onCompleted() {
                        mView.ChangeStatus(change1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.EndUseResult("与服务器建立联系失败！");
                    }

                    @Override
                    public void onNext(T5 t5) {
                        change1 = t5.getChange1();
                    }
                });
    }

    @Override
    public void Seemystate(String userid) {
        APIWrapper.getInstance().SeeMystate(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MyState>() {
                    @Override
                    public void onCompleted() {
                        mView.SeeMyState(myState1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.EndUseResult("与服务器建立联系失败！");
                    }

                    @Override
                    public void onNext(MyState myState) {
                        myState1 = myState;
                    }
                });
    }

    @Override
    public void UploadTouxiang(String filepath,String userid) {
        TLog.error("filepath == " + filepath);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File file = new File(filepath);
        RequestBody r = RequestBody.create(MediaType.parse("image/png"), file);
        builder.addFormDataPart(StaticClass.UploadTouxiang,file.getName(),r);
        MultipartBody.Part p = builder.build().part(0);
        APIWrapper.getInstance().uploadTouxiangImgs(p,userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Status>() {
                    @Override
                    public void onCompleted() {
                        TLog.error("成功");
                        mView.Touixiangsuccess(uploadtouxiangmessage);
                    }

                    @Override
                    public void onError(Throwable e) {
                        TLog.error(e.getMessage());
                        mView.Touxiangfail(uploadtouxiangmessage);
                    }

                    @Override
                    public void onNext(Status status) {
                        TLog.error("上传结果：：：" + status.getStatus());
                        uploadtouxiangmessage = status.getStatus();
                    }
                });
    }

    @Override
    public void changestatus2(String userid) {
        //恢复座位
        APIWrapper.getInstance().JieshuZanli(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T5>() {
                    @Override
                    public void onCompleted() {
                        mView.JIESHUZANLISUCCESS(change1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.FAILZANLI("与服务器建立联系失败！");
                    }

                    @Override
                    public void onNext(T5 t5) {
                        change1 = t5.getChange1();
                    }
                });
    }
}
