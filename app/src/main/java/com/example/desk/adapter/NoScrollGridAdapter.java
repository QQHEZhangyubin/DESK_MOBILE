package com.example.desk.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.desk.R;
import com.example.desk.util.TLog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class NoScrollGridAdapter extends BaseAdapter {
    /** 上下文 */
    private Context ctx;
    /** 图片Url集合 */
    private ArrayList<String> imageUrls;

    public NoScrollGridAdapter(Context ctx, ArrayList<String> imageUrls) {
        this.ctx = ctx;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        TLog.log("imgurl:" + imageUrls.size());
        return imageUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return imageUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(ctx, R.layout.item_gridview, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
       /*
        DisplayImageOptions options = new DisplayImageOptions.Builder()//
                .cacheInMemory(true)//
                .cacheOnDisk(true)//
                .bitmapConfig(Bitmap.Config.RGB_565)//
                .build();
        ImageLoader.getInstance().displayImage(imageUrls.get(position), imageView, options);
        */
        Glide.with(ctx).load(imageUrls.get(position))
                //.skipMemoryCache(false)
                .placeholder(R.mipmap.default_error)
                .error(R.mipmap.default_error)
                .into(imageView);
        return view;
    }

}
