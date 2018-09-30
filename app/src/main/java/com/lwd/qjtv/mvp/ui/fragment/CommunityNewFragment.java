package com.lwd.qjtv.mvp.ui.fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.di.component.DaggerCommunityNewComponent;
import com.lwd.qjtv.di.module.CommunityNewModule;
import com.lwd.qjtv.mvp.contract.CommunityNewContract;
import com.lwd.qjtv.mvp.presenter.CommunityNewPresenter;
import com.lwd.qjtv.mvp.ui.activity.PushCommunityActivity;
import com.lwd.qjtv.mvp.ui.fragment.community.CommuntiyAllFragment;
import com.lwd.qjtv.view.DragFloatActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CommunityNewFragment extends BaseFragment<CommunityNewPresenter> implements CommunityNewContract.View, ViewPager.OnPageChangeListener, View.OnClickListener {

    @BindView(R.id.cursor)
    View cursor;
    @BindView(R.id.tv_first)
    TextView tv_first;
    @BindView(R.id.tv_second)
    TextView tv_second;
    @BindView(R.id.viewpager)
    ViewPager viewpager;


    @BindView(R.id.circle_button)
    DragFloatActionButton circle_button;


    List<String> list = new ArrayList<>();


    private int number = 0;//当前页面编号


    public static CommunityNewFragment newInstance() {
        CommunityNewFragment fragment = new CommunityNewFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCommunityNewComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .communityNewModule(new CommunityNewModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_community_new, container, false);
    }

    @Override
    public void initData() {

        initViewPager();


    }

    private void initViewPager() {
        list.add("最新");
        list.add("关注");
        viewpager.setCurrentItem(0);//设置当前页
        viewpager.addOnPageChangeListener(this);//监听页面改变
        viewpager.setOffscreenPageLimit(list.size());
        viewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
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

        viewpager.addOnPageChangeListener(this);

        circle_button.setOnClickListener(this);

    }


    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int i = UiUtils.dip2px(getContext(), 70);
        setFragmnetSelectorOrUnSelecot(position);


        ObjectAnimator objectAnimator = null;
        if (position == 0) {
            if (number == 1) {
                objectAnimator = ObjectAnimator.ofFloat(cursor, "translationX", i, 0f);
                //      设置移动时间
                objectAnimator.setDuration(200);
//      开始动画
                objectAnimator.start();

            }

        } else if (position == 1) {
            if (number == 0) {
                objectAnimator = ObjectAnimator.ofFloat(cursor, "translationX", 0f, i);
                //      设置移动时间
                objectAnimator.setDuration(200);
//      开始动画
                objectAnimator.start();
            }
        }

        number = position;


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    void setFragmnetSelectorOrUnSelecot(int position) {
        int i = UiUtils.dip2px(getContext(), 70);
        switch (position) {
            case 0:
                viewpager.setCurrentItem(0);//设置当前页
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(cursor, "translationX", i, 0f);
                //      设置移动时间
                objectAnimator.setDuration(200);
//      开始动画
                objectAnimator.start();

                tv_first.setTextColor(getResources().getColor(R.color.red_dark));
                tv_second.setTextColor(getResources().getColor(R.color.color222222));
                break;
            case 1:
                viewpager.setCurrentItem(1);//设置当前页
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(cursor, "translationX", 0, i);
                //      设置移动时间
                objectAnimator1.setDuration(200);
//      开始动画
                objectAnimator1.start();
                tv_first.setTextColor(getResources().getColor(R.color.color222222));
                tv_second.setTextColor(getResources().getColor(R.color.red_dark));
                break;
            default:
        }

    }

    @OnClick({R.id.tv_first, R.id.tv_second})

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_first:
                setFragmnetSelectorOrUnSelecot(0);
                break;
            case R.id.tv_second:
                setFragmnetSelectorOrUnSelecot(1);
                break;
            case R.id.circle_button:
                Intent intent = new Intent(getActivity(), PushCommunityActivity.class);
                startActivity(intent);
                break;
            default:

        }

    }
}
