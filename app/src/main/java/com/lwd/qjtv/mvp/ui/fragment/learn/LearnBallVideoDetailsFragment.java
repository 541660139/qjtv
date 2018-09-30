package com.lwd.qjtv.mvp.ui.fragment.learn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.ClickMoreUtils;
import com.lwd.qjtv.di.component.DaggerLearnBallVideoDetailsComponent;
import com.lwd.qjtv.di.module.LearnBallVideoDetailsModule;
import com.lwd.qjtv.mvp.contract.LearnBallVideoDetailsContract;
import com.lwd.qjtv.mvp.model.entity.LearnBallDetailsBean;
import com.lwd.qjtv.mvp.model.entity.VideoDetailsNewBean;
import com.lwd.qjtv.mvp.presenter.LearnBallVideoDetailsPresenter;
import com.lwd.qjtv.mvp.ui.holder.CommentMessageItemHolder;
import com.lwd.qjtv.view.CustomLoadingListItemCreator;
import com.paginate.Paginate;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/13.
 */

public class LearnBallVideoDetailsFragment extends BaseFragment<LearnBallVideoDetailsPresenter> implements LearnBallVideoDetailsContract.View, OnRefreshListener,OnLoadmoreListener, CommentMessageItemHolder.VideoCommentLike {

    @BindView(R.id.fragment_star_introduce_title_tv)
    TextView titleTv;
    @BindView(R.id.fragment_star_introduce_name_tv)
    TextView nameTv;
    @BindView(R.id.fragment_star_introduce_desc_tv)
    TextView descTv;
    @BindView(R.id.fragment_star_introduce_send_tv)
    TextView sendTv;
    @BindView(R.id.fragment_star_introduce_text_edt)
    EditText textEdt;
    @BindView(R.id.fragment_star_introduce_recyclerview)
    RecyclerView introRecyclerView;
//    @BindView(R.id.fragment_star_introduce_refreshlayout)
//    SmartRefreshLayout smartRefreshLayout;

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    private RecyclerView mRecyclerView;
    //    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String starId, videoId, v_type, op;

    public static LearnBallVideoDetailsFragment newInstance() {
        LearnBallVideoDetailsFragment fragment = new LearnBallVideoDetailsFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerLearnBallVideoDetailsComponent
                .builder()
                .appComponent(appComponent)
                .learnBallVideoDetailsModule(new LearnBallVideoDetailsModule(this))//请将LearnBallVideoDetailsModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_star_introduce, container, false);
        return view;
    }

    @Override
    public void initData() {
        getParseArguments();
        mPresenter.getDetailsInfo(starId);//打开app时自动加载列表
        mPresenter.requestCommentMessageList(true, videoId, v_type);
        initListener();
    }

    private void initListener() {
        sendTv.setOnClickListener(view -> {
            String trim = textEdt.getText().toString().trim();
            if (textEdt.getText().toString() == null || trim.length() == 0 || "".equals(textEdt.getText().toString())) {
                showMessage("评论不能为空~");
                return;
            }
            mPresenter.addMessage(videoId, v_type, trim);
            textEdt.setText("");
        });
        textEdt.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (ClickMoreUtils.isFastClick()) {
                return false;
            }
            if (i == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                String trim = textEdt.getText().toString().trim();
                if (textEdt.getText().toString() == null || trim.length() == 0 || "".equals(textEdt.getText().toString())) {
                    showMessage("评论不能为空~");
                    return false;
                }
                mPresenter.addMessage(videoId, v_type, trim);
                textEdt.setText("");
                return true;
            }
            return false;
        });
    }

    @Override
    public void setData(Object data) {
        LearnBallDetailsBean.DataBean dataBean = (LearnBallDetailsBean.DataBean) data;
        titleTv.setText(dataBean.getName());
        nameTv.setText(dataBean.getEnglish_name());
        descTv.setText(dataBean.getIntro());
    }

    private void getParseArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            starId = arguments.getString("starId");
            videoId = arguments.getString("videoId");
            v_type = arguments.getString("v_type");
        }
    }


    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
//        smartRefreshLayout.setOnRefreshListener(this);
//        smartRefreshLayout.setOnLoadmoreListener(this);
        UiUtils.configRecycleView(introRecyclerView, new LinearLayoutManager(getContext()));
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
//        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void showMessage(String message) {
//        UiUtils.SnackbarText(message);

        UiUtils.makeText(getContext(),message);
    }

    @Override
    public void launchActivity(Intent intent) {
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
//        finish();
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        introRecyclerView.setAdapter(adapter);
//        CommentMessageAdapter commentMessageAdapter = (CommentMessageAdapter) adapter;
//        commentMessageAdapter.setLikeCallback(this);
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
//        smartRefreshLayout.finishLoadmore();
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

            mPaginate = Paginate.with(introRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(10).setLoadingListItemCreator(new CustomLoadingListItemCreator())
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    public void onDestroy() {
        DefaultAdapter.releaseAllHolder(introRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mRxPermissions = null;
        this.mPaginate = null;
    }



    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.requestCommentMessageList(false, starId, v_type);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestCommentMessageList(true, starId, v_type);
    }

    @Override
    public void likeVideoComment(VideoDetailsNewBean.DataBean.VideoReviewBean data) {

    }
}
