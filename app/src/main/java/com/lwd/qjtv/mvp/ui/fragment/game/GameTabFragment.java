package com.lwd.qjtv.mvp.ui.fragment.game;

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
import com.lwd.qjtv.di.component.DaggerGameTabComponent;
import com.lwd.qjtv.di.module.GameTabModule;
import com.lwd.qjtv.mvp.contract.GameTabContract;
import com.lwd.qjtv.mvp.model.entity.GameDataBean;
import com.lwd.qjtv.mvp.presenter.GameTabPresenter;
import com.lwd.qjtv.mvp.ui.activity.LiveActivity;
import com.lwd.qjtv.mvp.ui.adapter.GameDataAdapter;
import com.lwd.qjtv.view.LoadingPageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class GameTabFragment extends BaseFragment<GameTabPresenter> implements GameTabContract.View, OnRefreshListener, OnLoadmoreListener {


    @BindView(R.id.receiver_game)
    RecyclerView receiver_game;

    @BindView(R.id.loading_game)
    LoadingPageView loading_game;


    @BindView(R.id.fragment_smartrefreshlayout)
    SmartRefreshLayout fragment_smartrefreshlayout;


    private String game_id;

    public static GameTabFragment newInstance(String game_id) {
        GameTabFragment fragment = new GameTabFragment();
        Bundle bundleOne = new Bundle();
        bundleOne.putString("game_id", game_id);
        fragment.setArguments(bundleOne);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerGameTabComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .gameTabModule(new GameTabModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_game_tab, container, false);
    }

    @Override
    public void initData() {
        if (!getArguments().isEmpty() && getArguments().containsKey("game_id")) {
            game_id = getArguments().getString("game_id");
        }
        loading_game.startLodingAnim();
        initListener();
        mPresenter.requestCollectionList(true, game_id);

    }

    private void initListener() {
        loading_game.setClickReload(() -> mPresenter.requestCollectionList(true, game_id));
        fragment_smartrefreshlayout.setOnRefreshListener(this);
        fragment_smartrefreshlayout.setEnableLoadmore(true);
        fragment_smartrefreshlayout.setOnLoadmoreListener(this);

    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData()} 中初始化就可以了
     * <p>
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
        loading_game.checkData(data);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        fragment_smartrefreshlayout.finishRefresh();

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
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestCollectionList(true, game_id);

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.requestCollectionList(false, game_id);
    }

    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {
        fragment_smartrefreshlayout.finishLoadmore();

    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {

    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        GameDataAdapter gameDataAdapter = (GameDataAdapter) adapter;
        UiUtils.configRecycleView(receiver_game, new LinearLayoutManager(getContext()));
        receiver_game.setAdapter(adapter);
        gameDataAdapter.setOnItemClickListener(new GameDataAdapter.OnItemClickListener() {
            @Override
            public void onChildItemClickListener(GameDataBean.DataBean.PhaseListBean haseListBeapn) {
                if (haseListBeapn.getIs_end().equals("1")) {
//                    直播间
                    Intent intent = new Intent(getContext(), LiveActivity.class);
                    intent.putExtra("id", haseListBeapn.getId());
                    intent.putExtra("match_id", haseListBeapn.getGame_id());
                    intent.putExtra("url", haseListBeapn.getLiveUrl());
                    intent.putExtra("title", haseListBeapn.getComplete_name());
                    intent.putExtra("moveText", "");
                    intent.putExtra("moveSpeed", "");
                    intent.putExtra("origin", "4");
                    intent.putExtra("erweimaUrl", "");
                    intent.putExtra("isNBA", false);
                    startActivity(intent);

                } else {
//                    竞猜界面
                    Intent intent = new Intent();
                }

                UiUtils.SnackbarText(haseListBeapn.getComplete_name());

            }
        });

    }
}
