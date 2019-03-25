package com.example.desk.ui.myinfo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.desk.R;
import com.example.desk.entity.U2;
import com.example.desk.entity.User;
import com.example.desk.mvp.MVPBaseActivity;
import com.example.desk.util.ShareUtils;
import com.example.desk.util.StaticClass;
import com.example.desk.util.TLog;
import com.example.desk.view.ItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MyinfoActivity extends MVPBaseActivity<MyinfoContract.View, MyinfoPresenter> implements MyinfoContract.View {
    @BindView(R.id.h_back)
    ImageView hBack;
    @BindView(R.id.h_head)
    ImageView hHead;
    @BindView(R.id.user_line)
    ImageView userLine;
    @BindView(R.id.user_uid)
    TextView userUid;
    @BindView(R.id.iv_college)
    ItemView ivCollege;
    @BindView(R.id.iv_sex)
    ItemView ivSex;
    @BindView(R.id.iv_class)
    ItemView ivClass;
    @BindView(R.id.iv_email)
    ItemView ivEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
        String userid = ShareUtils.getString(getApplicationContext(),StaticClass.userid,"");
        mPresenter.getU(userid);
    }

    @Override
    public void SetU22(U2 u2) {
        //设置背景磨砂效果
        TLog.error(u2.getUserlogo());
        Glide.with(this).load(u2.getUserlogo())
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(hBack);
        //设置圆形图像
        Glide.with(this).load(u2.getUserlogo())
                .bitmapTransform(new CropCircleTransformation(this))
                .into(hHead);
        ivCollege.setRightDesc(u2.getCollege());
        ivClass.setRightDesc(u2.getClasss());
        ivSex.setRightDesc(u2.getGender());
        ivEmail.setRightDesc(u2.getEmail());
        userUid.setText(u2.getUserid());
    }
}
