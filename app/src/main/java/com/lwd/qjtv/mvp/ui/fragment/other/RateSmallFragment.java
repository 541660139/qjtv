package com.lwd.qjtv.mvp.ui.fragment.other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.mvp.contract.RateSmallContract;
import com.lwd.qjtv.mvp.model.entity.WatchBallBean;
import com.lwd.qjtv.mvp.presenter.RateSmallPresenter;
import com.lwd.qjtv.mvp.ui.activity.LiveActivity;
import com.lwd.qjtv.mvp.ui.activity.LoginActivity;
import com.lwd.qjtv.view.RateItemView;


import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/13.
 */

public class RateSmallFragment extends BaseFragment<RateSmallPresenter> implements RateSmallContract.View {
    //    private static RateSmallFragment fragment;
    @BindView(R.id.fragment_rate_small_left_rateitemview)
    RateItemView leftRiv;
    @BindView(R.id.fragment_rate_small_right_rateitemview)
    RateItemView rightRiv;

    private WatchBallBean.DataBean.LiveListBean.DataListBean leftData;
    private WatchBallBean.DataBean.LiveListBean.DataListBean rightData;

    public RateSmallFragment() {

    }


    //    public static RateSmallFragment newInstance() {
    public static RateSmallFragment newInstance(WatchBallBean.DataBean.LiveListBean.DataListBean leftData, WatchBallBean.DataBean.LiveListBean.DataListBean rightData) {
        RateSmallFragment fragment = new RateSmallFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("leftData", leftData);
        if (rightData != null) {
            bundle.putSerializable("rightData", rightData);
        }
        fragment.setArguments(bundle);
        return fragment;
    }


    public void parseData(Context context, WatchBallBean.DataBean.LiveListBean.DataListBean leftData, WatchBallBean.DataBean.LiveListBean.DataListBean rightData) {
        this.leftData = leftData;
        this.rightData = rightData;
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_rate_small, null, false);
        ButterKnife.bind(this, view);
        initData();
//        if (leftRiv != null)
//            initData();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            leftData = (WatchBallBean.DataBean.LiveListBean.DataListBean) args.getSerializable("leftData");
            if (args.containsKey("rightData"))
                rightData = (WatchBallBean.DataBean.LiveListBean.DataListBean) args.getSerializable("rightData");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate_small, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
//        DaggerRateSmallComponent
//                .builder()
//                .appComponent(appComponent)
//                .rateSmallModule(new RateSmallModule(this))//请将RateSmallModule()第一个首字母改为小写
//                .build()
//                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_rate_small, container, false);
    }

    @Override
    public void initData() {
        leftRiv.setData(leftData);
        if (rightData == null)
            rightRiv.setVisibility(View.INVISIBLE);
        else
            rightRiv.setData(rightData);
//        initLeftRiv();
//        initRightRiv();
        initListener();
    }

