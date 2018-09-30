package com.lwd.qjtv.mvp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.mvp.presenter.RechargeCostPresenter;
import com.lwd.qjtv.mvp.ui.fragment.other.RechargeCostFragment;
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

public class RechargeCostActivity extends BaseActivity<RechargeCostPresenter>  {

    @BindView(R.id.activity_recharge_cost_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.activity_recharge_cost_viewpager)
    ViewPager viewPager;
    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;

    private AdapterViewPager adapterViewPager;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private String[] titles = {
            "收入明细",
            "支出明细"
    };

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView() {
        return R.layout.activity_recharge_cost;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!Preference.getBoolean(Constant.IS_LOGIN))
            finish();
    }

    @Override
    public void initData() {
        loadingPageView.startLodingAnim();
        setTitle("充值消费");
        initFragment();
        initViewPager();
    }

    private void initFragment() {
        RechargeCostFragment rechargeRecordeFragment = RechargeCostFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putInt("type",2);
        rechargeRecordeFragment.setArguments(bundle);
        fragmentList.add(rechargeRecordeFragment);
        RechargeCostFragment rechargeRecordeFragment1 = RechargeCostFragment.newInstance();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("type",1);
        rechargeRecordeFragment1.setArguments(bundle1);
        fragmentList.add(rechargeRecordeFragment1);
    }

    private void initViewPager() {
        tabLayout.setTabTextColors(getResources().getColor(R.color.color666666),getResources().getColor(R.color.colorOrigin));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorOrigin));
        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        adapterViewPager.bindData(fragmentList,titles);
        tabLayout.setupWithViewPager(viewPager);
    }



}
