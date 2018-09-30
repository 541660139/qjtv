package com.lwd.qjtv.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lwd.qjtv.R;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/5.
 */

public abstract class BaseFragment extends com.jess.arms.base.BaseFragment {

    private View rootView;
    private LinearLayout baseLl;
    private ImageView loadingView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_base, container,
                    false);
            baseLl = (LinearLayout) rootView.findViewById(R.id.fragment_base_ll);
            loadingView = (ImageView) rootView.findViewById(R.id.fragment_base_loading_iv);
        }
        return rootView;
    }

//    public void startLoadingAnim() {
////        baseLl.setVisibility(View.VISIBLE);
//        loadingView.setImageResource(R.drawable.rotate_anim);
//        ((AnimationDrawable) loadingView.getDrawable()).start();
//    }
//
//    public void stopLoadingAnim() {
//        loadingView.setImageResource(R.drawable.rotate_anim);
//        ((AnimationDrawable) loadingView.getDrawable()).stop();
//        baseLl.setVisibility(View.GONE);
//    }
}
