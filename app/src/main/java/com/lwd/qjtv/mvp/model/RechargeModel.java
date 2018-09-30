package com.lwd.qjtv.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.di.scope.ActivityScope;
import com.lwd.qjtv.mvp.contract.RechargeContract;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.AliH5PayBean;
import com.lwd.qjtv.mvp.model.entity.AliPayBean;
import com.lwd.qjtv.mvp.model.entity.GetLevelBean;
import com.lwd.qjtv.mvp.model.entity.SwiftPassPayBean;
import com.lwd.qjtv.mvp.model.entity.WechatH5PayBean;
import com.lwd.qjtv.mvp.model.entity.WechatPayBean;

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
 * Created by ZhengQian on 2017/5/13.
 */

@ActivityScope
public class RechargeModel extends BaseModel implements RechargeContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public RechargeModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<GetLevelBean> getUserInfo(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getUserInfo(map);
    }

    @Override
    public Observable<SwiftPassPayBean> getSwiftPassPay(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getSwiftPassPay(map);
    }

    @Override
    public Observable<WechatPayBean> wechatPay(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).wechatPay(map);
    }
    @Override
    public Observable<WechatH5PayBean> wechatH5Pay(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).wechatH5Pay(map);
    }
    @Override
    public Observable<AliPayBean> aliPay(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).aliPay(map);
    }


    @Override
    public Observable<AliH5PayBean> aliH5Pay(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).aliH5Pay(map);
    }



}