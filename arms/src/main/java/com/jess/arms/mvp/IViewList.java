package com.jess.arms.mvp;

import com.jess.arms.base.DefaultAdapter;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/22.
 */

public interface IViewList extends IView {
    void startLoadMore();
    void endLoadMore();
    void setAdapter(DefaultAdapter adapter);
}