//    private void initLeftRiv() {
//        //左边模块 左边按钮
//        leftRiv.findViewById(R.id.item_rate_view_layout_left_tv).setOnClickListener(view -> {
//            long l = Long.parseLong(leftData.getStartTimestamp()) * 1000;
//            if (l >= System.currentTimeMillis()) {
//                showMessage("尚未开播");
//            } else {
//                Intent intent = new_pic Intent(getContext(), LiveActivity.class);
//                intent.putExtra("id", leftData.getId());
//                intent.putExtra("match_id", leftData.getId());
//                intent.putExtra("url", leftData.getPlayUrl());
//                intent.putExtra("title", leftData.getMatcheName());
//                startActivity(intent);
//            }
//        });
//        //左边模块 右边按钮
//        leftRiv.findViewById(R.id.item_rate_view_layout_right_tv).setOnClickListener(view -> {
//            long l = Long.parseLong(leftData.getStartTimestamp()) * 1000;
//            if (l >= System.currentTimeMillis()) {
//                //// TODO: 2017/7/19 预约
//                long startTime = Long.parseLong();
//                long time = startTime - System.currentTimeMillis();
//                AlarmService.addNotification((int) time, "预约提醒", data.getMatcheName(), starInfoBean.getName() + "VS" + starInfoBean1.getName());
//            } else {
//                Intent intent = new_pic Intent(getContext(), LiveActivity.class);
//                intent.putExtra("id", leftData.getId());
//                intent.putExtra("match_id", leftData.getId());
//                intent.putExtra("url", leftData.getPlayUrl());
//                intent.putExtra("title", leftData.getMatcheName());
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void initRightRiv() {
//        //右边模块 左边按钮
//        rightRiv.findViewById(R.id.item_rate_view_layout_left_tv).setOnClickListener(view -> {
//            long l = Long.parseLong(rightData.getStartTimestamp()) * 1000;
//            if (l >= System.currentTimeMillis()) {
//                showMessage("尚未开播");
//            } else {
//                Intent intent = new_pic Intent(getContext(), LiveActivity.class);
//                intent.putExtra("id", rightData.getId());
//                intent.putExtra("match_id", rightData.getId());
//                intent.putExtra("url", rightData.getPlayUrl());
//                intent.putExtra("title", rightData.getMatcheName());
//                startActivity(intent);
//            }
//        });
//        //右边模块 右边按钮
//        rightRiv.findViewById(R.id.item_rate_view_layout_right_tv).setOnClickListener(view -> {
//            long l = Long.parseLong(rightData.getStartTimestamp()) * 1000;
//            if (l >= System.currentTimeMillis()) {
//                //// TODO: 2017/7/19 预约
//            } else {
//                Intent intent = new_pic Intent(getContext(), LiveActivity.class);
//                intent.putExtra("id", rightData.getId());
//                intent.putExtra("match_id", rightData.getId());
//                intent.putExtra("url", rightData.getPlayUrl());
//                intent.putExtra("title", rightData.getMatcheName());
//                startActivity(intent);
//            }
//        });
//    }


    public interface OnRateSmallFragmentClick{
        void onRateSmallFragmentClick();
    }

    private OnRateSmallFragmentClick onRateSmallFragmentClick;
    public void setOnRateSmallFragmentClick(OnRateSmallFragmentClick onRateSmallFragmentClick){
        this.onRateSmallFragmentClick=onRateSmallFragmentClick;
    }



    private void initListener() {
        //左边模块
        leftRiv.setOnClickListener(view -> {
            onRateSmallFragmentClick.onRateSmallFragmentClick();

            long l = Long.parseLong(leftData.getStartTimestamp()) * 1000;
            if (!leftData.getIsLive().equals("1")) {
                showMessage("尚未开播");
            } else {
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                Intent intent = new Intent(getContext(), LiveActivity.class);
                intent.putExtra("id", leftData.getId());
                intent.putExtra("match_id", leftData.getId());
                intent.putExtra("url", leftData.getPlayUrl());
                intent.putExtra("title", leftData.getMatcheName());
                intent.putExtra("moveText", leftData.getMoveText());
                intent.putExtra("moveSpeed", leftData.getMoveSpeed());
                intent.putExtra("origin", leftData.getOrigin());
                intent.putExtra("gonggao", leftData.getGonggao());
                intent.putExtra("erweimaUrl", leftData.getErweimaUrl());
                intent.putExtra("isNBA", false);
                startActivity(intent);
            }
        });
        //右边模块
        rightRiv.setOnClickListener(view -> {

            onRateSmallFragmentClick.onRateSmallFragmentClick();


            long l = Long.parseLong(rightData.getStartTimestamp()) * 1000;
            if (!rightData.getIsLive().equals("1")) {
                showMessage("尚未开播");
            } else {
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                Intent intent = new Intent(getContext(), LiveActivity.class);
                intent.putExtra("id", rightData.getId());
                intent.putExtra("match_id", rightData.getId());
                intent.putExtra("url", rightData.getPlayUrl());
                intent.putExtra("title", rightData.getMatcheName());
                intent.putExtra("moveText", rightData.getMoveText());
                intent.putExtra("moveSpeed", rightData.getMoveSpeed());
                intent.putExtra("origin", rightData.getOrigin());
                intent.putExtra("gonggao", leftData.getGonggao());
                intent.putExtra("erweimaUrl", rightData.getErweimaUrl());
                intent.putExtra("isNBA", false);
                startActivity(intent);
            }
        });

    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onActivityCreated
     * 还没执行,setData里调用presenter的方法时,是会报空的,因为dagger注入是在onActivityCreated
     * 方法中执行的,如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.makeText(getContext(), message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void update(Object left, Object right) {
        this.leftData = (WatchBallBean.DataBean.LiveListBean.DataListBean) left;
        if (right != null)
            this.rightData = (WatchBallBean.DataBean.LiveListBean.DataListBean) right;
        else
            rightData = null;
        Bundle args = getArguments();
        if (args != null) {
            args.putSerializable("leftData", leftData);
            if (rightData != null)
                args.putSerializable("rightData", rightData);
        }
    }

}