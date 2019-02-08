package com.example.desk.ui.third;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.desk.R;
import com.example.desk.mvp.MVPBaseFragment;
import com.example.desk.ui.myinfo.MyinfoActivity;
import com.example.desk.util.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ThirdFragment extends MVPBaseFragment<ThirdContract.View, ThirdPresenter> implements ThirdContract.View {
    public static final String URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547964247482&di=0c2b940409488695883a4b0fe7fac5ec&imgtype=0&src=http%3A%2F%2Fwww.veeqi.com%2Fuploadfile%2F2018%2F0409%2F20180409031427895.jpg";
    @BindView(R.id.header_view)
    PerfectArcView headerView;
    Unbinder unbinder;
    @BindView(R.id.lv_1)
    LinearLayout lv1;
    @BindView(R.id.lv_2)
    LinearLayout lv2;
    @BindView(R.id.lv_3)
    LinearLayout lv3;
    private PerfectArcView mArcHeaderView;

    public ThirdFragment() {
    }

    public static ThirdFragment newInstance() {
        ThirdFragment thirdFragment = new ThirdFragment();
        return thirdFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StatusBarUtils.setColor(getActivity(), getResources().getColor(R.color.start_color), 0);
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        mArcHeaderView.setImageUrl(URL);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.lv_1, R.id.lv_2, R.id.lv_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lv_1:
                //进入我的信息
                startActivity(new Intent(getContext(),MyinfoActivity.class));
                break;
            case R.id.lv_2:
                //进入使用记录
                break;
            case R.id.lv_3:
                //进入软件信息
                break;
        }
    }
}
