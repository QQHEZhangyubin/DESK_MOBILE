package com.example.desk.ui.second;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.desk.R;
import com.example.desk.adapter.RecyclerItemAdapter;
import com.example.desk.entity.ShuoShuo;
import com.example.desk.mvp.MVPBaseFragment;
import com.example.desk.ui.comment.CommentActivity;
import com.example.desk.ui.pulldshuoshuo.PulldshuoshuoActivity;
import com.example.desk.util.ShareUtils;
import com.example.desk.util.TLog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SecondFragment extends MVPBaseFragment<SecondContract.View, SecondPresenter> implements SecondContract.View {

    @BindView(R.id.listview)
    RecyclerView recyclerview;
    Unbinder unbinder;
    @BindView(R.id.ib_c)
    ImageButton ibC;


    public SecondFragment() {
    }

    public static SecondFragment newInstance() {
        SecondFragment secondFragment = new SecondFragment();
        return secondFragment;
    }

    @Override
    public void onPause() {
        TLog.error("onPause");
        super.onPause();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        unbinder = ButterKnife.bind(this, view);
        TLog.error("onCreateView");
        mPresenter.initData2();//请求网络数据
        return view;
    }

    @Override
    public void onStart() {
        TLog.error("onStart");
        boolean g = ShareUtils.getBoolean(getActivity().getApplicationContext(), "54", false);
        if (g){
            ShareUtils.deleShare(getActivity().getApplicationContext(),"54");
            mPresenter.initData2();
        }
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        TLog.error("onDestroyView");
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void Fail1() {
        //从服务器拉取不到说说内容
        Toast.makeText(getContext(), "拉取不到说说内容", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void Success1(ArrayList<ShuoShuo> shuoShuoList) {
        //从服务器拉取成功拉取说说内容
        RecyclerItemAdapter.OnPlayClickListener onPlayClickListener = new RecyclerItemAdapter.OnPlayClickListener() {
            @Override
            public void onItemClick(ShuoShuo shuo) {
                Intent intent = new Intent(getActivity(), CommentActivity.class);
                intent.putExtra("brower", shuo.getBrowser());//说说id
                intent.putExtra("content", shuo.getContent());//说说内容
                intent.putExtra("picture", shuo.getPicture());//用户头像
                intent.putExtra("time", shuo.getTime());//说说发布时间
                intent.putExtra("username", shuo.getUserName());//学号
                intent.putStringArrayListExtra("Epicture", shuo.getEpicture());//九宫格图片
                startActivity(intent);
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        RecyclerItemAdapter adapter = new RecyclerItemAdapter(getActivity(), shuoShuoList);
        adapter.setOnplayClickListener(onPlayClickListener);
        recyclerview.setAdapter(adapter);
    }

    @OnClick(R.id.ib_c)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(),PulldshuoshuoActivity.class);
        startActivity(intent);
    }
}
