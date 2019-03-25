package com.example.desk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.desk.R;
import com.example.desk.entity.Seat;
import com.example.desk.util.TLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder> {


    public interface OneOnPlayClickListener {
        void Seat_click(String roomid);//选择点击房间，进入下一个活动
    }

    private Context mContext;
    private List<Seat> seats;
    OneOnPlayClickListener oneOnPlayClickListener;

    public void setOneOnPlayClickListener(OneOnPlayClickListener oneOnPlayClickListener){
        this.oneOnPlayClickListener = oneOnPlayClickListener;
    }

    public SeatAdapter(Context mContext, List<Seat> seats) {
        this.mContext = mContext;
        this.seats = seats;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_first_fragment, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.ivlv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = holder.getAdapterPosition();
                Seat seat = seats.get(p);
                if (oneOnPlayClickListener != null){
                    oneOnPlayClickListener.Seat_click(seat.getRoomid());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Seat seat = seats.get(position);
        holder.seatAvaibleAccount.setText("可用座位："+seat.getAvail_seat());
        holder.seatTotalAccount.setText("位置总数："+seat.getTotal_seat());
        holder.roomId.setText(seat.getRoomid());
    }


    @Override
    public int getItemCount() {
        return seats.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.seat_avaible_account)
        TextView seatAvaibleAccount;
        @BindView(R.id.seat_total_account)
        TextView seatTotalAccount;
        @BindView(R.id.room_id)
        TextView roomId;
        @BindView(R.id.iv_iv)
        ImageView ivlv;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
