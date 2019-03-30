package com.example.desk.ui.pulldshuoshuo;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desk.MainActivity;
import com.example.desk.R;
import com.example.desk.adapter.GridViewAdapter;
import com.example.desk.mvp.MVPBaseActivity;
import com.example.desk.ui.login.LoginActivity;
import com.example.desk.util.FileUtils;
import com.example.desk.util.ImageTools;
import com.example.desk.util.NAlertDialog;
import com.example.desk.util.ShareUtils;
import com.example.desk.util.StaticClass;
import com.example.desk.util.TLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PulldshuoshuoActivity extends MVPBaseActivity<PulldshuoshuoContract.View, PulldshuoshuoPresenter> implements PulldshuoshuoContract.View, AdapterView.OnItemClickListener {

    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.tv_photo_path)
    TextView tvPhotoPath;
    @BindView(R.id.et_text)
    EditText etText;
    @BindView(R.id.noScrollgridview)
    GridView noScrollgridview;
    // 图片 九宫格适配器
    private GridViewAdapter gvAdapter;
    // 拍照
    public static final int IMAGE_CAPTURE = 1;
    // 从相册选择
    public static final int IMAGE_SELECT = 2;
    // 照片缩小比例
    private static final int SCALE = 5;
    // 用于保存图片资源文件
    private List<Bitmap> lists = new ArrayList<Bitmap>();
    // 用于保存图片路径
    private List<String> list_path = new ArrayList<String>();
    private NAlertDialog nAlertDialog;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*不允许横竖屏切换*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_pullshuoshuo);
        ButterKnife.bind(this);
        nAlertDialog = new NAlertDialog(this);
        gvAdapter = new GridViewAdapter(this, lists);
        noScrollgridview.setOnItemClickListener(this);
        noScrollgridview.setAdapter(gvAdapter);
        gvAdapter.setList(lists);
    }

    @Override
    public void FabiaoSuccess() {
        //发表说说成功
        progressDialog.dismiss();
        Toast.makeText(PulldshuoshuoActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
        ShareUtils.putBoolean(getApplicationContext(),"54",true);
        finish();
    }

    @Override
    public void FabiaoFail() {
        //发表说说失败
        progressDialog.dismiss();
        Toast.makeText(PulldshuoshuoActivity.this,"发布失败",Toast.LENGTH_SHORT).show();
        finish();
    }
    /**
     *
     * 从图库中选取图片
     *
     */

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, IMAGE_SELECT);
    }

    /**
     * 拍照照片存放的路径
     * @param
     */
    private void captureImage(String sdpath) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //缺少版本控制
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        if(Build.VERSION.SDK_INT < 24){
            TLog.error("android6");
            Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }else {
            TLog.error("android78");
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            //TLog.error("PROVIDER == "+ getActivity().getPackageName()+".fileprovider");
            Uri contentUri = FileProvider.getUriForFile(PulldshuoshuoActivity.this, PulldshuoshuoActivity.this.getPackageName()+".fileprovider", new File(Environment.getExternalStorageDirectory(), "image.jpg"));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            Log.e("getPicFromCamera", contentUri.toString());
        }

        startActivityForResult(intent, IMAGE_CAPTURE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && resultCode != RESULT_CANCELED) {
            String fileName;
            switch (requestCode) {
                case IMAGE_CAPTURE://拍照返回
                    // 将保存在本地的图片取出并缩小后显示在界面上
                    Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/image.jpg");
                    Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
                    // 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
                    bitmap.recycle();
                    // 生成一个图片文件名
                    fileName = String.valueOf(System.currentTimeMillis());
                    // 将处理过的图片添加到缩略图列表并保存到本地
                    ImageTools.savePhotoToSDCard(newBitmap, FileUtils.SDPATH, fileName);
                    lists.add(newBitmap);
                    list_path.add(FileUtils.SDPATH + fileName + ".jpg");
                    for (int i = 0; i < list_path.size(); i++) {
                        TLog.log("第" + i + "张照片的地址：" + list_path.get(i));
                    }
                    // 更新GrideView
                    gvAdapter.setList(lists);
                    break;
                case IMAGE_SELECT:// 选择照片返回
                    ContentResolver resolver = getContentResolver();
                    // 照片的原始资源地址
                    Uri originalUri = data.getData();
                    try {
                        // 使用ContentProvider通过URI获取原始图片
                        Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                        if (photo != null) {
                            // 为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                            Bitmap smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
                            // 释放原始图片占用的内存，防止out of memory异常发生
                            //photo.recycle();
                            // 生成一个图片文件名
                            fileName = String.valueOf(System.currentTimeMillis());
                            // 将处理过的图片添加到缩略图列表并保存到本地
                            //ImageTools.savePhotoToSDCard(smallBitmap, FileUtils.SDPATH, fileName);
                            ImageTools.savePhotoToSDCard(photo, FileUtils.SDPATH, fileName);
                            lists.add(smallBitmap);
                            list_path.add(FileUtils.SDPATH + fileName + ".jpg");
                            for (int i = 0; i < list_path.size(); i++) {
                                TLog.log("第" + i + "照片的地址：" + list_path.get(i));
                            }
                            // 更新GrideView
                            gvAdapter.setList(lists);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    break;

            }
        }
    }
    @OnClick(R.id.tv_send)
    public void onViewClicked() {
        String text = etText.getText().toString();
        if (text.isEmpty() && list_path.size() == 0){
            Toast.makeText(PulldshuoshuoActivity.this,"不能发布空信息",Toast.LENGTH_SHORT).show();
        }else {
            progressDialog = new ProgressDialog(PulldshuoshuoActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("发布中...");
            progressDialog.show();
            if (list_path.size() != 0){
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                for (int i = 0; i <list_path.size() ; i++) {
                    TLog.error(list_path.get(i));
                    File file = new File(list_path.get(i));
                    RequestBody phototRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
                    builder.addFormDataPart(StaticClass.FabiaoShuoShuo,file.getName(),phototRequestBody);
                }
                List<MultipartBody.Part> parts = builder.build().parts();
                String username = ShareUtils.getString(getApplicationContext(), StaticClass.userid, "");
                mPresenter.FabiaoShuoShuo(parts,text,username);
            }else {
                // Toast.makeText(PulldshuoshuoActivity.this,"please add some photos ",Toast.LENGTH_SHORT).show();
                String username = ShareUtils.getString(getApplicationContext(), StaticClass.userid, "");
                mPresenter.FabiaoShuoShuoT(text,username);
            }
        }
    }
    @Override
    protected void onDestroy() {
        //删除文件夹及文件
        FileUtils.deleteDir();
        super.onDestroy();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if (position == getDataSize()) {// 点击“+”号位置添加图片
            nAlertDialog.showAlertDialog(false, "提示", new String[]{"拍照", "从图库选择", "取消"},
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which + 1) {
                                case 1:// 拍照
                                    captureImage(FileUtils.SDPATH);
                                    dialog.dismiss();
                                    break;
                                case 2:// 从图库选择
                                    selectImage();
                                    dialog.dismiss();
                                    break;
                                case 3:// 取消
                                    dialog.dismiss();
                                    break;

                                default:
                                    break;
                            }
                        }
                    });
        } else {// 点击图片删除
            nAlertDialog.showAlertDialog("提示", "是否删除此图片？", "确定", "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    lists.remove(position);
                    FileUtils.delFile(list_path.get(position));
                    list_path.remove(position);
                    gvAdapter.setList(lists);
                    dialog.dismiss();
                }
            }, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
    }

    private int getDataSize() {
        return lists == null ? 0 : lists.size();
    }



}
