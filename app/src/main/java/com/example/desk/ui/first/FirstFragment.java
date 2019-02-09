package com.example.desk.ui.first;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.desk.R;
import com.example.desk.adapter.SeatAdapter;
import com.example.desk.entity.Seat;
import com.example.desk.mvp.MVPBaseFragment;
import com.example.desk.ui.room.RoomActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FirstFragment extends MVPBaseFragment<FirstContract.View, FirstPresenter> implements FirstContract.View{
    @BindView(R.id.rv_one)
    RecyclerView rvOne;
    Unbinder unbinder;
    public FirstFragment() {
    }

    //单例模式
    public static FirstFragment newInstance() {
        FirstFragment firstFragment = new FirstFragment();
        return firstFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        unbinder = ButterKnife.bind(this, view);
        mPresenter.getSeatData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //请求网络数据失败
    @Override
    public void Error(String error) {
        Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void Success(List<Seat> seatList) {
        //请求数据成功
        SeatAdapter.OneOnPlayClickListener oneOnPlayClickListener = new SeatAdapter.OneOnPlayClickListener() {
            @Override
            public void Seat_click(String roomid) {
                Intent intent = new Intent(getActivity(),RoomActivity.class);
                intent.putExtra("roomid",roomid);
                startActivity(intent);
            }
        };
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rvOne.setLayoutManager(layoutManager);
        SeatAdapter adapter = new SeatAdapter(getContext(), seatList);
        adapter.setOneOnPlayClickListener(oneOnPlayClickListener);
        rvOne.setAdapter(adapter);
    }
}
