package com.lwd.qjtv.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.lwd.qjtv.mvp.model.entity.WatchBallBean;
import com.lwd.qjtv.mvp.ui.fragment.other.RateSmallFragment;


import java.util.ArrayList;
import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/13.
 */

public class FragmentWatchBallPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private FragmentManager fm;
    private List<WatchBallBean.DataBean.LiveListBean.DataListBean> rightData;
    private List<WatchBallBean.DataBean.LiveListBean.DataListBean> leftData;

    public FragmentWatchBallPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    public void bindData(List<Fragment> list) {
        this.fragments = list;
        notifyDataSetChanged();
    }

    public void updateData(List<WatchBallBean.DataBean.LiveListBean.DataListBean> leftData, List<WatchBallBean.DataBean.LiveListBean.DataListBean> rightData) {
        this.leftData = leftData;
        this.rightData = rightData;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment f = (Fragment) super.instantiateItem(container, position);
//        View view = f.getView();
        RateSmallFragment fragment = (RateSmallFragment) f;
        if (leftData != null && rightData != null)
            if (leftData.size() == rightData.size() || leftData.size() - 1 != position)
                fragment.update(leftData.get(position), rightData.get(position));
//            else if (leftData.size() - 1 != position)
//                fragment.update(leftData.get(position), rightData.get(position));
            else
                fragment.update(leftData.get(position), null);
//        if (view != null)
//            container.addView(view);
        return f;
    }

//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        View view = fragments.get(position).getView();
//        if (view != null)
//            container.removeView(view);
//    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
