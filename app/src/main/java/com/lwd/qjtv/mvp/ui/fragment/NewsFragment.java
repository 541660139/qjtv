package com.lwd.qjtv.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.jude.rollviewpager.hintview.IconHintView;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.Contact;
import com.lwd.qjtv.di.component.DaggerNewsComponent;
import com.lwd.qjtv.di.module.NewsModule;
import com.lwd.qjtv.mvp.contract.NewsContract;
import com.lwd.qjtv.mvp.model.entity.BannerBean;
import com.lwd.qjtv.mvp.presenter.NewsPresenter;
import com.lwd.qjtv.mvp.ui.activity.PushCommunityActivity;
import com.lwd.qjtv.mvp.ui.fragment.HamePage.ChoiceFragment;
import com.lwd.qjtv.mvp.ui.fragment.HamePage.WatchingFocusFragment;
import com.lwd.qjtv.mvp.ui.fragment.community.CommuntiyAllFragment;
import com.lwd.qjtv.view.DragFloatActionButton;
import com.lwd.qjtv.view.LoadingPageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class NewsFragment extends BaseFragment<NewsPresenter> implements NewsContract.View {


    @BindView(R.id.tl_news)
    SlidingTabLayout tl_news;

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.rpv_homepage_banner)
    RollPagerView rpv_homepage_banner;


    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;

    @BindView(R.id.circle_button)
    DragFloatActionButton circle_button;


    private Contact contact;
//    private ChoiceFragment choiceFragment;
//    private WatchingFocusFragment watchingFocusFragment;

    private List<String> imagesUrl = new ArrayList<>();


    protected boolean isCreated = false;

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerNewsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .newsModule(new NewsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
    }


    @Override
    public void initData() {
        loadingPageView.startLodingAnim();

        loadingPageView.setClickReload(() -> mPresenter.getBanner());

        //通过Resources获取屏幕宽度
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int bannerWidth = dm.widthPixels;
        int bannerHeight = bannerWidth * 9 / 15;

        ViewGroup.LayoutParams layoutParams = rpv_homepage_banner.getLayoutParams();
        layoutParams.height = bannerHeight;

        mPresenter.getBanner();
        final List<String> list = new ArrayList<>();

        //添加4条数据,作为tablayout标签
        list.add("最新");
        list.add("关注");


        //设置viewpager可以预加载全部的页数,,不会销毁其他的页面
        vp.setOffscreenPageLimit(list.size());
        vp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {


            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position);
            }

            @Override
            public Fragment getItem(int position) {

                if (position == 0) {
                    //选中其他tablayou标签时候,展示这个fragment
                    CommuntiyAllFragment communtiyAllFragment1 = CommuntiyAllFragment.newInstance(1);
                    return communtiyAllFragment1;
                } else {

                    //当选中第一个tablayout标签时候,展示这个fragment

                    CommuntiyAllFragment communtiyAllFragment2 = CommuntiyAllFragment.newInstance(2);
                    return communtiyAllFragment2;
                }

            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    tl_news.getTitleView(position).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    tl_news.getTitleView(1).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                } else {
                    tl_news.getTitleView(position).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    tl_news.getTitleView(0).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置tablayout和viewpager联动
        tl_news.setViewPager(vp);
        tl_news.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp.setCurrentItem(position);
                if (position == 0) {
                    tl_news.getTitleView(position).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    tl_news.getTitleView(1).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                } else {
                    tl_news.getTitleView(position).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    tl_news.getTitleView(0).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }


            }

            @Override
            public void onTabReselect(int position) {
            }
        });


    }


    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

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

    @Override
    public void setBannerData(BannerBean bannerData) {

        loadingPageView.checkData(bannerData);
        List<BannerBean.DataBean> data = bannerData.getData();
        for (int i = 0; i < data.size(); i++) {
            imagesUrl.add(data.get(i).getBanner_url());
        }

        //设置每个图片的切换时间
        rpv_homepage_banner.setPlayDelay(3000);
        //设置图片切换动画时间
        rpv_homepage_banner.setAnimationDurtion(500);


        rpv_homepage_banner.setHintView(new IconHintView(getContext(), R.mipmap.video_place_holder, R.mipmap.video_place_holder));
        rpv_homepage_banner.setHintView(new ColorPointHintView(getContext(),
                getResources().getColor(R.color.colorAccent),
                Color.WHITE));
        //设置适配器
        rpv_homepage_banner.setAdapter(new RollPagerAdapter(rpv_homepage_banner, imagesUrl));

        //设置每一个图片的点击事件
        rpv_homepage_banner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


            }
        });
    }


    //适配器
    private class RollPagerAdapter extends LoopPagerAdapter {
        private List<String> imageUrls;

        public RollPagerAdapter(RollPagerView viewPager, List<String> imageUrls) {
            super(viewPager);
            this.imageUrls = imageUrls;
        }


        @Override
        public View getView(ViewGroup container, int position) {

            ImageView view = new ImageView(container.getContext());
            Glide.with(container.getContext()).load(imageUrls.get(position))
                    .placeholder(R.mipmap.video_place_holder)
                    .error(R.mipmap.video_place_holder).into(view);
            //设置高度和宽度
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置拉伸方式
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return view;
        }


        @Override
        public int getRealCount() {
            return imageUrls.size();
        }
    }


    @OnClick({R.id.circle_button})

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.circle_button:
                Intent intent = new Intent(getActivity(), PushCommunityActivity.class);
                startActivity(intent);
                break;
            default:

        }

    }
}

