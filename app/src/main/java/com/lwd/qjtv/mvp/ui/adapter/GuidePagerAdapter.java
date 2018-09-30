package com.lwd.qjtv.mvp.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by 666 on 2016/9/18.
 */
public class GuidePagerAdapter extends PagerAdapter {
    private List<ImageView> mList;
    public GuidePagerAdapter(List<ImageView> mList ){
        this.mList = mList;
    }
    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size() ;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = mList.get(position);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);
        return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
            (container).removeView(mList.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
