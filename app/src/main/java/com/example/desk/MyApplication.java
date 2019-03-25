package com.example.desk;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;

public class MyApplication extends Application {
    private MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initImageloader();
    }

    private void initImageloader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_error)
                .showImageOnFail(R.drawable.default_error)
                .resetViewBeforeLoading(false) // default
                .delayBeforeLoading(0).cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .considerExifParams(true) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .displayer(new SimpleBitmapDisplayer()) // default
                .handler(new Handler()) // default
                .build();
        //DisplyImageOptions
        File picPath = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "MyApp"
                + File.separator + "files");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
        .memoryCacheExtraOptions(480,800)
                // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null).threadPoolSize(3)
                // default
                .threadPriority(Thread.NORM_PRIORITY - 1)
                // default
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                // default
                .denyCacheImageMultipleSizesInMemory().memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
                // default
                // default
                .diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(1000)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                // default
                .imageDownloader(new BaseImageDownloader(getApplicationContext())) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(options) // default
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);

    }
}
