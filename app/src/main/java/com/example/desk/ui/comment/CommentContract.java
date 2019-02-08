package com.example.desk.ui.comment;

import android.content.Context;

import com.example.desk.entity.CommentDetailBean;
import com.example.desk.mvp.BasePresenter;
import com.example.desk.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CommentContract {
    interface View extends BaseView {

        void UpDataList(List<CommentDetailBean> commentDetailBeans);//请求成功更新列表

        void AddCommentSuccess(String userid, String content, String s);//评论成功

        void AddCommentFail();//评论失败

        void AddReplySuccess(String userid, String replyContent, int groupPosition);//回复某条评论成功

        void AddReplyFail();//回复某条评论失败
    }

    interface  Presenter extends BasePresenter<View> {
        void generateDate(String iduser_content);//根据动态id请求该说说下面的评论内容和回复内容
        void AddComment(String userid,String iduser_content,String content);//评论该内容
        void AddReply(String userid,String replyContent,String replyComId,int groupPosition);//回复某条评论
    }
}
