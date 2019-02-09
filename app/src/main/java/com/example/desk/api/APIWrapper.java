package com.example.desk.api;




import com.example.desk.entity.CommentBean;
import com.example.desk.entity.ShuoShuo;
import com.example.desk.entity.T1;
import com.example.desk.entity.T2;
import com.example.desk.entity.T3;
import com.example.desk.entity.User;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;

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
    public Observable<String> uploadImgs(List<MultipartBody.Part> file, String data){
        return getmAPIService().uploadImgs(file,data);
    }

    public Observable<T3> registeruser(String userid,String password,String college,String classs,String birthday,String email,String gender){
        return getmAPIService().registeruser(userid,password,college,classs,birthday,email,gender);
    }

    public Observable<User> loginuser(String userid,String password){
        return getmAPIService().loginuser(userid,password);
    }
}
