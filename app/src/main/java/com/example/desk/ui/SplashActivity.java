package com.example.desk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.desk.R;
import com.example.desk.ui.login.LoginActivity;
import com.example.desk.util.StaticClass;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
    @BindView(R.id.splashh)
    ImageView splashh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        Glide.with(SplashActivity.this)
                .load("")
                .asGif()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.mipmap.splash_default)
                .into(splashh);
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);
    }


}
