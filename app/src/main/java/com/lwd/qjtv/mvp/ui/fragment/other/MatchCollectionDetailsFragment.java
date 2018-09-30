package com.lwd.qjtv.mvp.ui.fragment.other;

import android.content.Intent;
import android.os.Bundle;
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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.lwd.qjtv.R;
import com.lwd.qjtv.di.component.DaggerMatchCollectionDetailsComponent;
import com.lwd.qjtv.di.module.MatchCollectionDetailsModule;
import com.lwd.qjtv.mvp.contract.MatchCollectionDetailsContract;
import com.lwd.qjtv.mvp.model.entity.MatchCollectionDetailsBean;
import com.lwd.qjtv.mvp.presenter.MatchCollectionDetailsPresenter;
import com.lwd.qjtv.mvp.ui.activity.VideoDetailsActivity;
import com.lwd.qjtv.view.LoadingPageView;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MatchCollectionDetailsFragment extends BaseFragment<MatchCollectionDetailsPresenter> implements MatchCollectionDetailsContract.View, DefaultAdapter.OnRecyclerViewItemClickListener<MatchCollectionDetailsBean.DataBean.MatchListBean> {

    @BindView(R.id.fragment_match_collection_details_recyclerview)
    RecyclerView fragmentMatchCollectionDetailsRecyclerview;
    @BindView(R.id.fragment_match_collection_details_swiperefreshlayout)
    SmartRefreshLayout fragmentMatchCollectionDetailsSwiperefreshlayout;
    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingFramelayout;
    private String id;


    public static MatchCollectionDetailsFragment newInstance(String id) {
        MatchCollectionDetailsFragment fragment = new MatchCollectionDetailsFragment();
        Bundle bundleOne = new Bundle();
        bundleOne.putString("id", id);
        fragment.setArguments(bundleOne);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMatchCollectionDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .matchCollectionDetailsModule(new MatchCollectionDetailsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_match_collection_details, container, false);
    }

    @Override
    public void initData() {
        loadingFramelayout.startLodingAnim();
        if (!getArguments().isEmpty() && getArguments().containsKey("id"))
            id = getArguments().getString("id");
        mPresenter.getMatchCollectionDetails(true, id);
    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {
        loadingFramelayout.checkData(data);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {

    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        UiUtils.configRecycleView(fragmentMatchCollectionDetailsRecyclerview, new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener(this);
        fragmentMatchCollectionDetailsRecyclerview.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int viewType, MatchCollectionDetailsBean.DataBean.MatchListBean data, int position) {
        Intent intent = new Intent(getContext(), VideoDetailsActivity.class);
        if (data.getV_type() != null && !data.getV_type().equals("") && data.getVideo_id() != null && !data.getVideo_id().equals("")) {
            intent.putExtra("type", data.getV_type());
            intent.putExtra("id", data.getVideo_id());
            startActivity(intent);
        }
    }
}
