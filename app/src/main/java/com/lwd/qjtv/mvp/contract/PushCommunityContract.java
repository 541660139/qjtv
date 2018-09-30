package com.lwd.qjtv.mvp.contract;

import android.graphics.Bitmap;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.UploadPicBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;


public interface PushCommunityContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadImage(List<Bitmap> list);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {


        Observable<UploadPicBean> uploadPicSlk(@Part MultipartBody.Part requestBody, @QueryMap Map<String, String> map);

        Observable<BaseBean> pushCommunity(@QueryMap Map<String, String> map);
    }
}
