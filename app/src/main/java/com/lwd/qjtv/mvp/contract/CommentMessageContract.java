package com.lwd.qjtv.mvp.contract;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IViewList;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.CommentMessageBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/19.
 */

public interface CommentMessageContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends IViewList {
        void setAdapter(DefaultAdapter adapter);

        void startLoadMore();

        void endLoadMore();

        //申请权限
        RxPermissions getRxPermissions();
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel {
        Observable<CommentMessageBean> getCommentMessage(@QueryMap Map<String, String> map);

        Observable<BaseBean> addCommentMessage(@QueryMap Map<String, String> map);

        Observable<BaseBean> likeComment(@QueryMap Map<String, String> map);

    }
}