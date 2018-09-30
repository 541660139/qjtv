package com.lwd.qjtv.mvp.ui.fragment.HamePage;

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
import com.lwd.qjtv.mvp.model.entity.HotPointBean;
import com.lwd.qjtv.mvp.ui.adapter.NewsAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Contact;
import com.lwd.qjtv.di.component.DaggerWatchingFocusComponent;
import com.lwd.qjtv.di.module.WatchingFocusModule;
import com.lwd.qjtv.mvp.contract.WatchingFocusContract;
import com.lwd.qjtv.mvp.presenter.WatchingFocusPresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class WatchingFocusFragment extends BaseFragment<WatchingFocusPresenter> implements WatchingFocusContract.View, OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.fragment_news_smartrefreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.fragment_news_recyclerview)
    RecyclerView recyclerView;
    private Contact contact;
    private NewsAdapter adapter1;

    public static WatchingFocusFragment newInstance() {
        WatchingFocusFragment fragment = new WatchingFocusFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWatchingFocusComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .watchingFocusModule(new WatchingFocusModule(this))
                .build()
                .inject(this);
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_watching_focus, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getMyCommentList(true);
    }


    @Override
    public void initData() {
        initListener();
    }

    private void initListener() {
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)},
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

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        smartRefreshLayout.finishRefresh();

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
        smartRefreshLayout.finishLoadmore();

    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        adapter1 = (NewsAdapter) adapter;
        recyclerView.setAdapter(adapter1);
        UiUtils.configRecycleView(recyclerView, new LinearLayoutManager(getContext()));
        adapter1.setOnItemClickListener((view, viewType, data, position) -> {
            HotPointBean.DataBean databean = (HotPointBean.DataBean) data;

            if (!databean.getContent_type().equals("2")) {
                Map<String, String> map = new HashMap<>();
                map.put("id", databean.getId());
                if (contact == null)
                    contact = new Contact(getContext());
                contact.startWebActivity(Constant.BBS_NEWS_DETAILS, map);
            }
//            else {
//                TextView tv_choice_title = view.findViewById(R.id.item_consult_more_pic_title_tv);
//                ListGSYVideoPlayer listGSYVideoPlayer = view.findViewById(R.id.danmaku_player);
//
//
//                Intent intent = new Intent(getContext(), VideoDetailsActivity.class);
//                intent.putExtra("id", databean.getId());
//                intent.putExtra("videoId", databean.getId());
//                intent.putExtra("type", databean.getAnalysis_type());
//                intent.putExtra("pic", databean.getBitmap());
//                intent.putExtra("title", databean.getTitle());
////
//                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    Pair<View, String> imgViewPair = Pair.create(listGSYVideoPlayer, getString(R.string.tu));
//                    Pair<View, String> txtViewPair = Pair.create(tv_choice_title, getString(R.string.zi));
//
//                    ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imgViewPair, txtViewPair);
//                    ActivityCompat.startActivity(getContext(), intent, compat.toBundle());
//                } else {
//                    startActivity(intent);
//                }
//            }
        });
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.getMyCommentList(true);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.getMyCommentList(false);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            //TODO now it's visible to user
            if (adapter1 != null) {
                adapter1.setListGSYVideoPlayerState(2);
            }
        }
    }
}
