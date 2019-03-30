package com.example.desk.api;




import com.example.desk.MyApplication;
import com.example.desk.callback.DownloadCallBack;
import com.example.desk.entity.CommentBean;
import com.example.desk.entity.Desk;
import com.example.desk.entity.MyState;
import com.example.desk.entity.Seat;
import com.example.desk.entity.ShuoShuo;
import com.example.desk.entity.Status;
import com.example.desk.entity.T1;
import com.example.desk.entity.T2;
import com.example.desk.entity.T3;
import com.example.desk.entity.T4;
import com.example.desk.entity.T5;
import com.example.desk.entity.U2;
import com.example.desk.entity.User;
import com.example.desk.util.ShareUtils;
import com.example.desk.util.StaticClass;
import com.example.desk.util.TLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class APIWrapper extends RetrofitUtil {
    private static APIWrapper mAPIWrapper;

    public APIWrapper() {
    }

    public static APIWrapper getInstance(){
        if (mAPIWrapper == null){
            mAPIWrapper = new APIWrapper();
        }
        return mAPIWrapper;
    }

    public Observable<ArrayList<ShuoShuo>> RequestData(){
        return getmAPIService().RequestData();
    }

    public Observable<CommentBean> RequestData2(String iduser_content){
        return getmAPIService().RequestData2(iduser_content);
    }

    public Observable<T1> AddComment(String userid, String iduser_content, String content){
        return getmAPIService().AddComment(userid,iduser_content,content);
    }
    public Observable<T2> AddReply(String userid, String replyContent, String replyComId){
        return getmAPIService().AddReply(userid,replyContent,replyComId);
    }
    public Observable<Status> uploadImgs(List<MultipartBody.Part> file, String data, String userid){
        return getmAPIService().uploadImgs(file,data,userid);
    }

    public Observable<Status> uploadImgsT(String data,String userid){
        return getmAPIService().uploadImgsT(data,userid);
    }

    public Observable<T3> registeruser(String userid,String password,String college,String classs,String birthday,String email,String gender){
        return getmAPIService().registeruser(userid,password,college,classs,birthday,email,gender);
    }

    public Observable<User> loginuser(String userid,String password){
        return getmAPIService().loginuser(userid,password);
    }

    public Observable<List<Seat>> QuerySeatInfo(){
        return getmAPIService().QuerySeatInfo();
    }

    public Observable<List<Desk>> QueryEmptySeat(String location,String classroom){
        return getmAPIService().QueryEmptySeat(location, classroom);
    }

    public Observable<T4> ChooseSeat(String location,String classroom,String seatnumber,String userid,String rqcord){
        return getmAPIService().ChooseSeat(location, classroom, seatnumber, userid,rqcord);
    }

    public Observable<T5> EndUse(String userid){
        return getmAPIService().EndUse(userid);
    }
    public Observable<T5> ChangeStatus(String userid){
        return getmAPIService().ChangeStatus(userid);
    }

    public Observable<MyState> SeeMystate(String userid){
        return getmAPIService().SeeMystate(userid);
    }

    public Observable<U2> SeeMe(String userid){
        return getmAPIService().SeeMe(userid);
    }

    public Observable<Status> uploadTouxiangImgs(MultipartBody.Part file,String userid){
        return getmAPIService().uploadTouxiangImgs(file,userid);
    }
    public Observable<T5> JieshuZanli(String userid){
        return getmAPIService().JieshuZanli(userid);
    }

    public Observable<ResponseBody> executeDownload(String range ,String url){
        return getmAPIService().executeDownload(range,url);
    }


    public void downloadFile(final long range, final String url, final String fileName, final DownloadCallBack downloadCallback){
        //断点续传时请求的总长度
        File file = new File(StaticClass.APP_ROOT_PATH + StaticClass.DOWNLOAD_DIR, fileName);
        String totalLength = "-";
        if (file.exists()){
            totalLength += file.length();
        }
        APIWrapper.getInstance().executeDownload("bytes=" + Long.toString(range) + totalLength, url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        downloadCallback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        RandomAccessFile randomAccessFile = null;
                        InputStream inputStream = null;
                        long total = range;
                        long responseLength = 0;
                        try{
                            byte[] buf = new byte[2048];
                            int len = 0;
                            responseLength = responseBody.contentLength();
                            inputStream = responseBody.byteStream();
                            String filePath = StaticClass.APP_ROOT_PATH + StaticClass.DOWNLOAD_DIR;
                            File file = new File(filePath,fileName);
                            File dir = new File(filePath);
                            if (!dir.exists()){
                                dir.mkdirs();
                            }
                            try {
                                randomAccessFile = new RandomAccessFile(file,"rwd");
                                if (range == 0){
                                    try {
                                        randomAccessFile.setLength(responseLength);
                                        randomAccessFile.seek(range);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (FileNotFoundException e) {

                            }
                            int progress = 0;
                            int lastProgress = 0;
                            while ((len = inputStream.read(buf)) != -1){
                                randomAccessFile.write(buf,0,len);
                                total += len;
                                ShareUtils.save(MyApplication.getInstance().getApplicationContext(),url,total);
                                lastProgress = progress;
                                progress = (int) (total *100 / randomAccessFile.length());
                                if (progress > 0 && progress != lastProgress){
                                    downloadCallback.onProgress(progress);
                                }
                            }
                            downloadCallback.onCompleted();
                        } catch (IOException e) {
                            TLog.error(e.getMessage());
                            downloadCallback.onError(e.getMessage());
                            e.printStackTrace();
                        }finally {
                            try{
                                if (randomAccessFile != null){
                                    randomAccessFile.close();
                                }
                                if (inputStream != null){
                                    inputStream.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}
