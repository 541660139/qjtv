package com.lwd.qjtv.mvp.ui.fragment.HamePage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.FileUtils;
import com.lwd.qjtv.app.utils.ToastUtil;
import com.lwd.qjtv.di.component.DaggerChoiceComponent;
import com.lwd.qjtv.di.module.ChoiceModule;
import com.lwd.qjtv.mvp.contract.ChoiceContract;
import com.lwd.qjtv.mvp.model.entity.ChoiceDataBean;
import com.lwd.qjtv.mvp.model.entity.DianZanBean;
import com.lwd.qjtv.mvp.presenter.ChoicePresenter;
import com.lwd.qjtv.mvp.ui.activity.SearchActivity;
import com.lwd.qjtv.mvp.ui.activity.VideoDetailsActivity;
import com.lwd.qjtv.mvp.ui.adapter.ChoiceAdapter;
import com.lwd.qjtv.view.LoadingPageView;
import com.paginate.Paginate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
//import cn.jzvd.JZVideoPlayerStandard;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ChoiceFragment extends BaseFragment<ChoicePresenter> implements ChoiceContract.View {

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    private boolean hasSkip = false;

    @BindView(R.id.rl_choice)
    RecyclerView rl_choice;
    @BindView(R.id.fragment_guess_swiperefreshlayout)
    SmartRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fragment_guess_loadingpage)
    LoadingPageView loadingPageView;



    private String filePath;
    private String videoName = "";
    private String mUrl;
    private List<String> fUrls = new ArrayList<>();
    private List<GSYVideoModel> mUrls = new ArrayList<>();
    private Map<String, String> mNeedreferer;
    private String sdCardPath;
    private int tDuration;


    private static ChoiceAdapter choiceAdapter;


    public static ChoiceFragment newInstance() {
        ChoiceFragment fragment = new ChoiceFragment();
        Bundle bundle = new Bundle();
        //fragment保存参数，传入一个Bundle对象
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerChoiceComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .choiceModule(new ChoiceModule(this))
                .build()
                .inject(this);
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {


        return inflater.inflate(R.layout.fragment_choice, container, false);
    }

    @Override
    public void initData() {

        sdCardPath = FileUtils.getStoragePath(getContext(), false);
        loadingPageView.startLodingAnim();
        mPresenter.requestData(true);//打开app时自动加载列表
        initListern();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            //TODO now it's visible to user
            if (choiceAdapter != null) {
                choiceAdapter.setListGSYVideoPlayerState(2);
            }
        }
    }

    private void initListern() {



        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.requestData(true);//打开app时自动加载列表

            }
        });

        mSwipeRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.requestData(false);//打开app时自动加载列表
            }
        });
    }


    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #(Bundle)} 中初始化就可以了
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

//        getRealUrl("https://v.youku.com/v_show/id_XMzY1NzU5MjkyNA==.html?spm=a2hww.20027244.m_250036.5~5!2~5~5~5~5~A&f=51774906");
//
        loadingPageView.checkData(data);
//        ChoiceAdapter choiceAdapter = new ChoiceAdapter(dataUrl);
//        rl_choice.setLayoutManager(new LinearLayoutManager(ChoiceFragment.this.getContext()));
//        rl_choice.setAdapter(choiceAdapter);
//        choiceAdapter.notifyDataSetChanged();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.finishRefresh();
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(getContext(), message);

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        startActivity(intent);
    }

    @Override
    public void killMyself() {

    }


    @Override
    public void startLoadMore() {
        isLoadingMore = true;

    }

    @Override
    public void endLoadMore() {

        mSwipeRefreshLayout.finishLoadmore();
        isLoadingMore = false;
    }

    @Override
    public void setDianZan(DianZanBean baseBean, ChoiceDataBean.DataBean data, View view, View view1) {
        String zan_number = baseBean.getData().getZan_number();
        if (baseBean.getStatus().equals("0")) {
//            取消点赞

            ((ImageView) view).setImageResource(R.mipmap.dainzan);
            ((TextView) view1).setText(zan_number);


        } else if (baseBean.getStatus().equals("1")) {

            ((ImageView) view).setImageResource(R.mipmap.dianzanselect);
            ((TextView) view1).setText(zan_number);


        }

    }


    @Override
    public void setAdapter(DefaultAdapter adapter) {

        if (adapter instanceof ChoiceAdapter) {
            choiceAdapter = (ChoiceAdapter) adapter;

            rl_choice.setAdapter(adapter);
            adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int viewType, Object data, int position) {

                    if (!hasSkip) {
                        hasSkip = true;
                        TextView tv_choice_title = view.findViewById(R.id.tv_choice_title);
                        ListGSYVideoPlayer videoplayer = view.findViewById(R.id.danmaku_player);


                        ImageView iv_dianzan = view.findViewById(R.id.iv_dianzan);
                        ImageView iv_pinglun = view.findViewById(R.id.iv_pinglun);

                        ChoiceDataBean.DataBean dataBean = (ChoiceDataBean.DataBean) data;
                        Intent intent = new Intent(getContext(), VideoDetailsActivity.class);
                        intent.putExtra("id", dataBean.getId());
                        intent.putExtra("videoId", dataBean.getId());
                        intent.putExtra("type", dataBean.getAnalysis_type());
                        intent.putExtra("pic", dataBean.getBitmap());
                        intent.putExtra("title", dataBean.getVideo_title());
//
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Pair<View, String> imgViewPair = Pair.create(videoplayer, getString(R.string.tu));
                            Pair<View, String> txtViewPair = Pair.create(tv_choice_title, getString(R.string.zi));

                            Pair<View, String> imgViewPair1 = Pair.create(iv_dianzan, getString(R.string.tu1));
                            Pair<View, String> imgViewPair2 = Pair.create(iv_pinglun, getString(R.string.tu2));
                            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imgViewPair, txtViewPair, imgViewPair1, imgViewPair2);
                            ActivityCompat.startActivity(getContext(), intent, compat.toBundle());
                        } else {
                            startActivity(intent);
                        }
                    }
                }


            });

            ((ChoiceAdapter) adapter).setClickMoreCallback(new ChoiceAdapter.ClickMoreCallback() {

                @Override
                public void clickdainzan(View view, ChoiceDataBean.DataBean data, View view1) {

                    mPresenter.setDianZan(data, view, view1);
                }
            });

            UiUtils.configRecycleView(rl_choice, new LinearLayoutManager(WEApplication.getContext()));

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        hasSkip = false;
    }
}
