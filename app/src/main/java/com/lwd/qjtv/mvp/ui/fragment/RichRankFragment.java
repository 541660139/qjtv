package com.lwd.qjtv.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.di.component.DaggerRichRankComponent;
import com.lwd.qjtv.di.module.RichRankModule;
import com.lwd.qjtv.mvp.contract.RichRankContract;
import com.lwd.qjtv.mvp.model.entity.GuessRankBean;
import com.lwd.qjtv.mvp.presenter.RichRankPresenter;
import com.lwd.qjtv.view.LoadingPageView;
import com.lwd.qjtv.view.VerticalSwipeRefreshLayout;
import com.paginate.Paginate;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class RichRankFragment extends BaseFragment<RichRankPresenter> implements RichRankContract.View, SwipeRefreshLayout.OnRefreshListener {

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    @BindView(R.id.activity_rank_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_rank_swiperefreshlayout)
    VerticalSwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;
    @BindView(R.id.activity_rank_one_civ)
    CircleImageView twoCiv;
    @BindView(R.id.activity_rank_two_civ)
    CircleImageView oneCiv;
    @BindView(R.id.activity_rank_three_civ)
    CircleImageView threeCiv;
    @BindView(R.id.activity_rank_one_name_tv)
    TextView twoNameTv;
    @BindView(R.id.activity_rank_two_name_tv)
    TextView oneNameTv;
    @BindView(R.id.activity_rank_three_name_tv)
    TextView threeNameTv;
    @BindView(R.id.activity_rank_one_number_tv)
    TextView twoNumTv;
    @BindView(R.id.activity_rank_two_number_tv)
    TextView oneNumTv;
    @BindView(R.id.activity_rank_three_number_tv)
    TextView threeNumTv;
    @BindView(R.id.tv_bottom_tishi)
    TextView tv_bottom_tishi;


    public static RichRankFragment newInstance() {
        RichRankFragment fragment = new RichRankFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerRichRankComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .richRankModule(new RichRankModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_rich_rank, container, false);
    }

    @Override
    public void initData() {
        loadingPageView.startLodingAnim();

        initListener();
        onRefresh();

    }


    @Override
    public void onRefresh() {

        mPresenter.requestRankList(true);//打开app时自动加载列表
    }


    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(getContext()));
    }


    private void initListener() {
        loadingPageView.setClickReload(() -> onRefreshData());
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
        loadingPageView.checkData(data);
        if (data != null) {
            List<GuessRankBean.DataBean> dataBeen = (List<GuessRankBean.DataBean>) data;
            for (int i = 0; i < dataBeen.size(); i++) {
                GuessRankBean.DataBean dataBean = dataBeen.get(i);
                if (i == 1) {
                    twoNameTv.setText(dataBean.getUsername());
                    twoNumTv.setText(dataBean.getAll_wager());
                    Glide.with(this).load(dataBean.getAvater()).placeholder(R.mipmap.video_place_holder).error(R.mipmap.video_place_holder).into(twoCiv);
                } else if (i == 2) {
                    threeNameTv.setText(dataBean.getUsername());
                    threeNumTv.setText(dataBean.getAll_wager());
                    Glide.with(this).load(dataBean.getAvater()).placeholder(R.mipmap.video_place_holder).error(R.mipmap.video_place_holder).into(threeCiv);
                } else if (i == 0) {
                    oneNameTv.setText(dataBean.getUsername());
                    oneNumTv.setText(dataBean.getAll_wager());
                    Glide.with(this).load(dataBean.getAvater()).placeholder(R.mipmap.video_place_holder).error(R.mipmap.video_place_holder).into(oneCiv);
                }
            }
        }

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


    public void onRefreshData() {

        mPresenter.requestRankList(true);//打开app时自动加载列表
    }


    @Override
    public void showLoading() {
        Timber.tag(TAG).w("showLoading");
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideLoading() {
        Timber.tag(TAG).w("hideLoading");
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void setAdapter(DefaultAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        initRecycleView();
//        initPaginate();
    }

    /**
     * 开始加载更多
     */
    @Override
    public void startLoadMore() {
        isLoadingMore = true;
    }

    @Override
    public void endLoadMore() {
        isLoadingMore = false;
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }


    @Override
    public void onDestroy() {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mRxPermissions = null;
        this.mPaginate = null;
    }
}
