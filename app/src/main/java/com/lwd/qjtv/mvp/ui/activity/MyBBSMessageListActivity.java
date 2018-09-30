package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.autolayout.AutoToolbar;
import com.lwd.qjtv.R;
import com.lwd.qjtv.di.component.DaggerMyBBSMessageListComponent;
import com.lwd.qjtv.di.module.MyBBSMessageListModule;
import com.lwd.qjtv.mvp.contract.MyBBSMessageListContract;
import com.lwd.qjtv.mvp.model.entity.BBSMessageListBean;
import com.lwd.qjtv.mvp.presenter.MyBBSMessageListPresenter;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MyBBSMessageListActivity extends BaseActivity<MyBBSMessageListPresenter> implements MyBBSMessageListContract.View {


    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.toolbar_back)
    AutoRelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.activity_my_bbsmessage_comment_civ)
    CircleImageView activityMyBbsmessageCommentCiv;
    @BindView(R.id.activity_my_bbsmessage_comment_content_tv)
    TextView activityMyBbsmessageCommentContentTv;
    @BindView(R.id.activity_my_bbsmessage_like_civ)
    CircleImageView activityMyBbsmessageLikeCiv;
    @BindView(R.id.activity_my_bbsmessage_like_tv)
    TextView activityMyBbsmessageLikeTv;
    @BindView(R.id.activity_my_bbsmessage_like_rl)
    RelativeLayout likeRl;
    @BindView(R.id.activity_my_bbsmessage_comment_rl)
    RelativeLayout commentRl;
    @BindView(R.id.activity_my_bbsmessage_comment_num_tv)
    TextView commentNumTv;
    @BindView(R.id.activity_my_bbsmessage_like_num_tv)
    TextView likeNumTv;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMyBBSMessageListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myBBSMessageListModule(new MyBBSMessageListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_my_bbsmessage_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getMyBBSMessageData();
    }

    @Override
    public void initData() {
        setTitle("我的消息");
        initListener();
    }

    private void initListener() {
        likeRl.setOnClickListener(v -> startActivity(new Intent(this, MyLikeListActivity.class)));
        commentRl.setOnClickListener(v -> startActivity(new Intent(this, MyCommentListActivity.class)));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    public void setData(Object o) {
        if (o instanceof Integer) {

        } else if (o instanceof BBSMessageListBean.DataBean) {
            BBSMessageListBean.DataBean dataBean = (BBSMessageListBean.DataBean) o;
            activityMyBbsmessageCommentContentTv.setText(dataBean.getCard().getContent() == null ? "暂无更多评论" : dataBean.getCard().getContent());
            activityMyBbsmessageLikeTv.setText(dataBean.getZan().getUser() == null ? "暂无更多点赞" : dataBean.getZan().getUser());
            int commentNum = Integer.valueOf(dataBean.getCard().getNum());
//            评论数不超过100
            if (commentNum < 100) {
                commentNumTv.setText(dataBean.getCard().getNum() == null ? "0" : dataBean.getCard().getNum());
            } else {
                commentNumTv.setText("...");
            }
            commentNumTv.setVisibility(dataBean.getCard().getNum() == null || dataBean.getCard().getNum().equals("0") ? View.GONE : View.VISIBLE);
            int likeNum = Integer.valueOf(dataBean.getZan().getNum());
//            点赞数不超过100
            if (likeNum < 100) {
                likeNumTv.setText(dataBean.getZan().getNum() == null ? "0" : dataBean.getZan().getNum());
            } else {
                likeNumTv.setText("...");
            }
            likeNumTv.setVisibility(dataBean.getZan().getNum() == null || dataBean.getZan().getNum().equals("0") ? View.GONE : View.VISIBLE);
        }
    }
}
