package com.lwd.qjtv.mvp.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.presenter.GuessCenterPresenter;
import com.lwd.qjtv.mvp.ui.fragment.GuessRankFragment;
import com.lwd.qjtv.mvp.ui.fragment.RichRankFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/17.
 */

public class GuessCenterActivity extends BaseActivity<GuessCenterPresenter> {


    @BindView(R.id.back_iv)
    TextView back_iv;
    @BindView(R.id.tv_sineoke)
    TextView tv_sineoke;
    @BindView(R.id.other_cai)
    TextView other_cai;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.ll_guess_center_title)
    LinearLayout ll_guess_center_title;


    @BindView(R.id.tv_guess_center_title)
    TextView tv_guess_center_title;


    @BindView(R.id.slidingtablayout)
    SlidingTabLayout slidingtablayout;


    private List<BaseFragment> fragmentList;
    private MyPagerAdapter adapter;


    ArrayList<String> mTitles = new ArrayList<>();

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView() {
        return R.layout.activity_new_guess_center;
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void initData() {
        mTitles.add("竞猜排行榜");
        mTitles.add("富豪排行榜");
        initFragment();
        initViewPager();
        initListener();
    }


    private void initListener() {
        back_iv.setOnClickListener(view -> finish());

    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        RichRankFragment richRankFragment = new RichRankFragment();
        fragmentList.add(richRankFragment);
        GuessRankFragment guessRankFragment = new GuessRankFragment();
        fragmentList.add(guessRankFragment);


    }


    private void initViewPager() {


        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewpager.setOffscreenPageLimit(fragmentList.size());
        viewpager.setAdapter(adapter);
        slidingtablayout.setViewPager(viewpager);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mTitles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
    }

}
