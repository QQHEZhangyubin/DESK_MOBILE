package com.example.desk.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.widget.RemoteViews;

import com.example.desk.MyApplication;
import com.example.desk.R;
import com.example.desk.api.APIWrapper;
import com.example.desk.api.RetrofitUtil;
import com.example.desk.callback.DownloadCallBack;
import com.example.desk.util.ShareUtils;
import com.example.desk.util.StaticClass;
import com.example.desk.util.TLog;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.File;


public class DownloadIntentService extends IntentService {

    private NotificationManager mNotificationManager;
    private String mDownloadFileName;
    private Notification mNotification;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param
     */
    public DownloadIntentService() {
        super("InitializeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String downloadUrl = intent.getExtras().getString("download_url");
        final int downloadId = intent.getExtras().getInt("download_id");
        mDownloadFileName =  intent.getExtras().getString("download_file");
           TLog.log(downloadUrl);
           TLog.log(mDownloadFileName);
          final File file = new File(StaticClass.APP_ROOT_PATH + StaticClass.DOWNLOAD_DIR + mDownloadFileName);
          long range = 0;
          int progress = 0;
          if (file.exists()){
              range = ShareUtils.get(MyApplication.getInstance().getApplicationContext(),downloadUrl, (long) 0);
              progress = (int) (range * 100 / file.length());
              if (range == file.length()){
                  installApp(file);
                  return;
              }
          }

        TLog.log("range = "+range);
        final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify_download);
        remoteViews.setProgressBar(R.id.pb_progress,100,progress,false);
        remoteViews.setTextViewText(R.id.tv_progress,"已下载" + progress + "%");
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            TLog.log("i am android 8");
            NotificationChannel mChannel = new NotificationChannel("chan", mDownloadFileName, NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(mChannel);
            NotificationCompat.Builder builderr = new NotificationCompat.Builder(this, "chan")
                    .setChannelId("chan")
                    .setContent(remoteViews)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setOngoing(true)
                    .setTicker("正在下载")
                    .setSmallIcon(R.mipmap.ic_launcher);
            mNotification = builderr.build();
        }else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"chan")
                    .setContent(remoteViews)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setOngoing(true)
                    .setTicker("正在下载")
                    .setSmallIcon(R.mipmap.ic_launcher);
            mNotification = builder.build();
        }
        mNotificationManager.notify(downloadId,mNotification);
        APIWrapper.getInstance().downloadFile(range, downloadUrl, mDownloadFileName, new DownloadCallBack() {
            @Override
            public void onProgress(int progress) {
                TLog.log("已下载: " + progress);
                remoteViews.setProgressBar(R.id.pb_progress,100,progress,false);
                remoteViews.setTextViewText(R.id.tv_progress, "已下载" + progress + "%");
                mNotificationManager.notify(downloadId,mNotification);
            }

            @Override
            public void onCompleted() {
                TLog.log("下载完成");
                mNotificationManager.cancel(downloadId);
                installApp(file);
            }

            @Override
            public void onError(String msg) {
                mNotificationManager.cancel(downloadId);
                TLog.log("下载发生错误--" + msg);
            }
        });

    }

    private void installApp(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           data = Uri.fromFile(file);
        }else {
            intent.setAction(Intent.ACTION_INSTALL_PACKAGE);
            //清单文件中配置的authorities
            data = FileProvider.getUriForFile(this, "com.example.desk.fileprovider", file);
            // 给目标应用一个临时授权
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//重点！！
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        startActivity(intent);
    }
}
