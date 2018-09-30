package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.MyCommentListBean;
import com.lwd.qjtv.mvp.ui.holder.MyCommentItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class MyCommentItemAdapter extends DefaultAdapter<MyCommentListBean.DataBean> {
    public MyCommentItemAdapter(List<MyCommentListBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<MyCommentListBean.DataBean> getHolder(View v, int viewType) {
        return new MyCommentItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_my_comment_item_layout;
    }

}