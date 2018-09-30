package com.lwd.qjtv.mvp.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.delegate.IActivity;
import com.jess.arms.di.component.AppComponent;
import com.lwd.qjtv.mvp.ui.fragment.MallFragment;
import com.lwd.qjtv.mvp.ui.fragment.WatchBallFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.ui.fragment.LearnBallFragment;
import com.lwd.qjtv.mvp.ui.fragment.MineFragment;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class TestMainActivity extends BaseActivity implements IActivity {

    @BindView(R.id.activity_main_vp)
    ViewPager viewPager;

    @BindView(R.id.activity_main_rg)
    RadioGroup radioGroup;

    @BindView(R.id.activity_main_learnball_rb)
    RadioButton learnBallRb;

    @BindView(R.id.activity_main_watchball_rb)
    RadioButton watchBallRb;

    @BindView(R.id.activity_main_mall_rb)
    RadioButton mallRb;

    @BindView(R.id.activity_main_mine_rb)
    RadioButton mineRb;

    //学球模块
    private LearnBallFragment learnBallFragment;
    //商城模块
    private MallFragment mallFragment;
    //我的模块
    private MineFragment mineFragment;
    //看球模块
    private WatchBallFragment watchBallFragment;
    private List<BaseFragment> fragmentList;
    private AdapterViewPager adapter;


    private void initListener() {
        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            switch (i) {
                //选择学球模块
                case R.id.activity_main_learnball_rb:
                    viewPager.setCurrentItem(1);
                    learnBallFragment.initData();
                    checkBottomText(1);
                    break;
                //选择商城模块
                case R.id.activity_main_mall_rb:
                    viewPager.setCurrentItem(2);
                    mallFragment.initData();
                    checkBottomText(2);
                    break;
                //选择我的模块
                case R.id.activity_main_mine_rb:
                    viewPager.setCurrentItem(3);
                    checkBottomText(3);
                    mineFragment.initData();
                    break;
                //选择看球模块
                case R.id.activity_main_watchball_rb:
                    viewPager.setCurrentItem(0);
                    checkBottomText(0);
                    watchBallFragment.initData();
                    break;

            }
        });
    }

    private void checkBottomText(int i) {
        int colorOrigin = getResources().getColor(R.color.colorOrigin);
        int color333333 = getResources().getColor(R.color.color333333);
        watchBallRb.setTextColor(i == 0 ? colorOrigin : color333333);
        learnBallRb.setTextColor(i == 1 ? colorOrigin : color333333);
        mallRb.setTextColor(i == 2 ? colorOrigin : color333333);
        mineRb.setTextColor(i == 3 ? colorOrigin : color333333);

    }

    private void initFragment() {
        learnBallFragment = LearnBallFragment.newInstance();
        mallFragment = MallFragment.newInstance();
        mineFragment = MineFragment.newInstance();
        watchBallFragment = WatchBallFragment.newInstance();
        fragmentList = new ArrayList<>();
        fragmentList.add(watchBallFragment);
        fragmentList.add(learnBallFragment);
        fragmentList.add(mallFragment);
        fragmentList.add(mineFragment);
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public int initView() {
        return R.layout.activity_main;
    }


    private void initViewPager() {
        viewPager.setOffscreenPageLimit(3);
        adapter = new AdapterViewPager(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        adapter.bindData(fragmentList);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        radioGroup.check(R.id.activity_main_watchball_rb);
                        break;
                    case 1:
                        radioGroup.check(R.id.activity_main_learnball_rb);
                        break;
                    case 2:
                        radioGroup.check(R.id.activity_main_mall_rb);
                        break;
                    case 3:
                        radioGroup.check(R.id.activity_main_mine_rb);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
        initFragment();
        initListener();
        initViewPager();
        watchBallRb.setChecked(true);
    }

    @Override
    public boolean useFragment() {
        return true;
    }

    private FragmentStatePagerAdapter pagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return fragmentList == null ? null : fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }

    };

}
