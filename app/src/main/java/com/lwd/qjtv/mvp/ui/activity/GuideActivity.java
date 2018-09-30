package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.lwd.qjtv.app.utils.Preference;

import java.util.ArrayList;
import java.util.List;

import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.mvp.ui.adapter.GuidePagerAdapter;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/5.
 */

public class GuideActivity extends BaseActivity {

    private ViewPager viewPager;

    private GuidePagerAdapter adapter;

    private List<ImageView> mList = new ArrayList<>();


    @Override
    public void initData() {
        initViews();
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.guide_one);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageView imageView1 = new ImageView(this);
        imageView1.setImageResource(R.mipmap.guide_two);
        imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageView imageView2 = new ImageView(this);
        imageView2.setImageResource(R.mipmap.guide_three);
        imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageView imageView3 = new ImageView(this);
        imageView3.setImageResource(R.mipmap.guide_four);
        imageView3.setScaleType(ImageView.ScaleType.FIT_XY);
        mList.add(imageView);
        mList.add(imageView1);
        mList.add(imageView2);
        mList.add(imageView3);
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView() {
        return R.layout.activity_guide;
    }



    private void initViews() {
        adapter = new GuidePagerAdapter(mList);
        viewPager = (ViewPager) findViewById(R.id.activity_guide_viewpager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == (mList.size() - 1)) {
                    ImageView imageView = mList.get(position);
                    imageView.setOnClickListener(view -> {
                                startActivity(new Intent(WEApplication.getContext(), TestMainActivity.class));
                                Preference.putBoolean(Constant.IS_FIRST,false);
                                finish();
                            }
                    );
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
