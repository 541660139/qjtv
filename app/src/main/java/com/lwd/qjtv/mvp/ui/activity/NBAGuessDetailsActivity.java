package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.App;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;

import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.paginate.Paginate;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.usher.greendao_demo.greendao.gen.BetModelBeanDao;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.di.component.DaggerNBAGuessDetailsComponent;
import com.lwd.qjtv.di.module.NBAGuessDetailsModule;
import com.lwd.qjtv.mvp.contract.NBAGuessDetailsContract;
import com.lwd.qjtv.mvp.model.entity.BetModelBean;
import com.lwd.qjtv.mvp.model.entity.GuessDetailsBean;
import com.lwd.qjtv.mvp.presenter.NBAGuessDetailsPresenter;

import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.ui.adapter.GuessDetailsAdapter;
import com.lwd.qjtv.mvp.ui.adapter.GuessDetailsNumAdapter;
import com.lwd.qjtv.view.LoadingPageView;
import com.lwd.qjtv.view.RaceView;


import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class NBAGuessDetailsActivity extends BaseActivity<NBAGuessDetailsPresenter> implements NBAGuessDetailsContract.View, SwipeRefreshLayout.OnRefreshListener, GuessDetailsNumAdapter.CallBackChooseItem {
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    //比分列表
    @BindView(R.id.fragment_live_guess_score_recyclerview)
    RecyclerView mRecyclerView;
    //右竞猜view
    @BindView(R.id.activity_guess_details_right_win_rv)
    RaceView rightWinRv;
    //左竞猜view
    @BindView(R.id.activity_guess_details_left_win_rv)
    RaceView leftWinRv;
    //左头像
    @BindView(R.id.activity_guess_details_left_head_civ)
    CircleImageView leftheadCiv;
    //左姓名
    @BindView(R.id.activity_guess_details_left_name_tv)
    TextView leftNameTv;
    //右头像
    @BindView(R.id.activity_guess_details_right_head_civ)
    CircleImageView rightheadCiv;
    //右姓名
    @BindView(R.id.activity_guess_details_right_name_tv)
    TextView rightNameTv;
    //时间
    @BindView(R.id.activity_guess_details_time_tv)
    TextView timeTv;
    //竞猜局数列表
    @BindView(R.id.activity_guess_details_number_recyclerview)
    RecyclerView numRecyclerView;
    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;
    //左胜负复选框
    @BindView(R.id.fragment_live_guess_left_cb)
    CheckBox leftRb;
    //右胜负复选框
    @BindView(R.id.fragment_live_guess_right_cb)
    CheckBox rightRb;
    //更多按钮
    @BindView(R.id.activity_guess_details_more_tv)
    TextView moreTv;

    @BindView(R.id.fragment_live_guess_left_rate_tv)
    TextView leftRateTv;
    @BindView(R.id.fragment_live_guess_right_rate_tv)
    TextView rightRateTv;
    @BindView(R.id.fragment_live_guess_battle_pb)
    ProgressBar battlePb;
    @BindView(R.id.activity_guess_details_more_ll)
    LinearLayout moreLL;
    @BindView(R.id.activity_guess_details_back_tv)
    TextView backTv;
    @BindView(R.id.activity_guess_details_charge_tv)
    TextView chargeTv;
    @BindView(R.id.activity_guess_details_score_tv)
    TextView scoreTv;
    @BindView(R.id.activity_guess_details_game_num_tv)
    TextView gameNumTv;
    //竞猜id
    private String jc_id;
    //是否可见
    private boolean isVisible;
    //竞猜详情适配器
    private GuessDetailsAdapter a;


    private AppComponent appComponent;
    private ImageLoader imageLoader;
    //竞猜局数适配器
    private GuessDetailsNumAdapter guessDetailsNumAdapter;
    private GuessDetailsBean.DataBean o;
    private BetModelBeanDao winDao;
    private BetModelBeanDao thirdModelBeanDao;

    //胜负选项
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            //true为左 false为右
            boolean b = (boolean) msg.obj;
            if (o == null)
                return;
            GuessDetailsBean.DataBean.GuessWinnerBean guess_winner = o.getGuess_winner();
            BetModelBean moreBetBean = new BetModelBean();
            moreBetBean.setJc_id(guess_winner.getId());
            moreBetBean.setId(Long.parseLong(guess_winner.getId()));
            moreBetBean.setJc_name(o.getGuess_winner().getPlInfo().get(what).getJc_name());
            moreBetBean.setJc_peilv(o.getGuess_winner().getPlInfo().get(what).getJc_peilv());
            moreBetBean.setJc_pl_id(o.getGuess_winner().getPlInfo().get(what).getId() + "");
            moreBetBean.setJc_type(o.getGuess_winner().getJc_type());
            moreBetBean.setLeft_name(o.getGuess_detail().getStarInfo().get(0).getName());
            moreBetBean.setLeft_pic(o.getGuess_detail().getStarInfo().get(0).getThumb_img());
            moreBetBean.setRight_name(o.getGuess_detail().getStarInfo().get(1).getName());
            moreBetBean.setRight_pic(o.getGuess_detail().getStarInfo().get(1).getThumb_img());
            moreBetBean.setMatch_id(o.getGuess_detail().getMatch_id() + "");
            moreBetBean.setMatch_name(o.getGuess_detail().getMatcheName());
            moreBetBean.setOver_time(o.getGuess_detail().getJc_over_time());
            //如果选中
            if (b) {
                //标识分辨 左选中还是右选中
                if (what == 1)
                    leftRb.setChecked(false);
                else if (what == 0)
                    rightRb.setChecked(false);
                winDao.insertOrReplace(moreBetBean);
                showMoreBetDialog();
//                showMessage("加入多选成功");
                //更新多选车状态
                if (getSize() == 0)
                    chargeTv.setText("多选车");
                else
                    chargeTv.setText("多选车(" + getSize() + ")");
            } else {
                //取消选中
                if (o != null && o.getGuess_winner() != null) {
                    //如果左右都没选中
                    if (!(leftRb.isChecked() || rightRb.isChecked()))
                        winDao.delete(moreBetBean);
                    if (getSize() == 0)
                        chargeTv.setText("多选车");
                    else
                        chargeTv.setText("多选车(" + getSize() + ")");
//                    showMessage("删除多选成功");
                }
            }
        }
    };
    private String id;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerNBAGuessDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .nBAGuessDetailsModule(new NBAGuessDetailsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_guess_details_layout; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData() {
        loadingPageView.startLodingAnim();
        setTitle("竞猜详情");
        isVisible = false;
        if (getIntent() != null)
            jc_id = getIntent().getStringExtra("id");
        winDao = WEApplication.getWinDao();
        thirdModelBeanDao = WEApplication.getThirdModelBeanDao();
        appComponent = ((App) getApplicationContext()).getAppComponent();
        imageLoader = appComponent.imageLoader();
        mPresenter.requestGuessDetailsList(jc_id, true);//打开app时自动加载列表
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!Preference.getBoolean(Constant.IS_LOGIN))
            finish();
        //如果数据源不为空
        if (o != null) {
            //初始化 复选框状态
            rightRb.setChecked(false);
            leftRb.setChecked(false);
            rightRb.setVisibility(View.GONE);
            leftRb.setVisibility(View.GONE);
            rightWinRv.setClickable(true);
            leftWinRv.setClickable(true);
            //重新初始化数据库
            initDao();
            //点击返回单选
            backTv.callOnClick();
            mPresenter.requestGuessDetailsList(jc_id, true);//打开app时自动加载列表
        }
    }

    //初始化数据库,判断是否存在历史选中记录,如果存在显示出多选框
    private void initDao() {
        List<BetModelBean> betModelBeen = winDao.loadAll();
        for (int i = 0; i < betModelBeen.size(); i++) {
            BetModelBean betModelBean = betModelBeen.get(i);
            if (betModelBean != null && o != null) {
                //如果数据库存在竞猜id和此竞猜详情相同的id
                if (o.getGuess_detail().getId().equals(betModelBean.getId() + "")) {
                    //变成多选
                    moreTv.callOnClick();
                    String jc_pl_id = betModelBean.getJc_pl_id() + "";
                    int id = o.getGuess_winner().getPlInfo().get(0).getId();
                    //如果竞猜分类id相同
                    if (jc_pl_id.equals(id + ""))
                        leftRb.setChecked(true);
                    else
                        rightRb.setChecked(true);
                }
            }
        }
        List<BetModelBean> betModelBeen1 = thirdModelBeanDao.loadAll();
        for (int i = 0; i < betModelBeen1.size(); i++) {
            BetModelBean betModelBean = betModelBeen1.get(i);
            if (betModelBean != null && o != null && o.getGuess_gameNumber() != null && o.getGuess_gameNumber().getId() != null) {
                if (o.getGuess_gameNumber().getId().equals(betModelBean.getId() + "")) {
                    //点击进入多选状态
                    moreTv.callOnClick();
                    if (guessDetailsNumAdapter != null)
                        guessDetailsNumAdapter.setChoose(betModelBean.getJc_pl_id());
                }
            }
        }
    }


    private void initListener() {
        loadingPageView.setClickReload(() -> mPresenter.requestGuessDetailsList(jc_id, true));
        moreTv.setOnClickListener(view -> {
            //进入多选状态
            isVisible = true;
            //显示多选视图
            moreLL.setVisibility(View.VISIBLE);
            //显示复选框
            rightRb.setVisibility(View.VISIBLE);
            leftRb.setVisibility(View.VISIBLE);
            //隐藏进入多选按钮
            moreTv.setVisibility(View.GONE);
            //左下注框点击
            leftWinRv.setOnClickListener(v -> {
//                //如果是多选状态 则执行复选框操作
//                if (isVisible) leftRb.setChecked(!leftRb.isChecked());
//                    //如果是单选状态 则执行点击事件
//                else
                    leftWinRv.setOnItemClick();
            });
            //右下注框点击
            rightWinRv.setOnClickListener(v -> {
//                //如果是多选状态 则执行复选框操作
//                if (isVisible) rightRb.setChecked(!rightRb.isChecked());
//                    //如果是单选状态 则执行点击事件
//                else
                    leftWinRv.setOnItemClick();
            });
            if (guessDetailsNumAdapter != null)
                guessDetailsNumAdapter.setVisible(true);
        });
        backTv.setOnClickListener(v -> {
            moreLL.setVisibility(View.GONE);
            rightRb.setVisibility(View.GONE);
            leftRb.setVisibility(View.GONE);
            moreTv.setVisibility(View.VISIBLE);
            isVisible = false;
            if (guessDetailsNumAdapter != null)
                guessDetailsNumAdapter.setVisible(false);
        });
        //进入多选
        chargeTv.setOnClickListener(v -> {
                    Intent intent = new Intent(this, MoreBetActivity.class);
                    startActivity(intent);
                }
        );
        //右复选框监听
        rightRb.setOnCheckedChangeListener((compoundButton, b) -> {
            Message message = new Message();
            message.what = 1;
            message.obj = b;
            handler.sendMessage(message);
        });
        //左复选框监听
        leftRb.setOnCheckedChangeListener((compoundButton, b) -> {
            Message message = new Message();
            message.what = 0;
            message.obj = b;
            handler.sendMessage(message);
        });
    }

    @Override
    public void onRefresh() {
        mPresenter.requestGuessDetailsList(jc_id, true);
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
//        mSwipeRefreshLayout.setOnRefreshListener(this);
        UiUtils.configRecycleView(mRecyclerView, new GridLayoutManager(this, 2));
    }


    @Override
    public void showLoading() {
        Timber.tag(TAG).w("showLoading");
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread());
//                .subscribe(integer -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideLoading() {
        Timber.tag(TAG).w("hideLoading");
