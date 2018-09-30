package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;

import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.di.component.DaggerMatchCollectionComponent;
import com.lwd.qjtv.di.module.MatchCollectionModule;
import com.lwd.qjtv.mvp.contract.MatchCollectionContract;
import com.lwd.qjtv.mvp.model.entity.MatchCollectionTitleBean;
import com.lwd.qjtv.mvp.presenter.MatchCollectionPresenter;

import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.ui.fragment.other.MatchCollectionDetailsFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MatchCollectionActivity extends BaseActivity<MatchCollectionPresenter> implements MatchCollectionContract.View {

    @BindView(R.id.activity_match_collection_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.activity_match_collection_viewpager)
    ViewPager viewPager;
    @BindView(R.id.activity_match_collection_big_iv)
    ImageView bigIv;
    @BindView(R.id.activity_match_collection_search_iv)
    ImageView searchIv;
    @BindView(R.id.activity_search_edt)
    EditText searchEdt;
    @BindView(R.id.activity_match_collection_title_tv)
    TextView titleTv;
    @BindView(R.id.activity_match_collection_back_tv)
    TextView backTv;
    @BindView(R.id.activity_match_collection_appbar)
    AppBarLayout appBarLayout;
    private AdapterViewPager adapterViewPager;
    private List<BaseFragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private String id;
    private AppComponent appComponent;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
        DaggerMatchCollectionComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .matchCollectionModule(new MatchCollectionModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_match_collection;
    }

    @Override
    public void initData() {
        initTablayoutWithViewpager();
        if (getIntent() != null) {
            if (getIntent().hasExtra("id")) {
                id = getIntent().getStringExtra("id");
            }
            if (getIntent().hasExtra("pic")) {
                String pic = getIntent().getStringExtra("pic");
                Glide.with(this).load(pic).into(bigIv);
            }
            if (getIntent().hasExtra("name")) {
                titleTv.setText(getIntent().getStringExtra("name") == null ? "" : getIntent().getStringExtra("name"));
            }
        }
        initListener();
        mPresenter.getMatchTitle(id);
    }

    private void initListener() {
        searchIv.setOnClickListener(v -> {
            Intent intent = new Intent(this, MatchSearchActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        });
        searchEdt.setOnClickListener(v -> {
//            Intent intent = new Intent(this, MatchSearchActivity.class);
//            intent.putExtra("id", id);
//            startActivity(intent);
        });
        backTv.setOnClickListener(v -> finish());
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if (bigIv.getHeight() + verticalOffset < 50) {
                searchIv.setVisibility(View.VISIBLE);
            } else {
                searchIv.setVisibility(View.GONE);
            }
        });
    }

    private void initTablayoutWithViewpager() {
        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        tabLayout.setupWithViewPager(viewPager);
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
        List<MatchCollectionTitleBean.DataBean> dataBean = (List<MatchCollectionTitleBean.DataBean>) o;
        for (int i = 0; i < (dataBean == null ? 0 : dataBean.size()); i++) {
            fragments.add(MatchCollectionDetailsFragment.newInstance(dataBean.get(i).getPhaseMatch_id()));
            titles.add(dataBean.get(i).getMatch_name());
        }
        viewPager.setOffscreenPageLimit(titles.size());
        adapterViewPager.bindData(fragments, titles);
    }


    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {

    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {

    }
}
