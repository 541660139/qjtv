package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.WatchBallBean;
import com.lwd.qjtv.mvp.ui.holder.OtherGuessItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/19.
 */

public class OtherGuessAdapter extends DefaultAdapter<WatchBallBean.DataBean.NbaListBean.DataListBeanX> {


    public OtherGuessAdapter(List<WatchBallBean.DataBean.NbaListBean.DataListBeanX> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<WatchBallBean.DataBean.NbaListBean.DataListBeanX> getHolder(View v, int viewType) {
        return new OtherGuessItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_other_guess_layout;
    }
}