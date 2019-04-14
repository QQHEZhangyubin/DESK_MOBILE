package com.example.desk.ui.softupdate;


import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desk.R;
import com.example.desk.entity.Soft2;
import com.example.desk.mvp.MVPBaseActivity;
import com.example.desk.service.DownloadIntentService;
import com.example.desk.util.ShareUtils;
import com.example.desk.util.StaticClass;
import com.leon.lib.settingview.LSettingItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SoftupdateActivity extends MVPBaseActivity<SoftupdateContract.View, SoftupdatePresenter> implements SoftupdateContract.View {
    @BindView(R.id.tv_softversionname)
    TextView tvSoftversionname;
    @BindView(R.id.version_message)
    TextView versionMessage;
    private static final int DOWNLOADAPK_ID = 10;
    private String downloadurl;
    private int download_id;
    private String download_file;
    private AlertDialog alertDialog;
    private boolean isupdate = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.softupdate);
        ButterKnife.bind(this);
        LSettingItem lSettingItem = findViewById(R.id.ls_help1);
        lSettingItem.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                AlertDialog.Builder builder = new AlertDialog.Builder(SoftupdateActivity.this);
                builder.setTitle("关于软件");
                builder.setMessage(getResources().getString(R.string.tips));
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();

                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
        mPresenter.CheckUpdate();
    }

    @OnClick({R.id.tv_softversionname, R.id.version_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_softversionname:

                break;
            case R.id.version_message:
                if (isupdate){
                    if (isServiceRunning(DownloadIntentService.class.getName())) {
                        Toast.makeText(SoftupdateActivity.this, "正在下载,如无提示，请前往手机设置允许该应用提供通知", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(SoftupdateActivity.this, DownloadIntentService.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("download_url", downloadurl);
                    bundle.putInt("download_id", DOWNLOADAPK_ID);
                    bundle.putString("download_file", downloadurl.substring(downloadurl.lastIndexOf('/') + 1));
                    intent.putExtras(bundle);
                    startService(intent);
                }
                break;
        }
    }
    /**
     * 用来判断服务是否运行.
     *@param className 判断的服务名字
     *      * @return true 在运行 false 不在运行
     */
    private boolean isServiceRunning(String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) this
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;

            }
        }
        return isRunning;
    }

    @Override
    public void HadNewSoft(Soft2 softT) {
        int versionCode = softT.getVersioncode();//比较服务器得到的版本code与本地code
        downloadurl = softT.getVersionurl();
        download_id = softT.getVersioncode();
        try {
            int currentCode = getVersionNameCode();
            tvSoftversionname.setText(getVersionName());
            if (versionCode > currentCode){
                isupdate = true;
                versionMessage.setText("存在新版本,点我更新");
                versionMessage.setTextColor(Color.parseColor("#D81B60"));
            }
         } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
         }
    }

    //获取版本号
    private int getVersionNameCode() throws PackageManager.NameNotFoundException{
        PackageManager pm = getPackageManager();
        PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
        int vsesionCode = info.versionCode;
        return vsesionCode;
    }
    //获取版本号2
    private String getVersionName() throws PackageManager.NameNotFoundException{
        PackageManager pm = getPackageManager();
        PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
        String vsesionName = info.versionName;
        return vsesionName;
    }
}
