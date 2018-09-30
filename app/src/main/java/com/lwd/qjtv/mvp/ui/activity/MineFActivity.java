package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.di.component.DaggerMineFComponent;
import com.lwd.qjtv.di.module.MineFModule;
import com.lwd.qjtv.mvp.contract.MineFContract;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.FansOrFollerBean;
import com.lwd.qjtv.mvp.presenter.MineFPresenter;
import com.lwd.qjtv.mvp.ui.adapter.CommunityAllAdapter;
import com.lwd.qjtv.mvp.ui.adapter.FansOrFollowAdapter;
import com.lwd.qjtv.view.LoadingPageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MineFActivity extends BaseActivity<MineFPresenter> implements MineFContract.View, OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.iv_gz_back)
    ImageView iv_gz_back;
    @BindView(R.id.tv_title_gz_fs)
    TextView tv_title_gz_fs;

    @BindView(R.id.edit_search_gz_fs)
    EditText edit_search_gz_fs;
    @BindView(R.id.receiver_gzorfs)
    RecyclerView receiver_gzorfs;

    @BindView(R.id.iv_delete_search)
    ImageView iv_delete_search;


    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;

    @BindView(R.id.fragment_community_all_smartrefreshlayout)
    SmartRefreshLayout fragment_community_all_smartrefreshlayout;


    private int type;


    private List<FansOrFollerBean.DataBean> m_list;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMineFComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mineFModule(new MineFModule(this))
                .build()
                .inject(this);
    }


    @Override
    public int initView() {
        return R.layout.activity_mine_f; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData() {
        loadingPageView.startLodingAnim();
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
//            关注
            tv_title_gz_fs.setText("我的关注");
            mPresenter.getPersonalAll(1, true, "");


        } else {
//            粉丝
            tv_title_gz_fs.setText("我的粉丝");
            mPresenter.getPersonalAll(2, true, "");
        }
        initList();


    }


    private void initList() {
        fragment_community_all_smartrefreshlayout.setOnRefreshListener(this);
        fragment_community_all_smartrefreshlayout.setEnableLoadmore(true);
        fragment_community_all_smartrefreshlayout.setOnLoadmoreListener(this);


        edit_search_gz_fs.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override

            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {


                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {

                    mPresenter.getPersonalAll(type, true, edit_search_gz_fs.getText().toString().trim());
                }
                return false;

            }

        });


        edit_search_gz_fs.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String trim = edit_search_gz_fs.getText().toString().trim();
                if (trim.length() > 0) {
                    iv_delete_search.setVisibility(View.VISIBLE);
                } else {
                    iv_delete_search.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听


            }
        });


        loadingPageView.setClickReload(new LoadingPageView.ClickReloadCallback() {
            @Override
            public void clickReload() {
                if (type == 1) {
                    mPresenter.getPersonalAll(1, true, "");
                } else {
                    mPresenter.getPersonalAll(2, true, "");
                }
            }
        });
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        fragment_community_all_smartrefreshlayout.finishRefresh();

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
    public void setData(Object o) {
        loadingPageView.checkData(o);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @OnClick({R.id.iv_gz_back, R.id.iv_delete_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_gz_back:
                finish();
                break;
            case R.id.iv_delete_search:
                edit_search_gz_fs.setText("");
                edit_search_gz_fs.setHint("搜索我的关注");
                iv_delete_search.setVisibility(View.GONE);
                break;
            default:

        }
    }

    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {
        fragment_community_all_smartrefreshlayout.finishLoadmore();

    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {

        FansOrFollowAdapter fansOrFollowAdapter = (FansOrFollowAdapter) adapter;
        UiUtils.configRecycleView(receiver_gzorfs, new LinearLayoutManager(this));
        receiver_gzorfs.setAdapter(adapter);
        fansOrFollowAdapter.setOnItemTvClickListener(new CommunityAllAdapter.OnRecyclerViewTvItemClickListener() {
            @Override
            public void onItemTVClick(View view, String starid, int postion) {
//                点击关注
                mPresenter.setFollow(starid, type);

            }
        });


        adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                UiUtils.SnackbarText("点击条目");

            }
        });
    }


    @Override
    public void setFollowData(BaseBean data) {
        mPresenter.getPersonalAll(type, true, "");
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.getPersonalAll(type, true, "");
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.getPersonalAll(type, false, "");
    }
}
