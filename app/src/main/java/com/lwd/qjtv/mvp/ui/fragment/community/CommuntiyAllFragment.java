package com.lwd.qjtv.mvp.ui.fragment.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.di.component.DaggerCommuntiyAllComponent;
import com.lwd.qjtv.di.module.CommuntiyAllModule;
import com.lwd.qjtv.mvp.contract.CommuntiyAllContract;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.presenter.CommuntiyAllPresenter;
import com.lwd.qjtv.mvp.ui.activity.CommuntiyDetailActivity;
import com.lwd.qjtv.mvp.ui.adapter.CommunityAllAdapter;
import com.lwd.qjtv.view.LoadingPageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CommuntiyAllFragment extends BaseFragment<CommuntiyAllPresenter> implements CommuntiyAllContract.View, OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.receiver_community)
    RecyclerView receiver_community;
    @BindView(R.id.fragment_community_all_smartrefreshlayout)
    SmartRefreshLayout fragment_community_all_smartrefreshlayout;
    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;


    protected boolean isCreated = false;
    private int m_type = 0;


    private CommunityAllAdapter communityAllAdapter;
    //    点击关注的条目


    public static CommuntiyAllFragment newInstance(int type) {

        CommuntiyAllFragment fragment = new CommuntiyAllFragment();
        Bundle bundleOne = new Bundle();
        bundleOne.putInt("m_type", type);
        fragment.setArguments(bundleOne);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCommuntiyAllComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .communtiyAllModule(new CommuntiyAllModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_communtiy_all, container, false);
    }

    @Override
    public void initData() {
        loadingPageView.startLodingAnim();
        if (!getArguments().isEmpty() && getArguments().containsKey("m_type"))
            m_type = getArguments().getInt("m_type");
        mPresenter.getCommunity(true, m_type + "");

        initSmartreFresh();

        initListener();
    }

    private void initSmartreFresh() {
        fragment_community_all_smartrefreshlayout.getRefreshHeader().setPrimaryColors(getResources().getColor(R.color.colorPrimaryDark));
        ClassicsFooter ballPulseFooter = new ClassicsFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale);
        ballPulseFooter.setPrimaryColors(getResources().getColor(R.color.colorPrimaryDark));
        ballPulseFooter.setBackgroundResource(R.color.colorPrimaryDark);
        fragment_community_all_smartrefreshlayout.setRefreshFooter(ballPulseFooter);
    }

    private void initListener() {
        loadingPageView.setClickReload(() -> mPresenter.getCommunity(true, m_type + ""));
        fragment_community_all_smartrefreshlayout.setOnRefreshListener(this);
//        fragment_community_all_smartrefreshlayout.setEnableLoadmore(true);
        fragment_community_all_smartrefreshlayout.setOnLoadmoreListener(this);

    }


    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)},
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {
        loadingPageView.checkData(data);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        fragment_community_all_smartrefreshlayout.finishRefresh();
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
        fragment_community_all_smartrefreshlayout.finishLoadmore();
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        communityAllAdapter = (CommunityAllAdapter) adapter;

        ((CommunityAllAdapter) adapter).setOnItemTvClickListener(new CommunityAllAdapter.OnRecyclerViewTvItemClickListener() {
            @Override
            public void onItemTVClick(View view, String starid, int posttion) {

                mPresenter.setFollow(starid);
            }
        });

        UiUtils.configRecycleView(receiver_community, new LinearLayoutManager(getContext()));
        receiver_community.setAdapter(adapter);
        adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {

                Intent intent = new Intent(getContext(), CommuntiyDetailActivity.class);
                intent.putExtra("post_id", communityAllAdapter.getAllData().get(position).getPost_id());
                startActivity(intent);

            }
        });
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.getCommunity(true, m_type + "");
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.getCommunity(false, m_type + "");
    }


    //    m_type 1 最新 | 2 关注
    @Override
    public void setFollowData(BaseBean data) {
//        取消关注成功
        mPresenter.getCommunity(true, m_type + "");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (!isCreated) {
            return;
        }

        if (isVisibleToUser) {
            mPresenter.getCommunity(true, m_type + "");
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
    }
}
