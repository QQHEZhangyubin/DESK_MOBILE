package com.example.desk.util;

import android.os.Environment;

import com.example.desk.MyApplication;

/**
 * Created by 13608 on 2018/5/16.
 */

public class StaticClass {
   public static final String userid = "userid";
   public static final String userlogo = "userlogo";
   public static final String password = "password";
   public static final int HANDLER_SPLASH = 1001;
   public static final String UploadTouxiang = "touxiangfile";
   public static final String FabiaoShuoShuo = "file2";
   public static final String Keeppass = "keeppass";

   public final static String APP_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +  MyApplication.getInstance().getPackageName();
   public final static String DOWNLOAD_DIR = "/downlaod/";
   public static final int DOWNLOADAPK_ID = 10;
}
