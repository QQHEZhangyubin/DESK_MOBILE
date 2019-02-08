package com.example.desk.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.desk.R;

import java.util.ArrayList;

public class MyPagerAdapter extends PagerAdapter {
    private ArrayList<String> imgs;
    private Context mContext;

    public MyPagerAdapter(ArrayList<String> imgs, Context mContext) {
        this.imgs = imgs;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.page_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_page_item_img);
        Glide.with(mContext).load(imgs.get(position)).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
