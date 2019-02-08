package com.example.desk.ui.comment;

import android.content.Context;

import com.example.desk.api.APIWrapper;
import com.example.desk.entity.CommentBean;
import com.example.desk.entity.CommentDetailBean;
import com.example.desk.entity.T1;
import com.example.desk.entity.T2;
import com.example.desk.mvp.BasePresenterImpl;
import com.example.desk.util.TLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CommentPresenter extends BasePresenterImpl<CommentContract.View> implements CommentContract.Presenter{
    private List<CommentDetailBean> commentDetailBeans = new ArrayList<>();
    private List<CommentDetailBean> commentDetailBeans2 = new ArrayList<>();
    @Override
    public void generateDate(String iduser_content) {
        APIWrapper.getInstance().RequestData2(iduser_content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommentBean>() {
                    @Override
                    public void onCompleted() {
                        TLog.log("AAAAAAA");
                        commentDetailBeans = commentDetailBeans2;
                        mView.UpDataList(commentDetailBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        TLog.log(e.toString());
                    }

                    @Override
                    public void onNext(CommentBean commentBean) {
                        TLog.log("::::"+commentBean.getData().getList().toString());
                        commentDetailBeans2 = commentBean.getData().getList();
                    }
                });

    }

    @Override
    public void AddComment(final String userid, String iduser_content, final String content) {
        APIWrapper.getInstance().AddComment(userid,iduser_content,content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T1>() {
                    @Override
                    public void onCompleted() {
                        mView.AddCommentSuccess(userid,content,new Date()+"");
                    }

                    @Override
                    public void onError(Throwable e) {
                        TLog.error(e.toString());
                        mView.AddCommentFail();
                    }

                    @Override
                    public void onNext(T1 t1) {
                    }
                });
    }

    @Override
    public void AddReply(final String userid, final String replyContent, String replyComId, final int groupPosition) {
        APIWrapper.getInstance().AddReply(userid,replyContent,replyComId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T2>() {
                    @Override
                    public void onCompleted() {
                        mView.AddReplySuccess(userid,replyContent,groupPosition);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.AddReplyFail();
                        TLog.log(e.toString());
                    }

                    @Override
                    public void onNext(T2 t2) {

                    }
                });
    }
}
