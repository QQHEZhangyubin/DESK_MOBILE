package com.example.desk.ui.third;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.desk.R;
import com.example.desk.entity.MyState;
import com.example.desk.mvp.MVPBaseFragment;
import com.example.desk.ui.myinfo.MyinfoActivity;
import com.example.desk.util.ShareUtils;
import com.example.desk.util.StaticClass;
import com.example.desk.util.TLog;
import com.leon.lib.settingview.LSettingItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ThirdFragment extends MVPBaseFragment<ThirdContract.View, ThirdPresenter> implements ThirdContract.View, View.OnClickListener ,EasyPermissions.PermissionCallbacks{

    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.tv_xuehao2)
    TextView tvXuehao2;
    @BindView(R.id.item_three_myinfo)
    LSettingItem itemThreeMyinfo;
    @BindView(R.id.item_four_myinfo)
    LSettingItem itemFourMyinfo;
    Unbinder unbinder;

    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int CROP_SMALL_PICTURE = 3;

    //调用照相机返回图片文件
    private File tempFile;
    //最后显示的图片文件
    private  String mFile;


    public ThirdFragment() {
    }

    public static ThirdFragment newInstance() {
        ThirdFragment thirdFragment = new ThirdFragment();
        return thirdFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        unbinder = ButterKnife.bind(this, view);
        String m = ShareUtils.getString(getActivity().getApplicationContext(), StaticClass.userlogo, "0");
        TLog.error("图片地址：" + m);
        boolean fl = false;
        if (m.startsWith("http")){

            Glide.with(getActivity()).load(m).into(profileImage);
            fl = true;
        }
        if (m.equals("0")){

            Glide.with(getActivity()).load(R.drawable.user_logo).into(profileImage);
            fl = true;
        }
        if (!fl){

            Bitmap photo =BitmapFactory.decodeFile(m);
           // String tmpfile = mFile;
            profileImage.setImageBitmap(photo);
           // File filename = new File(m);
            //Glide.with(getActivity()).load(filename).into(profileImage);
        }
        String username =  ShareUtils.getString(getActivity().getApplicationContext(),StaticClass.userid,"*********");
        tvXuehao2.setText(username);
        LSettingItem threeAgain = view.findViewById(R.id.item_three_again_myinfo);
        threeAgain.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("恢复座位");
                progressDialog.setMessage("等待中...");
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.show();
                String userid = ShareUtils.getString(getActivity().getApplicationContext(), StaticClass.userid, "");
                mPresenter.changestatus2(userid);//向服务器发送数据，userid
            }
        });
        LSettingItem five = view.findViewById(R.id.item_five_myinfo);
        five.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                builder2.setTitle("软件说明");
                builder2.setMessage(getString(R.string.tips));
                builder2.setPositiveButton("版本更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setTitle("检查更新");
                        progressDialog.setMessage("可能需要...");
                        progressDialog.setIndeterminate(true);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        mPresenter.UpdateSoftWare(getActivity());//联网更新
                    }
                });
                alertDialog = builder2.create();
                alertDialog.show();
            }
        });
        LSettingItem first = view.findViewById(R.id.item_one_myinfo);
        first.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(getActivity(),MyinfoActivity.class));
            }
        });
        LSettingItem second = view.findViewById(R.id.item_two_myinfo);
        second.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                //当前状态
                String userid = ShareUtils.getString(getActivity().getApplicationContext(), StaticClass.userid, "");
                mPresenter.Seemystate(userid);
            }
        });
        LSettingItem third = view.findViewById(R.id.item_three_myinfo);
        third.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                //暂离
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setTitle("暂离座位");
                builder1.setMessage("确定暂离座位？");
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setTitle("暂离座位");
                        progressDialog.setMessage("等待中...");
                        progressDialog.setIndeterminate(true);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        String userid = ShareUtils.getString(getActivity().getApplicationContext(), StaticClass.userid, "");
                        mPresenter.changestatus(userid);//向服务器发送数据，userid
                    }
                });
                builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog = builder1.create();
                alertDialog.show();
            }
        });

        LSettingItem four = view.findViewById(R.id.item_four_myinfo);
        four.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                //结束
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("结束使用");
                builder.setMessage("确定结束使用？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setTitle("结束使用");
                        progressDialog.setMessage("等待中...");
                        progressDialog.setIndeterminate(true);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        String userid = ShareUtils.getString(getActivity().getApplicationContext(), StaticClass.userid, "");
                        mPresenter.enduse(userid);//向服务器发送数据，
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.profile_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profile_image:
                dialog = new Dialog(getActivity(),R.style.ActionSheetDialogStyle);
                View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.touxiang, null);
                Button choosePhoto = (Button) inflate.findViewById(R.id.choosePhoto);
                Button takePhoto = (Button) inflate.findViewById(R.id.takePhoto);
                Button cancel = (Button) inflate.findViewById(R.id.btn_cancel);
                choosePhoto.setOnClickListener(this);
                takePhoto.setOnClickListener(this);
                cancel.setOnClickListener(this);
                dialog.setContentView(inflate);

                Window dialogWindow = dialog.getWindow();
                dialogWindow.setGravity( Gravity.BOTTOM);
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
                dialogWindow.setAttributes(lp);

                dialog.show();
                break;
        }
    }

    @Override
    public void EndUseResult(String change1) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(),change1,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ChangeStatus(String change1) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(),change1,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void SeeMyState(MyState myState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("当前状态：" + myState.getStatus());
        builder.setMessage("校区:" + myState.getLocation() + "\n自习室:" + myState.getClassroom()+ "\n座位号:" + myState.getSeatnumber() +"\n开始时间:" +myState.getStarttime());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void Touixiangsuccess(String uploadtouxiangmessage) {
        Bitmap photo =BitmapFactory.decodeFile(mFile);
        String tmpfile = mFile;
        profileImage.setImageBitmap(photo);

        ShareUtils.deleShare(getActivity().getApplicationContext(),StaticClass.userlogo);
        ShareUtils.putString(getActivity().getApplicationContext(),StaticClass.userlogo,tmpfile);

        Toast.makeText(getActivity(),uploadtouxiangmessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Touxiangfail(String failmessage) {
        Toast.makeText(getActivity(),failmessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void JIESHUZANLISUCCESS(String change1) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(),change1,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void FAILZANLI(String s) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void UpDateSoftWareMessage(String updateinfo) {
        //progressDialog.dismiss();
        Toast.makeText(getActivity(),updateinfo,Toast.LENGTH_SHORT).show();
    }

    /**
     * 重写onRequestPermissionsResult，用于接受请求结果
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将请求结果传递EasyPermission库处理
        TLog.error("onRequestPermissionsResult:"+ requestCode);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.takePhoto:
                dialog.dismiss();
                if (EasyPermissions.hasPermissions(getActivity(),Manifest.permission.CAMERA)){
                    getPicFromCamera();//拍照
                }else {
                    // request for one permission
                    EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
                            CAMERA_REQUEST_CODE, Manifest.permission.CAMERA);
                }

                break;
            case R.id.choosePhoto:
                dialog.dismiss();
                getPicFromAlbm();//从系统相册选择
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
        }
    }

    private void getPicFromCamera() {
        //用于保存调用相机拍照后所生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".png");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            TLog.error("PROVIDER == "+ getActivity().getPackageName()+".fileprovider");
            Uri contentUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName()+".fileprovider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            Log.e("getPicFromCamera", contentUri.toString());
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }
    /**
     * 从相册获取图片
     */
    private void getPicFromAlbm() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ALBUM_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:   //调用相机后返回
                if (resultCode == RESULT_OK) {
                    //用相机返回的照片去调用剪裁也需要对Uri进行处理
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //intent.getData();
                        Uri contentUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName()+".fileprovider", tempFile);
                        startPhotoZoom(contentUri);//开始对图片进行裁剪处理
                        //startPhotoZoom(intent.getData());
                        TLog.error("777777777777777777777");
                    } else {
                        startPhotoZoom(Uri.fromFile(tempFile));//开始对图片进行裁剪处理
                    }
                }
                break;
            case ALBUM_REQUEST_CODE:    //调用相册后返回
                if (resultCode == RESULT_OK) {
                    if(intent == null){
                        TLog.error("intent为空");
                    }
                    Uri uri = intent.getData();
                    startPhotoZoom(uri); // 开始对图片进行裁剪处理
                }
                break;
            case CROP_SMALL_PICTURE:  //调用剪裁后返回
                if (intent != null) {
                    // 让刚才选择裁剪得到的图片显示在界面上
                    String userid = ShareUtils.getString(getActivity().getApplicationContext(),StaticClass.userid,"");
                    mPresenter.UploadTouxiang(mFile,userid);
                } else {
                    Log.e("data","data为空");
                    TLog.error("intent为空2");
                }
                break;
        }
    }

    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
//        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", false);
        File out = new File(getPath());
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
            TLog.error("执行了吗？");
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }
    //裁剪后的地址
    public  String getPath() {
        //resize image to thumb
        if (mFile == null) {
           TLog.error("*******************************************");
            //mFile = Environment.getExternalStorageDirectory() + "/" +"wode/"+ "outtemp.png";
            mFile = Environment.getExternalStorageDirectory() + "/" +"outtemp.png";
        }
        TLog.error("=================="+mFile+"=================");
        return mFile;
    }
    public String saveImage(String name, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        getPicFromCamera();//拍照
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(getActivity(),"用户授权失败",Toast.LENGTH_SHORT).show();
        /**
         * 若是在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限。
         * 这时候，需要跳转到设置界面去，让用户手动开启。
         */
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
