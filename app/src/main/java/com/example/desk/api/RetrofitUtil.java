package com.example.desk.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private static final String API_HOST = "http://192.168.43.230:8080/desk_web_war/";
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
