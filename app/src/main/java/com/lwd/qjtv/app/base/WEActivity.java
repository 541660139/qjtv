package com.lwd.qjtv.app.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.delegate.IActivity;
import com.jess.arms.mvp.IPresenter;
import com.umeng.analytics.MobclickAgent;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.Constant;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static com.jess.arms.base.delegate.ActivityDelegate.LAYOUT_FRAMELAYOUT;
import static com.jess.arms.base.delegate.ActivityDelegate.LAYOUT_LINEARLAYOUT;
import static com.jess.arms.base.delegate.ActivityDelegate.LAYOUT_RELATIVELAYOUT;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/11.
 */

public abstract class WEActivity<P extends IPresenter> extends  BaseActivity implements IActivity {

    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    protected P mPresenter;

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.equals(LAYOUT_FRAMELAYOUT)) {
            view = new AutoFrameLayout(context, attrs);
        }
        if (name.equals(LAYOUT_LINEARLAYOUT)) {
            view = new AutoLinearLayout(context, attrs);
        }
        if (name.equals(LAYOUT_RELATIVELAYOUT)) {
            view = new AutoRelativeLayout(context, attrs);
        }
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    @Override
    public boolean useEventBus() {
        return true;
    }

    /**
     * 这个Activity是否会使用Fragment,框架会根据这个属性判断是否注册{@link android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回false,那意味着这个Activity不需要绑定Fragment,那你再在这个Activity中绑定继承于 {@link com.jess.arms.base.BaseFragment} 的Fragment将不起任何作用
     * @return
     */
    @Override
    public boolean useFragment() {
        return true;
    }
    private View activityView;
    private ViewGroup containerView;
    private ImageView loadingIv;
    private TextView emptyTv;
    private ProgressDialog progressDialog;
    private LinearLayout loadingLl;
    private LinearLayout loadingFailLl;
    private LinearLayout loadingEmptyLl;

    //是否显示空页面
    private boolean isEmptyShow;
    private boolean isLoading;




    @Override
    protected void onStart() {
        super.onStart();
        findView();
        initLoadingProgress();
    }

    //加载进度框
    private void initLoadingProgress() {
//        containerView.addView(activityView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载");
        progressDialog.setCancelable(false);
    }

    private void findView() {
        View rootView = LayoutInflater.from(this).inflate(initView(), null);
        View view = rootView.findViewById(R.id.loading_layout);
        loadingIv = (ImageView) view.findViewById(R.id.activity_base_loading_iv);
        emptyTv = (TextView) view.findViewById(R.id.activity_base_loading_no_data_tv);
        loadingLl = (LinearLayout) view.findViewById(R.id.activity_base_loading_ll);
        loadingFailLl = (LinearLayout) view.findViewById(R.id.activity_base_loading_fail_ll);
        loadingEmptyLl = (LinearLayout) view.findViewById(R.id.activity_base_loading_no_data_ll);
    }

    //设置layoutId
    public abstract int getLayout();

    //设置是否加载
    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    //检查数据显示相应界面
    public String checkData(Object data) {

        if (data == null) {
            loadingEmpty();
            return Constant.LOADING_EMPTY;
        }
        if (data instanceof Integer && (int) data == -1) {
            loadingFail();
            return Constant.LOADING_FAIL;
        }
        if (data instanceof List && ((List) data).size() == 0) {
            loadingEmpty();
            return Constant.LOADING_EMPTY;
        }
        if (data instanceof Map && ((Map) data).size() == 0) {
            loadingEmpty();
            return Constant.LOADING_EMPTY;
        }
        hideLoadingPage();
        return Constant.LOADING_SUCCESS;
    }

    //开启加载动画
    public void startLodingAnim() {
        loadingLl.setVisibility(View.VISIBLE);
        loadingIv.setImageResource(R.drawable.rotate_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingIv.getDrawable();
        animationDrawable.start();
    }

    //停止加载动画
    public void stopLodingAnim() {
        loadingLl.setVisibility(View.GONE);
        loadingIv.setImageResource(R.drawable.rotate_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingIv.getDrawable();
        animationDrawable.stop();
    }

    //加载失败
    public void loadingFail() {
        isEmptyShow = false;
        loadingPage();
    }

    //加载数据为空
    public void loadingEmpty() {
        isEmptyShow = true;
        loadingPage();
    }

    //隐藏所有加载页面
    public void hideLoadingPage() {
        loadingFailLl.setVisibility(View.GONE);
        loadingEmptyLl.setVisibility(View.GONE);
    }

    //加载页面
    public void loadingPage() {
        stopLodingAnim();
        loadingEmptyLl.setVisibility(isEmptyShow ? View.VISIBLE : View.GONE);
        loadingFailLl.setVisibility(!isEmptyShow ? View.VISIBLE : View.GONE);
    }

    //设置加载空数据显示文字
    public void setEmptyTv(String emptyStr) {
        if (emptyStr == null || emptyStr.equals(""))
            return;
        emptyTv.setText(emptyStr);
    }

}
