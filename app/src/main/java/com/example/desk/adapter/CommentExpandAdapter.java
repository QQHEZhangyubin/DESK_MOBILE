package com.example.desk.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.desk.R;
import com.example.desk.entity.CommentDetailBean;
import com.example.desk.entity.ReplyDetailBean;
import com.example.desk.util.TLog;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentExpandAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CommentExpandAdapter";
    private List<CommentDetailBean> commentDetailBeans;
    private List<ReplyDetailBean> replyDetailBeans;
    private Context context;
    private int pageIndex = 1;

    public CommentExpandAdapter(List<CommentDetailBean> commentDetailBeans, Context context) {
        this.commentDetailBeans = commentDetailBeans;
        this.context = context;
    }

    public CommentExpandAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        TLog.log(commentDetailBeans.size()+"");
        return commentDetailBeans.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (commentDetailBeans.get(groupPosition).getReplyDetailBeans() == null) {
            return 0;
        } else {
            return commentDetailBeans.get(groupPosition).getReplyDetailBeans().size() > 0 ? commentDetailBeans.get(groupPosition).getReplyDetailBeans().size() : 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return commentDetailBeans.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return commentDetailBeans.get(groupPosition).getReplyDetailBeans().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    boolean isLike = false;

    @Override
    public View getGroupView(int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupHolder groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item_layout, parent, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        Glide.with(context).load(commentDetailBeans.get(groupPosition).getUserLogo())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.drawable.user_logo)
                .centerCrop()
                .into(groupHolder.commentItemLogo);
        groupHolder.commentItemUserName.setText(commentDetailBeans.get(groupPosition).getNickName());
        groupHolder.commentItemTime.setText(commentDetailBeans.get(groupPosition).getCreateDate());
        groupHolder.commentItemContent.setText(commentDetailBeans.get(groupPosition).getContent());
        groupHolder.commentItemLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLike) {
                    isLike = false;
                    groupHolder.commentItemLike.setColorFilter(Color.parseColor("#aaaaaa"));
                } else {
                    isLike = true;
                    groupHolder.commentItemLike.setColorFilter(Color.parseColor("#FF5C5C"));
                }
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildHolder childHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_reply_item_layout, parent, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        }else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        String replyUser = commentDetailBeans.get(groupPosition).getReplyDetailBeans().get(childPosition).getNickName();
        if (!TextUtils.isEmpty(replyUser)){
            childHolder.replyItemUser.setText(replyUser + ":");
        }else {
            childHolder.replyItemUser.setText("无名" + ":");
        }
        childHolder.replyItemContent.setText(commentDetailBeans.get(groupPosition).getReplyDetailBeans().get(childPosition).getContent());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupHolder {
        @BindView(R.id.comment_item_logo)
        CircleImageView commentItemLogo;
        @BindView(R.id.comment_item_userName)
        TextView commentItemUserName;
        @BindView(R.id.comment_item_time)
        TextView commentItemTime;
        @BindView(R.id.comment_item_like)
        ImageView commentItemLike;
        @BindView(R.id.comment_item_content)
        TextView commentItemContent;

        GroupHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildHolder {
        @BindView(R.id.reply_item_user)
        TextView replyItemUser;
        @BindView(R.id.reply_item_content)
        TextView replyItemContent;

        ChildHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 评论成功插入一条数据
     * @param
     */
    public void addTheCommentData(CommentDetailBean commentDetailBean){
        if (commentDetailBean != null){
            commentDetailBeans.add(commentDetailBean);
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("评论数据为空!");
        }
    }

    /**
     * func:回复成功后插入一条数据
     * @param replyDetailBean
     * @param groupPosition
     */
    public void addTheReplyData(ReplyDetailBean replyDetailBean,int groupPosition){
        if (replyDetailBean != null){
            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:"+replyDetailBean.toString() );
            if (commentDetailBeans.get(groupPosition).getReplyDetailBeans() != null){
                commentDetailBeans.get(groupPosition).getReplyDetailBeans().add(replyDetailBean);
            }else {
                List<ReplyDetailBean> replyDetailBeanList = new ArrayList<>();
                replyDetailBeanList.add(replyDetailBean);
                commentDetailBeans.get(groupPosition).setReplyDetailBeans(replyDetailBeanList);
            }
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("回复数据为空！");
        }
    }

    //TODO:添加和展示所有回复
    private void addReplyList(List<ReplyDetailBean> replyDetailBeanList,int groupPosition){
        if (commentDetailBeans.get(groupPosition).getReplyDetailBeans() != null){
            commentDetailBeans.get(groupPosition).getReplyDetailBeans().clear();
            commentDetailBeans.get(groupPosition).getReplyDetailBeans().addAll(replyDetailBeanList);
        }else {
            commentDetailBeans.get(groupPosition).setReplyDetailBeans(replyDetailBeanList);
        }
        notifyDataSetChanged();
    }
}
