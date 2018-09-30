package com.lwd.qjtv.mvp.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.mvp.ui.fragment.CommunityNewFragment;
import com.lwd.qjtv.mvp.ui.fragment.GameFragment;
import com.lwd.qjtv.mvp.ui.fragment.HamePage.ChoiceFragment;
import com.lwd.qjtv.mvp.ui.fragment.HamePage.WatchingFocusFragment;
import com.lwd.qjtv.mvp.ui.fragment.LearnBallFragment;
import com.lwd.qjtv.mvp.ui.fragment.MallFragment;
import com.lwd.qjtv.mvp.ui.fragment.MineNewFragment;
import com.lwd.qjtv.mvp.ui.fragment.NewsFragment;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


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
 * Created by ZhengQian on 2017/6/14.
 */

public class MainActivity extends AppCompatActivity {
    //    @BindView(R.id.activity_main_vp)
    ViewPager viewPager;

    //    @BindView(R.id.activity_main_rg)
    RadioGroup radioGroup;

    //    @BindView(R.id.activity_main_learnball_rb)
    RadioButton learnBallRb;

    //    @BindView(R.id.activity_main_watchball_rb)
    RadioButton watchBallRb;

    //    @BindView(R.id.activity_main_mall_rb)
    RadioButton mallRb;

    //    @BindView(R.id.activity_main_mine_rb)
    RadioButton mineRb;

    //我的模块
//    private MineFragment mineFragment;


    private MineNewFragment mineFragment;
    //赛事模块
    private GameFragment watchBallFragment;

    //资讯模块
    private NewsFragment newsFragment;
    private List<BaseFragment> fragmentList;
    private AdapterViewPager adapter;

    //是否退出
    private static boolean isExit;

    //处理退出发出的消息
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (Preference.getBoolean(Constant.IS_REGISTER, false)) {
            WEApplication.showLuckyPan(this);
            Preference.put(Constant.IS_REGISTER, false);
        }
    }


    private void initListener() {
        if (Preference.getBoolean(Constant.IS_RELEASE)) {
            radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
                switch (i) {
                    //选择学球模块
                    case R.id.activity_main_learnball_rb:
                        viewPager.setCurrentItem(1);
                        checkBottomText(1);
                        break;
                    //选择社区模块
                    case R.id.activity_main_mall_rb:

                        viewPager.setCurrentItem(2);
                        checkBottomText(2);

                        break;
                    //选择个人中心模块
                    case R.id.activity_main_mine_rb:
                        viewPager.setCurrentItem(3);
                        checkBottomText(3);
                        mineFragment.initData();
                        break;
                    //选择首页模块
                    case R.id.activity_main_watchball_rb:
                        viewPager.setCurrentItem(0);
                        checkBottomText(0);
                        break;

                }
            });
        } else {
            radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
                switch (i) {

                    case R.id.activity_main_mall_rb:

                        viewPager.setCurrentItem(1);
                        checkBottomText(1);

                        break;
                    //选择我的模块
                    case R.id.activity_main_mine_rb:
                        viewPager.setCurrentItem(2);
                        checkBottomText(2);
                        mineFragment.initData();
                        break;
                    //选择看球模块
                    case R.id.activity_main_watchball_rb:
                        viewPager.setCurrentItem(0);
                        checkBottomText(0);
                        break;

                }
            });
        }
    }

    private void checkBottomText(int i) {
        if (Preference.getBoolean(Constant.IS_RELEASE)) {
            int colorOrigin = getResources().getColor(R.color.blue_main);
            int color333333 = getResources().getColor(R.color.raid_text_color);
            watchBallRb.setTextColor(i == 0 ? colorOrigin : color333333);
            learnBallRb.setTextColor(i == 1 ? colorOrigin : color333333);
            mallRb.setTextColor(i == 2 ? colorOrigin : color333333);
            mineRb.setTextColor(i == 3 ? colorOrigin : color333333);
        } else {
            int colorOrigin = getResources().getColor(R.color.blue_main);
            int color333333 = getResources().getColor(R.color.raid_text_color);
            watchBallRb.setTextColor(i == 0 ? colorOrigin : color333333);
            mallRb.setTextColor(i == 1 ? colorOrigin : color333333);
            mineRb.setTextColor(i == 2 ? colorOrigin : color333333);

        }
    }

    private void initFragment() {


        mineFragment = new MineNewFragment();
        newsFragment = new NewsFragment();

        watchBallFragment = new GameFragment();

        fragmentList = new ArrayList<>();
        fragmentList.add(newsFragment);

        fragmentList.add(WatchingFocusFragment.newInstance());
        fragmentList.add(ChoiceFragment.newInstance());

        fragmentList.add(mineFragment);

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initData();
        if (!Preference.getBoolean(Constant.IS_RELEASE)) {
            learnBallRb.setVisibility(View.GONE);
        }
// TODO: 2018/8/22  暂时屏蔽检查版本
//        WEApplication.checkVersion(true, this);
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.activity_main_vp);
        radioGroup = (RadioGroup) findViewById(R.id.activity_main_rg);
        learnBallRb = (RadioButton) findViewById(R.id.activity_main_learnball_rb);
        watchBallRb = (RadioButton) findViewById(R.id.activity_main_watchball_rb);
        mallRb = (RadioButton) findViewById(R.id.activity_main_mall_rb);
        mineRb = (RadioButton) findViewById(R.id.activity_main_mine_rb);
    }


    private void initViewPager() {
        if (Preference.getBoolean(Constant.IS_RELEASE)) {
            viewPager.setOffscreenPageLimit(4);
        } else {
            viewPager.setOffscreenPageLimit(3);
        }

        adapter = new AdapterViewPager(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        adapter.bindData(fragmentList);
        if (Preference.getBoolean(Constant.IS_RELEASE)) {
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    return;
                }

                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0:
                            radioGroup.check(R.id.activity_main_watchball_rb);

                            break;
                        case 1:

                            radioGroup.check(R.id.activity_main_learnball_rb);
//                            ((WatchBallFragment) (fragmentList.get(1))).refreshData();
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
        } else {
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    return;
                }

                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0:
                            radioGroup.check(R.id.activity_main_watchball_rb);

                            break;
                        case 1:

                            radioGroup.check(R.id.activity_main_mall_rb);
                            break;
                        case 2:

                            radioGroup.check(R.id.activity_main_mine_rb);

                            break;

                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //如果isExit标记为false，提示用户再次按键
            if (!isExit) {
                isExit = true;
                UiUtils.makeText(this, "再按一次退出程序");
                //如果用户没有在3秒内再次按返回键的话，就发送消息标记用户为不退出状态
                mHandler.sendEmptyMessageDelayed(0, 3000);
            }
            //如果isExit标记为true，退出程序
            else {
                //退出程序
                MobclickAgent.onKillProcess(this);
                finish();
                System.exit(0);
            }
        }
        return false;
    }

//    public WatchBallFragment getHomeFragment() {
//        return watchBallFragment;
//    }


    public void initData() {
        initFragment();
        initListener();
        initViewPager();
        watchBallRb.setChecked(true);
    }
}
