package com.lwd.qjtv.mvp.common;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.IPresenter;
import com.lwd.qjtv.app.WEApplication;

/**
 * Created by jess on 8/5/16 13:13
 * contact with jess.yan.effort@gmail.com
 */
public abstract class WEActivity<P extends IPresenter> extends BaseActivity<P> {
    protected WEApplication mWeApplication;
    private boolean mFirstTimeApplySkin = true;

    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor;
    private long mL;
    private AlertDialog mLoadingDialog;


    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initLoadingDialog();
        super.onCreate(savedInstanceState);
    }

    private void initLoadingDialog() {

    }

    protected void showLoadingView(){
        if (mLoadingDialog!=null&&!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    protected void hideLoadingView(){
        if (mLoadingDialog!=null&&mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
        }
    }
}
