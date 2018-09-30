package com.lwd.qjtv.mvp.ui.fragment.star;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.ClickMoreUtils;
import com.lwd.qjtv.app.utils.SoftInputUtils;
import com.lwd.qjtv.di.component.DaggerStarIntroduceComponent;
import com.lwd.qjtv.di.module.StarIntroduceModule;
import com.lwd.qjtv.mvp.contract.StarIntroduceContract;
import com.lwd.qjtv.mvp.model.entity.StarCommentBean;
import com.lwd.qjtv.mvp.model.entity.StarDetailsBean;
import com.lwd.qjtv.mvp.presenter.StarIntroducePresenter;
import com.lwd.qjtv.mvp.ui.adapter.StarCommentAdapter;
import com.lwd.qjtv.mvp.ui.holder.StarCommentItemHolder;
import com.lwd.qjtv.view.CustomLoadingListItemCreator;
import com.lwd.qjtv.view.EmojiBoard;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class StarIntroduceFragment extends BaseFragment<StarIntroducePresenter> implements StarIntroduceContract.View, OnRefreshListener, OnLoadmoreListener, StarCommentItemHolder.LikeCallBack {
    @BindView(R.id.fragment_star_introduce_recyclerview)
    RecyclerView mRecyclerView;
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
    @BindView(R.id.fragment_comment_message_emoj_iv)
    ImageView emojIv;
    @BindView(R.id.fragment_star_introduce_xl_iv)
    ImageView xlIv;
    @BindView(R.id.fragment_comment_message_bottom_ll)
    LinearLayout msgBottomLl;
    @BindView(R.id.input_emoji_board)
    EmojiBoard emojiBoard;
//    @BindView(R.id.fragment_star_introduce_refreshlayout)
//    SmartRefreshLayout mSwipeRefreshLayout;

    private static String starId;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    private boolean flag;
    private int measuredHeight;
    private int measuredWidth;


    public static StarIntroduceFragment newInstance() {
        StarIntroduceFragment fragment = new StarIntroduceFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerStarIntroduceComponent
                .builder()
                .appComponent(appComponent)
                .starIntroduceModule(new StarIntroduceModule(this))//请将StarIntroduceModule()第一个首字母改为小写
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
        starId = getArguments().getString("starId");
        mPresenter.getStarIntro(starId, true);
        mPresenter.getStarComment(starId, true);//打开app时自动加载列表
        initListener();
    }

    private void initListener() {
//        mSwipeRefreshLayout.setOnLoadmoreListener(this);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
        xlIv.setOnClickListener(view -> {
            if (flag) {
                flag = false;
                descTv.setEllipsize(null);
                descTv.setSingleLine(flag);
            } else {
                flag = true;
                descTv.setEllipsize(TextUtils.TruncateAt.END);
                descTv.setLines(3);
            }
        });
        sendTv.setOnClickListener(view -> {
            String trim = textEdt.getText().toString().trim();
            if (textEdt.getText().toString() == null || trim.length() == 0 || "".equals(textEdt.getText().toString())) {
                showMessage("评论不能为空~");
                return;
            }
            try {
                String encode = URLEncoder.encode(trim, "utf-8");
                mPresenter.addStarComment(starId, encode);
                textEdt.setText("");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
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
                try {
                    String encode = URLEncoder.encode(trim, "utf-8");
                    mPresenter.addStarComment(starId, encode);
                    textEdt.setText("");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        });
        emojIv.setOnClickListener(view -> {
            SoftInputUtils.hideSoftInput(((Activity) getContext()).getWindow().getDecorView(), getContext());
            emojiBoard.setVisibility(View.VISIBLE);
        });
        emojiBoard.setItemClickListener(code -> {
            if (code.equals("/DEL")) {
                textEdt.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            } else {
                textEdt.getText().insert(textEdt.getSelectionStart(), code);
            }
        });
        emojiBoard.setOnFocusChangeListener((view, b) -> {
            if (b) {
//                emojiBoard.setVisibility(View.VISIBLE);
            } else {
                emojiBoard.setVisibility(View.GONE);
            }
        });
    }

    private void setPopConfig(PopupWindow popConfig) {
//        this.setContentView(mDataView);//设置要显示的视图
        popConfig.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popConfig.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击
        popConfig.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xcc000000);
        popConfig.setBackgroundDrawable(dw);
        popConfig.setOutsideTouchable(true);// 设置外部触摸会关闭窗口

        //获取自身的长宽高
        popConfig.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        measuredHeight = popConfig.getContentView().getMeasuredHeight();
        measuredWidth = popConfig.getContentView().getMeasuredWidth();
    }

    public void showUp(PopupWindow popupWindow, View v) {
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //在控件上方显示
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0]) - measuredWidth / 2, location[1] - measuredHeight);
    }


    @Override
    public void setData(Object data) {
        StarDetailsBean.DataBean starDetailsBean = (StarDetailsBean.DataBean) data;
        titleTv.setText(starDetailsBean.getName());
        nameTv.setText(starDetailsBean.getEnglish_name());
        descTv.setText(starDetailsBean.getIntro());
    }


    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
//        mSwipeRefreshLayout.setOnRefreshListener(this);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(WEApplication.getContext()));
    }


    @Override
    public void showLoading() {
        Timber.tag(TAG).w("showLoading");
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
//                    mSwipeRefreshLayout.setRefreshing(true)
                });
    }

    @Override
    public void hideLoading() {
        Timber.tag(TAG).w("hideLoading");
//        mSwipeRefreshLayout.finishRefresh();
//        mSwipeRefreshLayout.setRefreshing(false);
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
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        StarCommentAdapter starCommentAdapter = (StarCommentAdapter) adapter;
        starCommentAdapter.setLikeCallback(this);
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
//        mSwipeRefreshLayout.finishLoadmore();
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

            mPaginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(10)
                    .setLoadingListItemCreator(new CustomLoadingListItemCreator())
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
    public void clickLikeButton(StarCommentBean.DataBean.CommentsListBean data) {
        mPresenter.likeComment(data.getStar_id(), data.getId());
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.getStarComment(starId, false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.getStarComment(starId, true);
    }
}
