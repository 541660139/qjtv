package com.lwd.qjtv.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IViewList;
import com.lwd.qjtv.mvp.model.entity.BBSNewsListBean;
import com.lwd.qjtv.mvp.model.entity.BannerBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;


public interface NewsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IViewList {
        void setBannerData(BannerBean bannerBean);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BBSNewsListBean> getBBSNews(@QueryMap Map<String, String> map);
        Observable<BannerBean> getBanner(@QueryMap Map<String, String> map);

    }

}
