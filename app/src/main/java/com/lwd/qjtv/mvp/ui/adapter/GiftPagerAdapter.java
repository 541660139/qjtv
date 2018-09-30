package com.lwd.qjtv.mvp.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/19.
 */

public class GiftPagerAdapter extends PagerAdapter {

    private List<View> viewContainer;

    public GiftPagerAdapter(List<View> viewContainer){
        this.viewContainer = viewContainer;
    }

    @Override
    public int getCount() {
        return viewContainer == null ? 0 : viewContainer.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewContainer.get(position));
        return viewContainer.get(position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
