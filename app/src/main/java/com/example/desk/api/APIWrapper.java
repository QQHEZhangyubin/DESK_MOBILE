package com.example.desk.api;




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

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
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

    public Observable<T3> uploadTouxiangImgs(MultipartBody.Part file,String userid){
        return getmAPIService().uploadTouxiangImgs(file,userid);
    }
    public Observable<T5> JieshuZanli(String userid){
        return getmAPIService().JieshuZanli(userid);
    }
}
