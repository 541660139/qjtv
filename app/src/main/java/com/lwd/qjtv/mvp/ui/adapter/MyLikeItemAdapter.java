package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.MyLikeListBean;
import com.lwd.qjtv.mvp.ui.holder.MyLikeItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/19.
 */

public class MyLikeItemAdapter extends DefaultAdapter<MyLikeListBean.DataBean.AvaterBean> {


    private MyLikeItemHolder myLikeItemHolder;

    public MyLikeItemAdapter(List<MyLikeListBean.DataBean.AvaterBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<MyLikeListBean.DataBean.AvaterBean> getHolder(View v, int viewType) {
        myLikeItemHolder = new MyLikeItemHolder(v);
        return myLikeItemHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_my_like_item_layout;
    }

    public void setClickMoreCallback(MyLikeItemHolder.ClickMoreCallback clickMoreCallback) {
        if (clickMoreCallback != null)
            if (myLikeItemHolder != null)
                myLikeItemHolder.setClickMoreCallback(clickMoreCallback);
    }
}