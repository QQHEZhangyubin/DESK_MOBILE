package com.example.desk.api;

import android.app.Application;

import com.example.desk.MyApplication;
import com.example.desk.callback.DownloadCallBack;
import com.example.desk.util.ShareUtils;
import com.example.desk.util.StaticClass;
import com.example.desk.util.TLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitUtil {
    private static final String API_HOST = "http://172.16.63.128:8080/desk/";
    private static Retrofit mRetrofit;
    private static APIService mAPIService;

    private static Retrofit getmRetrofit(){
        if (mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(API_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public static APIService getmAPIService(){
        if (mAPIService == null){
            mAPIService = getmRetrofit().create(APIService.class);
        }
        return mAPIService;
    }


}
