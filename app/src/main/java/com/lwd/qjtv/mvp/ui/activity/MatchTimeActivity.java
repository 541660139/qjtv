package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.di.component.DaggerMatchTimeComponent;
import com.lwd.qjtv.di.module.MatchTimeModule;
import com.lwd.qjtv.mvp.contract.MatchTimeContract;
import com.lwd.qjtv.mvp.model.entity.MatchTimeBean;
import com.lwd.qjtv.mvp.presenter.MatchTimePresenter;
import com.lwd.qjtv.mvp.ui.fragment.other.MatchTimeChildFragment;


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
 * Created by ZhengQian on 2017/5/27.
 */

public class MatchTimeActivity extends BaseActivity<MatchTimePresenter> implements MatchTimeContract.View {

    @BindView(R.id.activity_match_time_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.activity_match_time_viewpager)
    ViewPager viewPager;
    @BindView(R.id.activity_guess_center_guess_champion_tv)
    TextView guessChampionTv;
    //    @BindView(R.id.loading_framelayout)
//    LoadingPageView loadingPageView;
    private List<BaseFragment> fragments = new ArrayList<>();
    private AdapterViewPager adapterViewPager;
    private List<String> titles = new ArrayList<>();
    private String bigMatch_id;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(WEApplication.getContext(), GuessChampionActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }
    };
    private String id;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMatchTimeComponent
                .builder()
                .appComponent(appComponent)
                .matchTimeModule(new MatchTimeModule(this)) //请将MatchTimeModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_match_time;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Preference.getBoolean(Constant.IS_LOGIN))
            finish();
    }

    @Override
    public void initData() {
        if (!Preference.getBoolean(Constant.IS_GUESS)) {
            guessChampionTv.setVisibility(View.GONE);
        }
        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
            mPresenter.getMatchTimeTitle(id);
        }
//        loadingPageView.startLodingAnim();
        setTitle("赛程");
        initViewPager();

        initListener();
    }

    private void initListener() {
//        loadingPageView.setClickReload(() -> mPresenter.getMatchTimeTitle("1"));
        guessChampionTv.setOnClickListener(view -> handler.sendEmptyMessage(1));
    }

    private void initViewPager() {
        tabLayout.setTabTextColors(getResources().getColor(R.color.color666666), getResources().getColor(R.color.colorOrigin));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorOrigin));
        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(10);
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
//        loadingPageView.checkData(o);
        List<MatchTimeBean.DataBean> data = (List<MatchTimeBean.DataBean>) o;
        MatchTimeBean.DataBean dataBean = data.get(0);
        bigMatch_id = dataBean.getBigMatch_id();
        for (int i = 0; i < (data == null ? 0 : data.size()); i++) {
            titles.add(data.get(i).getMatch_name());
            MatchTimeChildFragment matchTimeChildFragment = MatchTimeChildFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("id", data.get(i).getPhaseMatch_id());
            matchTimeChildFragment.setArguments(bundle);
            fragments.add(matchTimeChildFragment);
        }
        adapterViewPager.bindData(fragments, titles);
    }
}
