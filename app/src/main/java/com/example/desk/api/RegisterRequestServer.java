package com.example.desk.api;

import com.example.desk.entity.Message;
import com.example.desk.entity.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterRequestServer {
    @POST("doRegister")
    @FormUrlEncoded
    Call<Message> getCall(@Field("i") String targetSentence);
    //采用@Post表示Post方法进行请求（传入部分url地址）
    // 采用@FormUrlEncoded注解的原因:API规定采用请求格式x-www-form-urlencoded,即表单形式
    // 需要配合@Field 向服务器提交需要的字段

    @POST("login")
    @FormUrlEncoded
    Call<User> getCall(@Field("username") String username,
                       @Field("password") String password);
}
