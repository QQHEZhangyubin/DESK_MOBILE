package com.example.desk;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
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
}
