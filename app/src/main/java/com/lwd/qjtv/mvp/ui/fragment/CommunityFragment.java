package com.lwd.qjtv.mvp.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Contact;
import com.lwd.qjtv.app.utils.GlideConfigGlobal;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.di.component.DaggerCommunityComponent;
import com.lwd.qjtv.di.module.CommunityModule;
import com.lwd.qjtv.mvp.contract.CommunityContract;
import com.lwd.qjtv.mvp.model.entity.Auto;
import com.lwd.qjtv.mvp.model.entity.BBSDataBean;
import com.lwd.qjtv.mvp.presenter.CommunityPresenter;
import com.lwd.qjtv.mvp.ui.activity.GuessCenterActivity;
import com.lwd.qjtv.mvp.ui.activity.GuessChampionActivity;
import com.lwd.qjtv.mvp.ui.activity.LiveActivity;
import com.lwd.qjtv.mvp.ui.activity.LoginActivity;
import com.lwd.qjtv.mvp.ui.activity.MoreBetActivity;
import com.lwd.qjtv.mvp.ui.activity.MyBBSMessageListActivity;
import com.lwd.qjtv.mvp.ui.activity.StarDetailsActivity;
import com.lwd.qjtv.mvp.ui.activity.VideoDetailsActivity;
import com.lwd.qjtv.mvp.ui.activity.WebActivity;
import com.lwd.qjtv.mvp.ui.activity.WebViewActivity;
import com.lwd.qjtv.mvp.ui.adapter.StarAdapter;
import com.lwd.qjtv.view.AutoRollLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CommunityFragment extends BaseFragment<CommunityPresenter> implements CommunityContract.View, OnRefreshListener, OnLoadmoreListener, DefaultAdapter.OnRecyclerViewItemClickListener {


    @BindView(R.id.fragment_community_banner_auto)
    AutoRollLayout fragmentCommunityBannerAuto;
    @BindView(R.id.fragment_community_recyclerview)
    RecyclerView fragmentCommunityRecyclerview;
    @BindView(R.id.fragment_community_smartrefreshlayout)
    SmartRefreshLayout fragmentCommunitySmartrefreshlayout;
    @BindView(R.id.fragment_community_message_iv)
    ImageView messageIv;
    @BindView(R.id.fragment_community_message_tv)
    TextView messageTv;
    //明星视频
    @BindView(R.id.fragment_watch_ball_star_recyclerview)
    RecyclerView fragment_watch_ball_star_recyclerview;

    //明星标题
    @BindView(R.id.fragment_watch_ball_star_title_tv)
    TextView titleTv;
    //明星图标
    @BindView(R.id.fragment_watch_ball_star_iv)
    ImageView starIv;

    private List<AutoRollLayout.IShowItem> items = new ArrayList<>();
    private List<BBSDataBean.DataBean.BannerBean> advList = new ArrayList<>();
    private Contact contact;

    private List<BBSDataBean.DataBean.StarListBean.DataListBean> startList = new ArrayList<>();

    public static CommunityFragment newInstance() {
        CommunityFragment fragment = new CommunityFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerCommunityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .communityModule(new CommunityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getBBSData(true);
    }

    @Override
    public void initData() {
        initAutoRollLayout();
        initListener();
    }

    private void initAutoRollLayout() {
        fragmentCommunityBannerAuto.setItems(items);
    }

    private void initListener() {
        fragmentCommunitySmartrefreshlayout.setOnRefreshListener(this);
        fragmentCommunitySmartrefreshlayout.setOnLoadmoreListener(this);
        messageTv.setOnClickListener(v -> {
            if (Preference.getBoolean(Constant.IS_LOGIN))
                startActivity(new Intent(getContext(), MyBBSMessageListActivity.class));
            else
                startActivity(new Intent(getContext(), LoginActivity.class));
        });

        fragmentCommunityBannerAuto.setOnItemClickListener(index -> jumpBanner(index % advList.size()));

    }


    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {
        if (data instanceof BBSDataBean.DataBean) {
            setDataToBanner((BBSDataBean.DataBean) data);
//            setStarRecycleData((BBSDataBean.DataBean) data);
        }
        if (data instanceof Integer) {
            int code = (int) data;
            messageIv.setVisibility(code == 1 ? View.VISIBLE : View.GONE);
        }

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        fragmentCommunitySmartrefreshlayout.finishRefresh();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
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
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {
        fragmentCommunitySmartrefreshlayout.finishLoadmore();
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        UiUtils.configRecycleView(fragmentCommunityRecyclerview, new LinearLayoutManager(getContext()));
        fragmentCommunityRecyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);


    }

//    设置明星视频

    private void setStarRecycleData(BBSDataBean.DataBean data) {


        /**
         * 设置明星视频标题和图标
         * */
        titleTv.setText(data.getStarList().getColumn_name());
        AppComponent appComponent = ((App) getContext().getApplicationContext()).getAppComponent();
        ImageLoader imageLoader = appComponent.imageLoader();
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(data.getStarList().getIcon(), starIv));

        List<BBSDataBean.DataBean.StarListBean.DataListBean> dataList = data.getStarList().getDataList();
        startList.clear();
        startList.addAll(dataList);

        UiUtils.configRecycleView(fragment_watch_ball_star_recyclerview, new LinearLayoutManager(WEApplication.getContext(), LinearLayoutManager.HORIZONTAL, false));
        StarAdapter adapter = new StarAdapter(startList);
        fragment_watch_ball_star_recyclerview.setAdapter(adapter);

        adapter.setOnItemClickListener((view, viewType, data1, position) -> {
            if (!SaveUserInfo.getLogin()) {
                getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                return;
            }
            Intent intent = new Intent(getContext(), StarDetailsActivity.class);
            BBSDataBean.DataBean.StarListBean.DataListBean dataBean = (BBSDataBean.DataBean.StarListBean.DataListBean) data1;
            intent.putExtra("starId", dataBean.getId() + "");
            startActivity(intent);
        });

    }

    //设置数据轮播图
    private void setDataToBanner(BBSDataBean.DataBean data) {
        List<BBSDataBean.DataBean.BannerBean> bannerList = data.getBanner();
        items.clear();
        advList.clear();
        for (int i = 0; i < (bannerList == null ? 0 : bannerList.size()); i++) {
            BBSDataBean.DataBean.BannerBean bannerListBean = bannerList.get(i);
            Auto auto = new Auto(bannerListBean.getPicture(), bannerListBean.getTitle());
            items.add(auto);
            advList.add(bannerListBean);
        }
        fragmentCommunityBannerAuto.getItemList().addAll(items);
        fragmentCommunityBannerAuto.notifyPage();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.getBBSData(true);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.getBBSData(false);
    }

    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {
        BBSDataBean.DataBean.ListBean databean = (BBSDataBean.DataBean.ListBean) data;
        Map<String, String> map = new HashMap<>();
        map.put("id", databean.getId());
        if (contact == null)
            contact = new Contact(getContext());
        contact.startWebActivity(Constant.BBS_CARD_LIST, map);
    }


    private void jumpBanner(int index) {
        if (advList == null || advList.size() == 0)
            return;

        BBSDataBean.DataBean.BannerBean bannerBean = advList.get(index);
        switch (bannerBean.getLink_type()) {

            case "2":
                //视频详情页
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                Intent intent1 = new Intent(getContext(), VideoDetailsActivity.class);
                intent1.putExtra("id", bannerBean.getLink());
                intent1.putExtra("pic", bannerBean.getPicture());
                intent1.putExtra("type", bannerBean.getV_type());
                startActivity(intent1);
                break;
            case "3":
                //跳转商城
                Intent intent2 = new Intent(WEApplication.getContext(), WebViewActivity.class);
                intent2.putExtra("id", bannerBean.getLink());
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                break;
            case "4":
                //竟猜中心
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getContext(), GuessCenterActivity.class));
                break;
            case "5":
                //冠军竞猜
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                Intent intent3 = new Intent(getContext(), GuessChampionActivity.class);
                intent3.putExtra("id", bannerBean.getLink());
                startActivity(intent3);
                break;
            case "6":
                //多选竞猜
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getContext(), MoreBetActivity.class));
                break;
            case "7":
                //活动详情页
                Intent intent4 = new Intent(getContext(), WebActivity.class);
                intent4.putExtra("url", bannerBean.getLink_url());
                startActivity(intent4);
                break;
            case "8":
                Intent intent6 = new Intent();
                intent6.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(bannerBean.getLink_url());
                intent6.setData(content_url);
                startActivity(intent6);
                break;
            case "9":
                //直播间
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                Intent intent5 = new Intent(getContext(), LiveActivity.class);
                intent5.putExtra("id", bannerBean.getId());
                intent5.putExtra("match_id", bannerBean.getId());
                intent5.putExtra("url", bannerBean.getLink_url());
                intent5.putExtra("title", bannerBean.getTitle());
                intent5.putExtra("moveText", "");
                intent5.putExtra("moveSpeed", "");
                intent5.putExtra("origin", bannerBean.getOrigin());
                intent5.putExtra("erweimaUrl", "");
                intent5.putExtra("isNBA", false);
                startActivity(intent5);
                break;
            case "10":
                final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=" + bannerBean.getLink() + "&version=1";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
                break;
        }
    }
}
