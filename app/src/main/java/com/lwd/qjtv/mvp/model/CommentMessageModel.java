package com.lwd.qjtv.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.di.scope.ActivityScope;
import com.lwd.qjtv.mvp.contract.CommentMessageContract;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.CommentMessageBean;

import java.util.Map;

import javax.inject.Inject;

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

@ActivityScope
public class CommentMessageModel extends BaseModel implements CommentMessageContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public CommentMessageModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<CommentMessageBean> getCommentMessage(Map<String, String> map) {
        Observable<CommentMessageBean> commentMessage = mRepositoryManager.obtainRetrofitService(UserService.class)
                .getCommentMessage(map);
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return commentMessage;
    }

    @Override
    public Observable<BaseBean> addCommentMessage(@QueryMap Map<String, String> map) {
        Observable<BaseBean> stringObservable = mRepositoryManager.obtainRetrofitService(UserService.class)
                .addCommentMessage(map);
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return stringObservable;
    }

    @Override
    public Observable<BaseBean> likeComment(@QueryMap Map<String, String> map) {
        Observable<BaseBean> stringObservable = mRepositoryManager.obtainRetrofitService(UserService.class)
                .likeComment(map);
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return stringObservable;
    }
}