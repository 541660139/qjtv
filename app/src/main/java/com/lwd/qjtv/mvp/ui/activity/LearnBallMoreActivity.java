package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.paginate.Paginate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.lwd.qjtv.app.utils.GlideConfigGlobal;
import com.lwd.qjtv.di.component.DaggerLearnBallMoreComponent;
import com.lwd.qjtv.di.module.LearnBallMoreModule;
import com.lwd.qjtv.mvp.contract.LearnBallMoreContract;
import com.lwd.qjtv.mvp.model.entity.LearnBallBean;
import com.lwd.qjtv.mvp.presenter.LearnBallMorePresenter;

import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.ui.fragment.LearnBallFragment;
import com.lwd.qjtv.view.LoadingPageView;
import com.lwd.qjtv.view.WatchBallItemViewLearnBall;


import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LearnBallMoreActivity extends BaseActivity<LearnBallMorePresenter> implements LearnBallMoreContract.View ,OnRefreshListener {

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    private RecyclerView mRecyclerView;

    private static LearnBallFragment learnBallFragment;
    @BindView(R.id.zq_refreshlayout)
    SmartRefreshLayout mSwipeRefreshLayout;
    //小编推荐
    @BindView(R.id.fragment_learn_ball_push_iv)
    ImageView pushIv;
    @BindView(R.id.fragment_learn_ball_push_title_tv)
    TextView pushTitleTv;
    @BindView(R.id.fragment_learn_ball_push_more_tv)
    TextView pushMoreTv;
    @BindView(R.id.fragment_learn_ball_push_one_wbiv)
    WatchBallItemViewLearnBall pushWbivOne;
    @BindView(R.id.fragment_learn_ball_push_two_wbiv)
    WatchBallItemViewLearnBall pushWbivTwo;
    @BindView(R.id.fragment_learn_ball_push_three_wbiv)
    WatchBallItemViewLearnBall pushWbivThree;
    @BindView(R.id.fragment_learn_ball_push_four_wbiv)
    WatchBallItemViewLearnBall pushWbivFour;

    //大师教学
    @BindView(R.id.fragment_learn_ball_teach_title_tv)
    TextView teachTitleTv;
    @BindView(R.id.fragment_learn_ball_teach_iv)
    ImageView teachIv;
    @BindView(R.id.fragment_learn_ball_teach_more_tv)
    TextView teachMoreTv;
    @BindView(R.id.fragment_learn_ball_teach_one_wbiv)
    WatchBallItemViewLearnBall teachWbivOne;
    @BindView(R.id.fragment_learn_ball_teach_two_wbiv)
    WatchBallItemViewLearnBall teachWbivTwo;
    @BindView(R.id.fragment_learn_ball_teach_three_wbiv)
    WatchBallItemViewLearnBall teachWbivThree;
    @BindView(R.id.fragment_learn_ball_teach_four_wbiv)
    WatchBallItemViewLearnBall teachWbivFour;

    //单杆破百
    @BindView(R.id.fragment_learn_ball_pobai_title_tv)
    TextView pobaiTitleTv;
    @BindView(R.id.fragment_learn_ball_pobai_iv)
    ImageView pobaiIv;
    @BindView(R.id.fragment_learn_ball_pobai_more_tv)
    TextView pobaiMoreTv;
    @BindView(R.id.fragment_learn_ball_pobai_one_wbiv)
    WatchBallItemViewLearnBall pobaiWbivOne;
    @BindView(R.id.fragment_learn_ball_pobai_two_wbiv)
    WatchBallItemViewLearnBall pobaiWbivTwo;
    @BindView(R.id.fragment_learn_ball_pobai_three_wbiv)
    WatchBallItemViewLearnBall pobaiWbivThree;
    @BindView(R.id.fragment_learn_ball_pobai_four_wbiv)
    WatchBallItemViewLearnBall pobaiWbivFour;

    //系列视频
    @BindView(R.id.fragment_learn_ball_video_iv)
    ImageView videoIv;
    @BindView(R.id.fragment_learn_ball_video_title_tv)
    TextView videoTitleTv;
    @BindView(R.id.fragment_learn_ball_video_more_tv)
    TextView videoMoreTv;
    @BindView(R.id.fragment_learn_ball_video_one_wbiv)
    WatchBallItemViewLearnBall videoWbivOne;
    @BindView(R.id.fragment_learn_ball_video_two_wbiv)
    WatchBallItemViewLearnBall videoWbivTwo;
    @BindView(R.id.fragment_learn_ball_video_three_wbiv)
    WatchBallItemViewLearnBall videoWbivThree;
    @BindView(R.id.fragment_learn_ball_video_four_wbiv)
    WatchBallItemViewLearnBall videoWbivFour;

    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;

    private AppComponent appComponent;
    private ImageLoader imageLoader;
    private LearnBallBean.DataBean data;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerLearnBallMoreComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .learnBallMoreModule(new LearnBallMoreModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_learn_ball_more; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData() {
        setTitle("完美单杆");
        loadingPageView.startLodingAnim();
        mPresenter.requestLearnBallList(true);
        initListener();
    }

    private void initListener() {
        loadingPageView.setClickReload(() -> mPresenter.requestLearnBallList(true));
        mSwipeRefreshLayout.setEnableLoadmore(false);
        pushMoreTv.setOnClickListener(view -> {
            Intent intent = new Intent(this, MoreActivity.class);
            intent.putExtra("type", "1");
            intent.putExtra("title", this.data.getXiaobian().getColumn_name() == null ? "小编推荐" : this.data.getXiaobian().getColumn_name());
            startActivity(intent);
        });
        teachMoreTv.setOnClickListener(view -> {
            Intent intent = new Intent(this, MoreActivity.class);
            intent.putExtra("type", "2");
            intent.putExtra("title", this.data.getDashi().getColumn_name() == null ? "大师教学" : this.data.getDashi().getColumn_name());
            startActivity(intent);
        });
        videoMoreTv.setOnClickListener(view -> {
            Intent intent = new Intent(this, MoreActivity.class);
            intent.putExtra("type", "3");
            intent.putExtra("title", this.data.getXilie().getColumn_name() == null ? "系列视频" : this.data.getXilie().getColumn_name());
            startActivity(intent);
        });
        pobaiMoreTv.setOnClickListener(view -> {
            Intent intent = new Intent(this, MoreActivity.class);
            intent.putExtra("type", "4");
            intent.putExtra("title", this.data.getSinglePole().getColumn_name() == null ? "完美单杆" : this.data.getSinglePole().getColumn_name());
            startActivity(intent);
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
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
        finish();
    }

    @Override
    public void setData(Object data) {
        loadingPageView.checkData(data);
        this.data = (LearnBallBean.DataBean) data;
        LearnBallBean.DataBean dataBean = this.data;
        setDaShi(dataBean.getDashi());
        setXiaoBian(dataBean.getXiaobian());
        setXilie(dataBean.getXilie());
        setPobai(dataBean.getSinglePole());
    }

    private void setPobai(LearnBallBean.DataBean.SinglePoleBean singlePole) {
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(singlePole.getIcon(), pobaiIv));
        pobaiTitleTv.setText(singlePole.getColumn_name());
        List<LearnBallBean.DataBean.SinglePoleBean.DataListBeanXXX> dataList = singlePole.getDataList();
        if (dataList == null || dataList.size() == 0)
            return;
        if (dataList.size() > 0)
            pobaiWbivOne.setData(dataList.get(0));
        else
            pobaiWbivOne.setVisibility(View.GONE);
        if (dataList.size() > 1)
            pobaiWbivTwo.setData(dataList.get(1));
        else {
            pobaiWbivTwo.setVisibility(View.GONE);
            if (dataList.size() == 1)
                pobaiWbivTwo.setVisibility(View.INVISIBLE);
        }
        if (dataList.size() > 2)
            pobaiWbivThree.setData(dataList.get(2));
        else
            pobaiWbivThree.setVisibility(View.GONE);
        if (dataList.size() > 3)
            pobaiWbivFour.setData(dataList.get(3));
        else {
            pobaiWbivFour.setVisibility(View.GONE);
            if (dataList.size() == 3)
                pobaiWbivFour.setVisibility(View.INVISIBLE);
        }
    }

    //设置系列视频数据
    private void setXilie(LearnBallBean.DataBean.XilieBean xilie) {
//        GlideConfigGlobal.loadImageView(xilie.getIcon(),videoIv);
//        Glide.with(getContext()).load(xilie.getIcon()).into(videoIv);
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(xilie.getIcon(), videoIv));
        videoTitleTv.setText(xilie.getColumn_name());
        List<LearnBallBean.DataBean.XilieBean.DataListBeanXX> dataList = xilie.getDataList();
        if (dataList == null || dataList.size() == 0)
            return;
        if (dataList.size() > 0)
            videoWbivOne.setData(dataList.get(0));
        else
            videoWbivOne.setVisibility(View.GONE);
        if (dataList.size() > 1)
            videoWbivTwo.setData(dataList.get(1));
        else {
            videoWbivTwo.setVisibility(View.GONE);
            if (dataList.size() == 1)
                videoWbivTwo.setVisibility(View.INVISIBLE);
        }
        if (dataList.size() > 2)
            videoWbivThree.setData(dataList.get(2));
        else
            videoWbivThree.setVisibility(View.GONE);
        if (dataList.size() > 3)
            videoWbivFour.setData(dataList.get(3));
        else {
            videoWbivFour.setVisibility(View.GONE);
            if (dataList.size() == 3)
                videoWbivFour.setVisibility(View.INVISIBLE);
        }
    }

    //设置小编推荐数据
    private void setXiaoBian(LearnBallBean.DataBean.XiaobianBean xiaobian) {
//        GlideConfigGlobal.loadImageView(xiaobian.getIcon(),pushIv);
//        Glide.with(getContext()).load(xiaobian.getIcon()).into(pushIv);
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(xiaobian.getIcon(), pushIv));
        pushTitleTv.setText(xiaobian.getColumn_name());
        List<LearnBallBean.DataBean.XiaobianBean.DataListBean> dataList = xiaobian.getDataList();
        if (dataList.size() > 0)
            pushWbivOne.setData(dataList.get(0));
        else
            pushWbivOne.setVisibility(View.GONE);
        if (dataList.size() > 1)
            pushWbivTwo.setData(dataList.get(1));
        else {
            pushWbivTwo.setVisibility(View.GONE);
            if (dataList.size() == 1)
                pushWbivTwo.setVisibility(View.INVISIBLE);
        }
        if (dataList.size() > 2)
            pushWbivThree.setData(dataList.get(2));
        else
            pushWbivThree.setVisibility(View.GONE);
        if (dataList.size() > 3)
            pushWbivFour.setData(dataList.get(3));
        else {
            pushWbivFour.setVisibility(View.GONE);
            if (dataList.size() == 3)
                pushWbivFour.setVisibility(View.INVISIBLE);
        }
    }

    //设置大师教学数据
    private void setDaShi(LearnBallBean.DataBean.DashiBean dashi) {
//        GlideConfigGlobal.loadImageView(dashi.getIcon(),teachIv);
        appComponent = ((App) getApplicationContext()).getAppComponent();
        imageLoader = appComponent.imageLoader();
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(dashi.getIcon(), teachIv));
//        Glide.with(getContext()).load(dashi.getIcon()).into(teachIv);
        teachTitleTv.setText(dashi.getColumn_name());
        List<LearnBallBean.DataBean.DashiBean.DataListBeanX> dataList = dashi.getDataList();
        if (dataList.size() > 0)
            teachWbivOne.setData(dataList.get(0));
        else
            teachWbivOne.setVisibility(View.GONE);
        if (dataList.size() > 1)
            teachWbivTwo.setData(dataList.get(1));
        else {
            teachWbivTwo.setVisibility(View.GONE);
            if (dataList.size() == 1)
                teachWbivTwo.setVisibility(View.INVISIBLE);
        }
        if (dataList.size() > 2)
            teachWbivThree.setData(dataList.get(2));
        else
            teachWbivThree.setVisibility(View.GONE);
        if (dataList.size() > 3)
            teachWbivFour.setData(dataList.get(3));
        else {
            teachWbivFour.setVisibility(View.GONE);
            if (dataList.size() == 3)
                teachWbivFour.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

        mPresenter.requestLearnBallList(true);
    }
}
