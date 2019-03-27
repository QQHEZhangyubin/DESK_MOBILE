package com.example.desk.ui.comment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.bumptech.glide.Glide;
import com.example.desk.R;
import com.example.desk.adapter.CommentExpandAdapter;
import com.example.desk.adapter.MyPagerAdapter;
import com.example.desk.entity.CommentBean;
import com.example.desk.entity.CommentDetailBean;
import com.example.desk.entity.ReplyDetailBean;
import com.example.desk.mvp.MVPBaseActivity;
import com.example.desk.util.ShareUtils;
import com.example.desk.util.StaticClass;
import com.example.desk.util.TLog;
import com.example.desk.view.CommentExpandableListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CommentActivity extends MVPBaseActivity<CommentContract.View, CommentPresenter> implements CommentContract.View {


    @BindView(R.id.vp_pager)
    ViewPager vpPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.detail_page_userLogo)
    CircleImageView detailPageUserLogo;
    @BindView(R.id.detail_page_userName)
    TextView detailPageUserName;
    @BindView(R.id.detail_page_time)
    TextView detailPageTime;
    @BindView(R.id.detail_page_story)
    TextView detailPageStory;
    @BindView(R.id.detail_page_above_container)
    LinearLayout detailPageAboveContainer;
    @BindView(R.id.detail_page_lv_comment)
    CommentExpandableListView detailPageLvComment;
    @BindView(R.id.detail_page_comment_container)
    LinearLayout detailPageCommentContainer;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.detail_page_do_comment)
    TextView detailPageDoComment;
    private CommentBean commentBean;
    private List<CommentDetailBean> commentDetailBeanList = new ArrayList<>();
    private CommentExpandAdapter adapter;
    private BottomSheetDialog dialog;
    private String brower;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("详情");

        Intent intent = getIntent();
        brower = intent.getStringExtra("brower");//得到说说唯一标示
        String content = intent.getStringExtra("content");
        String picture = intent.getStringExtra("picture");
        String time = intent.getStringExtra("time");
        String username = intent.getStringExtra("username");
        ArrayList<String> Epicture = intent.getStringArrayListExtra("Epicture");
        TLog.log("picture = " + picture);
        Glide.with(this).load(picture).into(detailPageUserLogo);//设置头像
        detailPageUserName.setText(username);//设置学号
        detailPageTime.setText(time);//设置说说时间
        detailPageStory.setText(content);//设置说说内容
        vpPager.setAdapter(new MyPagerAdapter(Epicture, this));//设置适配器
        vpPager.setPageTransformer(true, new RotateUpTransformer());
        mPresenter.generateDate(brower);
    }

    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final List<CommentDetailBean> commentsList) {
        detailPageLvComment.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new CommentExpandAdapter(commentsList,this);
        detailPageLvComment.setAdapter(adapter);
        for (int i = 0; i < commentsList.size(); i++) {
            detailPageLvComment.expandGroup(i);
        }
        detailPageLvComment.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = detailPageLvComment.isGroupExpanded(groupPosition);
                TLog.log("onGroupClick:当前的评论id是》》" + commentDetailBeanList.get(groupPosition).getId());
                showReplyDialog(groupPosition);
                return true;
            }
        });
        detailPageLvComment.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //Toast.makeText(CommentActivity.this,"点击了回复",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        detailPageLvComment.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                TLog.log("展开第"+groupPosition+"个分组");
            }
        });
    }

    /**
     * by moos on 2018/04/20
     * func:弹出回复框
     */
    private void showReplyDialog(final int groupPosition) {
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        TLog.log("groupPosition与评论标识的关系：" + groupPosition );
        commentText.setHint("回复 " + commentDetailBeanList.get(groupPosition).getNickName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {
                    dialog.dismiss();
                    String userID = ShareUtils.getString(getApplicationContext(),StaticClass.userid,"**********");
                    mPresenter.AddReply(userID,replyContent,commentDetailBeanList.get(groupPosition).getId()+"",groupPosition);
                } else {
                    Toast.makeText(CommentActivity.this, "回复内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length() > 2) {
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                } else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.detail_page_do_comment)
    public void onClick() {
        showCommentDialog();
    }

    /**
     * by moos on 2018/04/20
     * func:弹出评论框
     */
    private void showCommentDialog() {
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0, 0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(commentContent)) {

                    //commentOnWork(commentContent);
                    dialog.dismiss();
                    String userId = ShareUtils.getString(getApplicationContext(),StaticClass.userid,"**********");
                    mPresenter.AddComment(userId,brower,commentContent);

                } else {
                    Toast.makeText(CommentActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 2) {
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                } else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();

    }

    @Override
    public void UpDataList(List<CommentDetailBean> commentDetailBeans) {
        commentDetailBeanList = commentDetailBeans;
        if (commentDetailBeanList != null && commentDetailBeanList.size() > 0){
            initExpandableListView(commentDetailBeanList);
        }
    }

    @Override
    public void AddCommentSuccess(String userid, String content, String s) {
        //评论成功
        mPresenter.generateDate(brower);
       /*
        CommentDetailBean detailBean = new CommentDetailBean(userid, content, s);
        if (adapter == null){
            TLog.error("adapter == null");
            List<CommentDetailBean> commentDetailBeanList1 = new ArrayList<>();
            commentDetailBeanList1.add(detailBean);
            initExpandableListView(commentDetailBeanList1);
        }else {
            adapter.addTheCommentData(detailBean);
        }
       */
        Toast.makeText(CommentActivity.this,"评论成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void AddCommentFail() {
        //评论失败
        Toast.makeText(CommentActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void AddReplySuccess(String userid, String replyContent, int groupPosition) {
        //回复成功
        ReplyDetailBean detailBean = new ReplyDetailBean(userid, replyContent);
        adapter.addTheReplyData(detailBean, groupPosition);
        detailPageLvComment.expandGroup(groupPosition);
        Toast.makeText(CommentActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void AddReplyFail() {
        //回复失败
        Toast.makeText(CommentActivity.this, "回复失败", Toast.LENGTH_SHORT).show();
    }
}
