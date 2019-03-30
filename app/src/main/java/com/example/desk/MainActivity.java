package com.example.desk;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.desk.ui.first.FirstFragment;
import com.example.desk.ui.second.SecondFragment;
import com.example.desk.ui.third.ThirdFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,EasyPermissions.PermissionCallbacks {
    private static final int CAMERA_REQUEST_CODE = 89;
    @BindView(R.id.mFragment)
    FrameLayout mFragment;
    @BindView(R.id.mRb_home)
    RadioButton mRbHome;
    @BindView(R.id.mRb_message)
    RadioButton mRbMessage;
    @BindView(R.id.mRb_find)
    RadioButton mRbFind;
    @BindView(R.id.mRadioGroup)
    RadioGroup mRadioGroup;
    private List<Fragment> fragments = new ArrayList<>();
    private Fragment fragment;
    private FragmentManager fm;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRadioGroup.setOnCheckedChangeListener(this);
        fragments = getFragments(); //添加布局
        //添加默认布局
        normalFragment();
        //申请权限
        String[] pres = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(MainActivity.this,pres)){
        }else {
            // request for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
                    CAMERA_REQUEST_CODE, pres);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //默认布局
    private void normalFragment() {
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        fragment = fragments.get(0);
        transaction.replace(R.id.mFragment,fragment);
        transaction.commit();
    }
    private List<Fragment> getFragments() {
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFragment());
        return fragments;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        switch (checkedId){
            case R.id.mRb_home:
                fragment = fragments.get(0);
                transaction.replace(R.id.mFragment,fragment);
                break;
            case R.id.mRb_message:
                fragment = fragments.get(1);
                transaction.replace(R.id.mFragment,fragment);
                break;
            case R.id.mRb_find:
                fragment = fragments.get(2);
                transaction.replace(R.id.mFragment,fragment);
                break;
        }
        setTabState();
        transaction.commit();
    }
    //设置选中和未选择的状态
    private void setTabState() {
        //TODO:颜色要修改
        setHomeState();
        setMessageState();
        setFindState();
    }
    private void setFindState() {
        if (mRbFind.isChecked()){
            mRbFind.setTextColor(ContextCompat.getColor(this,R.color.colorRadioButtonP));
        }else {
            mRbFind.setTextColor(ContextCompat.getColor(this,R.color.colorRadioButtonN));
        }
    }
    private void setMessageState() {
        if (mRbMessage.isChecked()){
            mRbMessage.setTextColor(ContextCompat.getColor(this,R.color.colorRadioButtonP));
        }else {
            mRbMessage.setTextColor(ContextCompat.getColor(this,R.color.colorRadioButtonN));
        }
    }
    private void setHomeState() {
        if (mRbHome.isChecked()){
            mRbHome.setTextColor(ContextCompat.getColor(this,R.color.colorRadioButtonP));
        }else {
            mRbHome.setTextColor(ContextCompat.getColor(this,R.color.colorRadioButtonN));
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(MainActivity.this,"用户授权成功！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(MainActivity.this,"用户授权失败，如遇到不能打开相机情况请前往手机设置给予权限",Toast.LENGTH_SHORT).show();
    }
}
