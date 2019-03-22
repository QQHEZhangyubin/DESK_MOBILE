package com.example.desk.ui.third;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desk.R;
import com.example.desk.entity.MyState;
import com.example.desk.mvp.MVPBaseFragment;
import com.leon.lib.settingview.LSettingItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ThirdFragment extends MVPBaseFragment<ThirdContract.View, ThirdPresenter> implements ThirdContract.View {

    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.tv_xuehao2)
    TextView tvXuehao2;
    @BindView(R.id.item_one_myinfo)
    LSettingItem itemOneMyinfo;
    @BindView(R.id.item_two_myinfo)
    LSettingItem itemTwoMyinfo;
    @BindView(R.id.item_three_myinfo)
    LSettingItem itemThreeMyinfo;
    @BindView(R.id.item_four_myinfo)
    LSettingItem itemFourMyinfo;
    @BindView(R.id.item_five_myinfo)
    LSettingItem itemFiveMyinfo;
    Unbinder unbinder;

    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    public ThirdFragment() {
    }

    public static ThirdFragment newInstance() {
        ThirdFragment thirdFragment = new ThirdFragment();
        return thirdFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_third, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick({R.id.profile_image, R.id.item_one_myinfo, R.id.item_two_myinfo, R.id.item_three_myinfo, R.id.item_four_myinfo, R.id.item_five_myinfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profile_image:
                break;
            case R.id.item_one_myinfo:
                break;
            case R.id.item_two_myinfo:
                //当前状态
                mPresenter.Seemystate();
                break;
            case R.id.item_three_myinfo:
                //暂离
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setTitle("暂离座位");
                builder1.setMessage("确定暂离座位？");
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setTitle("暂离座位");
                        progressDialog.setMessage("等待中...");
                        progressDialog.setIndeterminate(true);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        mPresenter.changestatus();//向服务器发送数据，location，classroom，seatnumber
                    }
                });
                builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog = builder1.create();
                alertDialog.show();
                break;
            case R.id.item_four_myinfo:
                //结束
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("结束使用");
                builder.setMessage("确定结束使用？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setTitle("结束使用");
                        progressDialog.setMessage("等待中...");
                        progressDialog.setIndeterminate(true);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        mPresenter.enduse();//向服务器发送数据，location，classroom，seatnumber
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.item_five_myinfo:
                break;
        }
    }

    @Override
    public void EndUseResult(String change1) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(),change1,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ChangeStatus(String change1) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(),change1,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void SeeMyState(MyState myState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("当前状态");
        builder.setMessage("location:" + myState.getLocation() + "classroom:" + myState.getClassroom() + "seatnumber:" + myState.getSeatnumber() + "starttime:" +myState.getStarttime());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
}
