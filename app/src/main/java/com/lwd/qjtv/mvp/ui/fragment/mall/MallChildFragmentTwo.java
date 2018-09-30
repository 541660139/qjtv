package com.lwd.qjtv.mvp.ui.fragment.mall;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.di.component.DaggerMallChildTwoComponent;
import com.lwd.qjtv.di.module.MallChildModule;
import com.lwd.qjtv.mvp.contract.MallChildContract;
import com.lwd.qjtv.mvp.model.entity.MallListBean;
import com.lwd.qjtv.mvp.presenter.MallChildPresenter;
import com.lwd.qjtv.mvp.ui.activity.WebViewActivity;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

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
 * Created by ZhengQian on 2017/5/16.
 */

public class MallChildFragmentTwo extends BaseFragment<MallChildPresenter> implements MallChildContract.View, OnRefreshListener, OnLoadmoreListener {

    private Paginate mPaginate;
    private boolean isLoadingMore;
    @BindView(R.id.fragment_mallchild_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_mallchild_swiperefreshlayout)
    SmartRefreshLayout swipeRefreshLayout;

    public static MallChildFragmentTwo newInstance() {
        MallChildFragmentTwo fragment = new MallChildFragmentTwo();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMallChildTwoComponent
                .builder()
                .appComponent(appComponent)
                .mallChildModule(new MallChildModule(this))//请将MallChildFragmentModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_mallchild, container, false);
    }

    @Override
    public void initData() {
        mPresenter.requestMallList(true,"3");
    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onActivityCreated
     * 还没执行,setData里调用presenter的方法时,是会报空的,因为dagger注入是在onActivityCreated
     * 方法中执行的,如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

    }

    public void setAdapter(DefaultAdapter adapter) {
        adapter.setOnItemClickListener((view, viewType, data, position) -> {
            Intent intent = new Intent(WEApplication.getContext(), WebViewActivity.class);
            intent.putExtra("id", ((MallListBean.DataBean) data).getGoods_id());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            WEApplication.getContext().startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        initRecycleView();
//        initPaginate();
    }

    @Override
    public void startLoadMore() {
        isLoadingMore = true;
    }

    @Override
    public void endLoadMore() {
        isLoadingMore = false;
        swipeRefreshLayout.finishLoadmore();
    }

    @Override
    public RxPermissions getRxPermissions() {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.finishRefresh();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.makeText(getContext(),message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnLoadmoreListener(this);
        swipeRefreshLayout.setEnableLoadmore(false);
        UiUtils.configRecycleView(recyclerView, new LinearLayoutManager(WEApplication.getContext()));
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.requestMallList(false,"3");
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestMallList(true,"3");
    }
}