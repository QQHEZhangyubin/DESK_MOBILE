package com.example.desk.ui.pulldshuoshuo;

import android.content.Context;

import com.example.desk.mvp.BasePresenter;
import com.example.desk.mvp.BaseView;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PulldshuoshuoContract {
    interface View extends BaseView {
        void FabiaoSuccess();

        void FabiaoFail();
    }

    interface  Presenter extends BasePresenter<View> {
        void  FabiaoShuoShuo(List<MultipartBody.Part> file, String data,String username);

        void FabiaoShuoShuoT(String text, String username);
    }
}
