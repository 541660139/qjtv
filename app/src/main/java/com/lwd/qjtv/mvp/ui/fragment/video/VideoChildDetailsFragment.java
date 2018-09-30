package com.lwd.qjtv.mvp.ui.fragment.video;

import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

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
import com.lwd.qjtv.di.component.DaggerVideoChildDetailsComponent;
import com.lwd.qjtv.di.module.VideoChildDetailsModule;
import com.lwd.qjtv.mvp.contract.VideoChildDetailsContract;
import com.lwd.qjtv.mvp.model.entity.RelatedVideoBean;
import com.lwd.qjtv.mvp.model.entity.VideoDetailsNewBean;
import com.lwd.qjtv.mvp.presenter.VideoChildDetailsPresenter;
import com.lwd.qjtv.mvp.ui.activity.VideoDetailsActivity;
import com.lwd.qjtv.view.CustomLoadingListItemCreator;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class VideoChildDetailsFragment extends BaseFragment<VideoChildDetailsPresenter> implements VideoChildDetailsContract.View, OnRefreshListener, OnLoadmoreListener, DefaultAdapter.OnRecyclerViewItemClickListener {

    private static String videoId;
    private static String v_type;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
//    private SwipeRefreshLayout mSwipeRefreshLayout;


    //评分
    @BindView(R.id.fragment_video_child_details_ratingbar)
    RatingBar ratingBar;
    //标题
    @BindView(R.id.fragment_video_child_details_title_tv)
    TextView titleTv;
    //分数
    @BindView(R.id.fragment_video_child_details_score_tv)
    TextView scoreTv;
    //比赛人员
    @BindView(R.id.fragment_video_child_details_match_people_tv)
    TextView matchPeopleTv;
    //时间
    @BindView(R.id.fragment_video_child_details_time_tv)
    TextView timeTv;
    //描述
    @BindView(R.id.fragment_video_child_details_desc_tv)
    TextView descTv;
    //分享按钮
    @BindView(R.id.fragment_video_child_details_share_tv)
    TextView shareTv;
    //收藏按钮
    @BindView(R.id.fragment_video_child_details_collection_tv)
    TextView collectionTv;
    //收藏图表
    @BindView(R.id.fragment_video_child_details_collection_iv)
    ImageView collectionIv;
    //相关视频
    @BindView(R.id.fragment_video_child_details_video_recyclerview)
    RecyclerView mRecyclerView;
    //分集列表
    @BindView(R.id.fragment_video_child_details_fenji_recyclerview)
    RecyclerView fenjiRecyclerview;
    @BindView(R.id.fragment_video_child_details_scroll_view)
    ScrollView scrollView;
    @BindView(R.id.fragment_video_details_collection_ll)
    LinearLayout collectionLl;
    @BindView(R.id.fragment_video_details_share_ll)
    LinearLayout shareLl;
    @BindView(R.id.swipeRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    private boolean isCollection;
    private VideoDetailsNewBean.DataBean data;

    public static VideoChildDetailsFragment newInstance() {
        VideoChildDetailsFragment fragment = new VideoChildDetailsFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerVideoChildDetailsComponent
                .builder()
                .appComponent(appComponent)
                .videoChildDetailsModule(new VideoChildDetailsModule(this))//请将VideoChildDetailsModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_video_child_details, container, false);
    }

    @Override
    public void initData() {
        getParseArguments();
        initListener();
    }

    private void getParseArguments() {
        videoId = getArguments().getString("video_id");
        v_type = getArguments().getString("v_type");
    }

    private void initListener() {
        collectionLl.setOnClickListener(view -> mPresenter.addVideoCollection(videoId, v_type));
        shareLl.setOnClickListener(v -> WEApplication.showShare());
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
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
        Message message = (Message) data;
        switch (message.what) {
            case 0:
//                parseData((VideoDetailsNewBean.DataBean) message.obj);
                break;
            case 1:
                isCollection = !isCollection;
                collectionTv.setText(isCollection ? "已收藏" : "收藏");
                collectionTv.setTextColor(isCollection ? getResources().getColor(R.color.colorOrigin) : getResources().getColor(R.color.color333333));
                collectionIv.setImageResource(isCollection ? R.mipmap.collection_select : R.mipmap.collection);
                break;
        }
    }

//    private void parseData(VideoDetailsNewBean.DataBean data) {
//        this.data = data;
//        if (titleTv == null)
//            return;
//        titleTv.setText(data.getVideo_info().getName() == null ? "" : data.getVideo_info().getName());
//        matchPeopleTv.setText(data.getVideo_info().getMatchPeople());
//        scoreTv.setText(data.getVideo_info().getScore());
//        float i = Float.parseFloat((data.getVideo_info().getScore() == null || data.getVideo_info().getScore().equals("")) ? "0" : data.getVideo_info().getScore());
//        ratingBar.setRating(i == 0.0 ? 0 : (i / 2));
//        timeTv.setText(data.getVideo_info().getVideo_length());
//        descTv.setText(data.getVideo_info().getDes());
//        isCollection = data.getVideo_info().getIsCollect() == 1;
//        collectionTv.setText(isCollection ? "已收藏" : "收藏");
//        collectionTv.setTextColor(isCollection ? getResources().getColor(R.color.colorOrigin) : getResources().getColor(R.color.color333333));
//        collectionIv.setImageResource(isCollection ? R.mipmap.collection_select : R.mipmap.collection);
//        videoId = data.getVideo().getId();
//        mPresenter.requestVideoChildDetailsList(data.getVideo_info().getId(), data.getVideo_info().getV_type(), "relatedVideo", data.getVideo_info().getStarId(), true);
//    }


    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        mRecyclerView.setNestedScrollingEnabled(false);
        fenjiRecyclerview.setNestedScrollingEnabled(false);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(WEApplication.getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        scrollView.scrollTo(0, 0);
    }


    @Override
    public void showLoading() {
        Timber.tag(TAG).w("showLoading");
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                        }
                        //                        mSwipeRefreshLayout.setRefreshing(true)
                );
    }

    @Override
    public void hideLoading() {
        Timber.tag(TAG).w("hideLoading");
        smartRefreshLayout.finishRefresh();
//        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {
//        UiUtils.SnackbarText(message);

        UiUtils.makeText(getContext(), message);
    }

    @Override
    public void launchActivity(Intent intent) {
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }


    @Override
    public void setAdapter(DefaultAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
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

    /**
     * 结束加载更多
     */
    @Override
    public void endLoadMore() {
        isLoadingMore = false;
        smartRefreshLayout.finishLoadmore();
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    /**
     * 初始化Paginate,用于加载更多
     */
    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
//                    mPresenter.requestVideoChildDetailsList(false);
                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return false;
                }
            };

            mPaginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(10).setLoadingListItemCreator(new CustomLoadingListItemCreator())
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    public void onDestroy() {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mRxPermissions = null;
        this.mPaginate = null;
    }

    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {
        RelatedVideoBean.DataBean dataBean = (RelatedVideoBean.DataBean) data;
        Intent intent = new Intent(getContext(), VideoDetailsActivity.class);
        intent.putExtra("pic", dataBean.getPic_h());
        intent.putExtra("type", dataBean.getV_type());
        intent.putExtra("id", dataBean.getId());
        startActivity(intent);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
//        mPresenter.requestVideoChildDetailsList(data.getVideo_info().getId(), data.getVideo_info().getV_type(), "relatedVideo", data.getVideo_info().getStarId(), false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
//        mPresenter.requestVideoChildDetailsList(data.getVideo_info().getId(), data.getVideo_info().getV_type(), "relatedVideo", data.getVideo_info().getStarId(), true);
    }


}
