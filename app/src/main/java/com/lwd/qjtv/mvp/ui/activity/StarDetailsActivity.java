package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.base.App;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.GlideConfigGlobal;
import com.lwd.qjtv.di.component.DaggerStarDetailsComponent;
import com.lwd.qjtv.di.module.StarDetailsModule;
import com.lwd.qjtv.mvp.contract.StarDetailsContract;
import com.lwd.qjtv.mvp.model.entity.StarDetailsBean;
import com.lwd.qjtv.mvp.presenter.StarDetailsPresenter;
import com.lwd.qjtv.mvp.ui.fragment.star.StarIntroduceFragment;
import com.lwd.qjtv.mvp.ui.fragment.star.VisitVideoFragment;
import com.lwd.qjtv.mvp.ui.fragment.star.WonderfulVideoFragment;
import com.lwd.qjtv.view.LoadingPageView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/24.
 */

public class StarDetailsActivity extends BaseActivity<StarDetailsPresenter> implements StarDetailsContract.View {


    @BindView(R.id.activity_star_details_big_iv)
    ImageView bigIv;
    @BindView(R.id.activity_star_details_back_iv)
    ImageView backIv;
    @BindView(R.id.activity_star_details_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.activity_star_details_viewpager)
    ViewPager viewPager;
    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;
    private AdapterViewPager adapterViewPager;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private String[] titles = {
            "简介",
            "精彩视频",
            "其他"
    };
    private String starId;
    private StarIntroduceFragment starIntroduceFragment;
    private WonderfulVideoFragment wonderfulVideoFragment;
    private VisitVideoFragment wonderfulVideoFragment1;

    private AppComponent appComponent;
    private ImageLoader imageLoader;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerStarDetailsComponent
                .builder()
                .appComponent(appComponent)
                .starDetailsModule(new StarDetailsModule(this)) //请将StarDetailsModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return R.layout.activity_star_details;
    }


    @Override
    public void initData() {
        loadingPageView.startLodingAnim();
        getIntentData();
        initFragment();
        initListener();
        initViewPager();
    }

    private void getIntentData() {
        if (getIntent() != null) {
            starId = getIntent().getStringExtra("starId");
            mPresenter.getStarDetails(starId);
        }
    }

    private void initFragment() {
        //明星简介
        starIntroduceFragment = StarIntroduceFragment.newInstance();
        Bundle starIntroduceFragmentBundle = new Bundle();
        starIntroduceFragmentBundle.putString("starId", starId);
        starIntroduceFragment.setArguments(starIntroduceFragmentBundle);
        fragmentList.add(starIntroduceFragment);
        //精彩视频
        wonderfulVideoFragment = WonderfulVideoFragment.newInstance();
        Bundle wonderfulVideoFragmentBundle = new Bundle();
        wonderfulVideoFragmentBundle.putString("starId", starId);
        wonderfulVideoFragmentBundle.putString("videoId", starId);
        wonderfulVideoFragmentBundle.putString("v_type", starId);
        wonderfulVideoFragmentBundle.putString("op", "wonderfulVideo");
        wonderfulVideoFragment.setArguments(wonderfulVideoFragmentBundle);
        fragmentList.add(wonderfulVideoFragment);
        //访谈视频
        Bundle wonderfulVideoFragment1Bundle = new Bundle();
        wonderfulVideoFragment1Bundle.putString("starId", starId);
        wonderfulVideoFragment1Bundle.putString("videoId", starId);
        wonderfulVideoFragment1Bundle.putString("v_type", starId);
        wonderfulVideoFragment1Bundle.putString("op", "interviewVideo");
        wonderfulVideoFragment1 = VisitVideoFragment.newInstance();
        wonderfulVideoFragment1.setArguments(wonderfulVideoFragment1Bundle);
        fragmentList.add(wonderfulVideoFragment1);
    }

    private void initViewPager() {
        tabLayout.setTabTextColors(getResources().getColor(R.color.color666666), getResources().getColor(R.color.colorOrigin));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorOrigin));
        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapterViewPager);
        adapterViewPager.bindData(fragmentList, titles);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initListener() {
        loadingPageView.setClickReload(() -> mPresenter.getStarDetails(starId));
        backIv.setOnClickListener(view -> finish());
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
//        UiUtils.SnackbarText(message);
        UiUtils.makeText(this, message);
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
        loadingPageView.checkData(o);
        StarDetailsBean.DataBean dataBean = (StarDetailsBean.DataBean) o;
        appComponent = ((App) getApplicationContext()).getAppComponent();
        imageLoader = appComponent.imageLoader();
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(dataBean.getAvater(), bigIv));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bigIv != null)
            imageLoader.clear(appComponent.Application(), GlideImageConfig.builder()
                    .imageViews(bigIv)
                    .build());
    }
}
