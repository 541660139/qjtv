package com.lwd.qjtv.mvp.ui.common;

import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.BasePresenter;
import com.lwd.qjtv.app.WEApplication;

/**
 * Created by jess on 8/5/16 13:13
 * contact with jess.yan.effort@gmail.com
 */
public abstract class WEActivity<P extends BasePresenter> extends BaseActivity<P> {
    @SuppressWarnings("unchecked")
    public final <E extends View> E getView(int id) {
        try {

            return (E) findViewById(id);

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        }
    }

    protected WEApplication mWeApplication;


}