//        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {
//        UiUtils.SnackbarText(message);
        UiUtils.makeText(this, message);
    }

    @Override
    public void launchActivity(Intent intent) {
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void setData(Object o) {
        loadingPageView.checkData(o);
        this.o = (GuessDetailsBean.DataBean) o;
        GuessDetailsBean.DataBean dataBean = this.o;
        if (((GuessDetailsBean.DataBean) o).getGuess_score().getPlInfo().size() == 0)
            scoreTv.setVisibility(View.GONE);
        detailsInfo(dataBean);
        winMod(dataBean);
        guessMod(dataBean);
        initDao();
        GuessDetailsBean.DataBean.GuessDetailBean guess_detail = this.o.getGuess_detail();
        List<GuessDetailsBean.DataBean.GuessDetailBean.StarInfoBean> starInfo = guess_detail.getStarInfo();
        GuessDetailsBean.DataBean.GuessDetailBean.StarInfoBean leftStar = starInfo.get(0);
        GuessDetailsBean.DataBean.GuessDetailBean.StarInfoBean rightStar = starInfo.get(1);
        String title = leftStar.getName() + "  VS  " + rightStar.getName();
        a.setApiparm(title, this.o.getGuess_detail().getMatch_id() + "", this.o.getGuess_score().getJc_type(), this.o.getGuess_score().getId());
        chargeTv.setText("多选车(" + getSize() + ")");
    }

    //竞猜详情信息
    private void detailsInfo(GuessDetailsBean.DataBean dataBean) {
        GuessDetailsBean.DataBean.GuessDetailBean guess_detail = dataBean.getGuess_detail();
        timeTv.setText(guess_detail.getStart_time());
        List<GuessDetailsBean.DataBean.GuessDetailBean.StarInfoBean> starInfo = guess_detail.getStarInfo();
        GuessDetailsBean.DataBean.GuessDetailBean.StarInfoBean leftStarInfoBean = starInfo.get(0);
        GuessDetailsBean.DataBean.GuessDetailBean.StarInfoBean rightStarInfoBean = starInfo.get(1);
        setRate(leftStarInfoBean, rightStarInfoBean);
        leftNameTv.setText(leftStarInfoBean.getName());
        rightNameTv.setText(rightStarInfoBean.getName());
        Glide.with(this).load(leftStarInfoBean.getThumb_img()).into(leftheadCiv);
        Glide.with(this).load(rightStarInfoBean.getThumb_img()).into(rightheadCiv);
    }

    //设置比分信息
    private void setRate(GuessDetailsBean.DataBean.GuessDetailBean.StarInfoBean leftStarInfoBean, GuessDetailsBean.DataBean.GuessDetailBean.StarInfoBean rightStarInfoBean) {
        int leftWinCount = leftStarInfoBean.getWinCount();
        int rightWinCount = rightStarInfoBean.getWinCount();
        double leftWinRate;
        double rightWinRate;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        if (leftWinCount == 0 && rightWinCount == 0) {
            leftWinRate = 50;
            rightWinRate = 50;
        } else if (leftWinCount == 0) {
            leftWinRate = 0;
            rightWinRate = 100;
        } else if (rightWinCount == 0) {
            leftWinRate = 100;
            rightWinRate = 0;
        } else {
            double lf = (float) leftWinCount / (float) (rightWinCount + leftWinCount);
            double rg = (float) rightWinCount / (float) (rightWinCount + leftWinCount);
            leftWinRate = lf * 100;
            rightWinRate = rg * 100;
        }
        String leftFormat = decimalFormat.format(leftWinRate);
        String rightFormat = decimalFormat.format(rightWinRate);
        leftRateTv.setText(leftFormat + "%" + "(" + leftWinCount + "胜)");
        rightRateTv.setText(rightFormat + "%" + "(" + rightWinCount + "胜)");
        battlePb.setProgress((int) leftWinRate);
    }

    //输赢模块
    private void winMod(GuessDetailsBean.DataBean dataBean) {
        GuessDetailsBean.DataBean.GuessWinnerBean guess_winner = dataBean.getGuess_winner();
        GuessDetailsBean.DataBean.GuessWinnerBean.PlInfoBean leftWinBean = guess_winner.getPlInfo().get(0);
        GuessDetailsBean.DataBean.GuessWinnerBean.PlInfoBean rightWinBean = guess_winner.getPlInfo().get(1);
        GuessDetailsBean.DataBean.GuessDetailBean guess_detail = dataBean.getGuess_detail();
        List<GuessDetailsBean.DataBean.GuessDetailBean.StarInfoBean> starInfo = guess_detail.getStarInfo();
        GuessDetailsBean.DataBean.GuessDetailBean.StarInfoBean leftStar = starInfo.get(0);
        GuessDetailsBean.DataBean.GuessDetailBean.StarInfoBean rightStar = starInfo.get(1);
        String title = leftStar.getName() + "  VS  " + rightStar.getName();
        setWinnerDataDetails(leftWinRv, guess_winner, leftWinBean, guess_detail, title, "左胜");
        setWinnerDataDetails(rightWinRv, guess_winner, rightWinBean, guess_detail, title, "左负");
    }

    //设置输赢下注口信息
    private void setWinnerDataDetails(RaceView raceView, GuessDetailsBean.DataBean.GuessWinnerBean guess_winner, GuessDetailsBean.DataBean.GuessWinnerBean.PlInfoBean leftWinBean, GuessDetailsBean.DataBean.GuessDetailBean guess_detail, String title, String win) {
        raceView.setData(leftWinBean.getJc_peilv(), win, leftWinBean.getId() + "");
        raceView.setApiParam(title, leftWinBean.getJc_name(), leftWinBean.getJc_peilv(), guess_detail.getMatch_id() + "", guess_winner.getId(), leftWinBean.getId() + "", guess_winner.getJc_type());
    }

    //竞猜局数
    private void guessMod(GuessDetailsBean.DataBean dataBean) {
        GuessDetailsBean.DataBean.GuessGameNumberBean guess_gameNumber = dataBean.getGuess_gameNumber();
        if (guess_gameNumber == null)
            return;
        List<GuessDetailsBean.DataBean.GuessGameNumberBean.PlInfoBeanX> plInfo = guess_gameNumber.getPlInfo();
        if (plInfo == null || plInfo.size() == 0)
            gameNumTv.setVisibility(View.GONE);
        guessDetailsNumAdapter = new GuessDetailsNumAdapter(plInfo);
        guessDetailsNumAdapter.setApiParams(dataBean);
        numRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        guessDetailsNumAdapter.setCallback(this);
        numRecyclerView.setAdapter(guessDetailsNumAdapter);
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        a = (GuessDetailsAdapter) adapter;
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
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mRxPermissions = null;
        this.mPaginate = null;
    }

    //竞猜局数item点击事件
    @Override
    public void chooseItem(GuessDetailsBean.DataBean.GuessGameNumberBean.PlInfoBeanX plInfoBeanX, boolean b) {
        GuessDetailsBean.DataBean.GuessGameNumberBean guess_winner = o.getGuess_gameNumber();
        BetModelBean moreBetBean = new BetModelBean();
        moreBetBean.setJc_id(guess_winner.getId());
        moreBetBean.setId(Long.parseLong(guess_winner.getId()));
        moreBetBean.setJc_name(plInfoBeanX.getJc_name());
        moreBetBean.setJc_peilv(plInfoBeanX.getJc_peilv());
        moreBetBean.setJc_pl_id(plInfoBeanX.getId() + "");
        moreBetBean.setJc_type(o.getGuess_gameNumber().getJc_type());
        moreBetBean.setLeft_name(o.getGuess_detail().getStarInfo().get(0).getName());
        moreBetBean.setLeft_pic(o.getGuess_detail().getStarInfo().get(0).getThumb_img());
        moreBetBean.setRight_name(o.getGuess_detail().getStarInfo().get(1).getName());
        moreBetBean.setRight_pic(o.getGuess_detail().getStarInfo().get(1).getThumb_img());
        moreBetBean.setMatch_id(o.getGuess_detail().getMatch_id() + "");
        moreBetBean.setMatch_name(o.getGuess_detail().getMatcheName());
        moreBetBean.setOver_time(o.getGuess_detail().getJc_over_time());
        if (b) {
            id = guess_winner.getId();
            thirdModelBeanDao.insertOrReplace(moreBetBean);
            if (getSize() == 0)
                chargeTv.setText("多选车");
            else
                chargeTv.setText("多选车(" + getSize() + ")");
            showMoreBetDialog();
//            showMessage("加入多选成功");
        } else {
            thirdModelBeanDao.delete(moreBetBean);
            if (getSize() == 0)
                chargeTv.setText("多选车");
            else
                chargeTv.setText("多选车(" + getSize() + ")");
            showMessage("删除多选成功");
        }
    }

    private int getSize() {
        List<BetModelBean> betModelBeen = thirdModelBeanDao.loadAll();
        List<BetModelBean> betModelBeen1 = winDao.loadAll();
        return betModelBeen.size() + betModelBeen1.size();
    }

    private void showMoreBetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("加入购物车成功，还可继续添加其它场次投注项至多选车");
        builder.setNegativeButton("确定", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.create().show();
    }

}
