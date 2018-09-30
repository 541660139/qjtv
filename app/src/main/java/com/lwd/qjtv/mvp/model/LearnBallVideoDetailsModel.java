package com.lwd.qjtv.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;

import com.lwd.qjtv.mvp.contract.LearnBallVideoDetailsContract;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.CommentMessageBean;
import com.lwd.qjtv.mvp.model.entity.LearnBallDetailsBean;
import com.lwd.qjtv.mvp.model.entity.PingFenBean;


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
 * Created by ZhengQian on 2017/6/13.
 */

@ActivityScope
public class LearnBallVideoDetailsModel extends BaseModel implements LearnBallVideoDetailsContract.Model {
    @Inject
    public LearnBallVideoDetailsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<CommentMessageBean> getCommentMessage(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getCommentMessage(map);
    }

    @Override
    public Observable<BaseBean> addCommentMessage(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).addCommentMessage(map);
    }

    @Override
    public Observable<BaseBean> likeComment(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).likeComment(map);
    }

    @Override
    public Observable<LearnBallDetailsBean> getLearnBallDetails(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getLearnBallDetails(map);
    }

    @Override
    public Observable<PingFenBean> addPingFen(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).addPingFen(map);
    }


}
