package com.lwd.qjtv.mvp.ui.fragment.other;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.ClickMoreUtils;
import com.lwd.qjtv.app.utils.SoftInputUtils;
import com.lwd.qjtv.di.component.DaggerCommentMessageComponent;
import com.lwd.qjtv.di.module.CommentMessageModule;
import com.lwd.qjtv.mvp.contract.CommentMessageContract;
import com.lwd.qjtv.mvp.presenter.CommentMessagePresenter;
import com.lwd.qjtv.view.CustomLoadingListItemCreator;
import com.lwd.qjtv.view.EmojiBoard;
import com.paginate.Paginate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/19.
 */

public class CommentMessageFragment extends BaseFragment<CommentMessagePresenter> implements CommentMessageContract.View, OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_comment_message_edt)
    EditText messageEdt;
    @BindView(R.id.swipeRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.fragment_comment_message_send_tv)
    TextView sendTv;
    @BindView(R.id.fragment_comment_message_emoj_iv)
    ImageView emojIv;
    @BindView(R.id.input_emoji_board)
    EmojiBoard emojiBoard;
    @BindView(R.id.fragment_comment_message_tip_tv)
    TextView tipTv;
    @BindView(R.id.fragment_comment_message_bottom_ll)
    LinearLayout msgBottomLl;

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            PopupWindow popupWindow = new PopupWindow(getContext());
//            emojiBoard = new EmojiBoard(getContext(), null);
//            popupWindow.setContentView(emojiBoard);
//            popupWindow.setAnimationStyle(AnimBottomInOut);
////            setPopConfig(popupWindow);
////            popupWindow.show(msgBottomLl);
////            popupWindow.showAtLocation(rootView,0,100,Gravity.BOTTOM);
////            showUp(popupWindow, msgBottomLl);
//            emojiBoard.setItemClickListener(code -> {
//                if (code.equals("/DEL")) {
//                    messageEdt.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
//                } else {
//                    messageEdt.getText().insert(messageEdt.getSelectionStart(), code);
//                }
//            });
//        }
//    };


    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    //    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String videoId, videoType;
    private int measuredHeight;
    private int measuredWidth;
    private View rootView;

    public static CommentMessageFragment newInstance() {
        CommentMessageFragment fragment = new CommentMessageFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerCommentMessageComponent
                .builder()
                .appComponent(appComponent)
                .commentMessageModule(new CommentMessageModule(this))//请将CommentMessageModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_comment_message_layout, container, false);
//        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.zq_refreshlayout);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        return rootView;
    }

    @Override
    public void initData() {
        initListener();
    }

    private void initListener() {
        sendTv.setOnClickListener(view -> {
            String trim = messageEdt.getText().toString().trim();
            if (messageEdt.getText().toString() == null || trim.length() == 0 || "".equals(messageEdt.getText().toString())) {
                showMessage("评论不能为空~");
                return;
            }
            try {
                SoftInputUtils.hideSoftInput(((Activity) getContext()).getWindow().getDecorView(), getContext());
                emojiBoard.setVisibility(View.GONE);
                String encode = URLEncoder.encode(trim, "utf-8");
                mPresenter.addMessage(videoId, videoType, encode);
                messageEdt.setText("");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        messageEdt.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (ClickMoreUtils.isFastClick()) {
                return false;
            }
            if (i == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                String trim = messageEdt.getText().toString().trim();
                if (messageEdt.getText().toString() == null || trim.length() == 0 || "".equals(messageEdt.getText().toString())) {
                    showMessage("评论不能为空~");
                    return false;
                }
                try {
                    String encode = URLEncoder.encode(trim, "utf-8");
                    SoftInputUtils.hideSoftInput(((Activity) getContext()).getWindow().getDecorView(), getContext());
                    mPresenter.addMessage(videoId, videoType, encode);
                    messageEdt.setText("");
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
//            handler.sendEmptyMessageDelayed(1, 80);
//            emojiBoard.setVisibility(emojiBoard.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
//            emojIv.setSelected(emojiBoard.getVisibility() == View.VISIBLE);
        });
        emojiBoard.setItemClickListener(code -> {
            if (code.equals("/DEL")) {
                messageEdt.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            } else {
                messageEdt.getText().insert(messageEdt.getSelectionStart(), code);
            }
        });
        emojiBoard.setOnFocusChangeListener((view, b) -> {
            if (b) {
//                emojiBoard.setVisibility(View.VISIBLE);
            } else {
                emojiBoard.setVisibility(View.GONE);
            }
        });
        rootView.setOnTouchListener((v, event) -> {
            emojiBoard.setVisibility(View.GONE);
            SoftInputUtils.hideSoftInput(((Activity) getContext()).getWindow().getDecorView(), getContext());
            return true;
        });
//        msgBottomLl.setOnClickListener(view -> emojiBoard.setVisibility(View.GONE));
    }


//    private void setPopConfig(PopupWindow popConfig) {
////        this.setContentView(mDataView);//设置要显示的视图
//        popConfig.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        popConfig.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        // 设置弹出窗体可点击
//        popConfig.setFocusable(true);
//        ColorDrawable dw = new ColorDrawable(0xcc000000);
//        popConfig.setBackgroundDrawable(dw);
//        popConfig.setOutsideTouchable(true);// 设置外部触摸会关闭窗口
//
//        //获取自身的长宽高
//        popConfig.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        measuredHeight = popConfig.getContentView().getMeasuredHeight();
//        measuredWidth = popConfig.getContentView().getMeasuredWidth();
//    }

    public void showUp(PopupWindow popupWindow, View v) {
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //在控件上方显示
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0]) - measuredWidth / 2, location[1] - measuredHeight);
    }

    public void setIDandType(String videoId, String videoType) {
        this.videoId = videoId;
        this.videoType = videoType;
        mPresenter.requestCommentMessageList(true, videoId, videoType);
    }

    @Override
    public void setData(Object data) {
        if (data instanceof List && ((List) data).size() == 0)
            tipTv.setVisibility(View.VISIBLE);
        else
            tipTv.setVisibility(View.GONE);

    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(WEApplication.getContext()));
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
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.requestCommentMessageList(false, videoId, videoType);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestCommentMessageList(true, videoId, videoType);
    }

}
