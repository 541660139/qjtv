package com.lwd.qjtv.mvp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GuessDetailsBean;
import com.lwd.qjtv.mvp.ui.holder.GuessDetailsItemHolder;
import com.lwd.qjtv.mvp.ui.holder.GuessRangfenItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/23.
 */

public class GuessRangFenAdapter extends DefaultAdapter<GuessDetailsBean.DataBean.GuessRangfenBean.PlInfoBeanXXX> {
    private boolean isVisible;
    private GuessDetailsItemHolder holder;
    private String title, match_id, jc_type, jc_id;

    public GuessRangFenAdapter(List<GuessDetailsBean.DataBean.GuessRangfenBean.PlInfoBeanXXX> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<GuessDetailsBean.DataBean.GuessRangfenBean.PlInfoBeanXXX> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        GuessRangfenItemHolder mHolder = (GuessRangfenItemHolder) getHolder(view, viewType);
        mHolder.setApiparm(title, match_id, jc_type, jc_id);
        return mHolder;

    }

    @Override
    public BaseHolder<GuessDetailsBean.DataBean.GuessRangfenBean.PlInfoBeanXXX> getHolder(View v, int viewType) {
        return new GuessRangfenItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_guess_details_layout;
    }

    public void setApiparm(String title, String match_id, String jc_type, String jc_id) {
        this.title = title;
        this.match_id = match_id;
        this.jc_id = jc_id;
        this.jc_type = jc_type;
    }

}