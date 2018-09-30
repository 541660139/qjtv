package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Contact;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.di.component.DaggerMyCommunityComponent;
import com.lwd.qjtv.di.module.MyCommunityModule;
import com.lwd.qjtv.mvp.contract.MyCommunityContract;
import com.lwd.qjtv.mvp.model.entity.MyCommunityListDataBean;
import com.lwd.qjtv.mvp.presenter.MyCommunityPresenter;

import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.ui.adapter.MyCommunityAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MyCommunityActivity extends BaseActivity<MyCommunityPresenter> implements MyCommunityContract.View, OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.activity_my_community_recyclerview)
    SwipeMenuRecyclerView recyclerView;
    @BindView(R.id.activity_my_community_smartrefreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.activity_my_community_empty_ll)
    LinearLayout emptyLl;

    @BindView(R.id.tv_tixing)
    TextView tv_tixing;

    @BindView(R.id.ll_data)
    LinearLayout ll_data;



    private MyCommunityAdapter adapter;
    private Contact contact;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMyCommunityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myCommunityModule(new MyCommunityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_my_community; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData() {
        setTitle("我的帖子");
        mPresenter.requestCommunityList(true);
        initRecycleView();
        initListener();
    }

    private void initListener() {
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        smartRefreshLayout.finishRefresh();
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
        finish();
    }

    @Override
    public void setData(Object o) {
        if (o instanceof Boolean) {
            emptyLl.setVisibility((boolean) o ? View.VISIBLE : View.GONE);
            ll_data.setVisibility((boolean) o ? View.GONE : View.VISIBLE);
        } else if (!(o instanceof String)) {

            recyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {
                    MyCommunityListDataBean.DataBean dataBean = ((List<MyCommunityListDataBean.DataBean>) o).get(position);
                    if (contact == null)
                        contact = new Contact(MyCommunityActivity.this);

                    HashMap<String, String> map = new HashMap<>();
                    map.put("card_id", dataBean.getId());
                    map.put("p", "1");
                    String webUrl = contact.getWebUrl(Constant.BBS_CARD_INFO, map);
                    Intent intent = new Intent(MyCommunityActivity.this, WebNewActivity.class);
                    intent.putExtra("url", webUrl);
                    intent.putExtra("card_id", dataBean.getId());
                    intent.putExtra("from_uid", Preference.get(Constant.UID, "0"));
                    intent.putExtra("main_id", dataBean.getVid());
                    intent.putExtra("is_bbs_details", true);
                    startActivity(intent);
                }
            });
        }

    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestCommunityList(true);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.requestCommunityList(false);
    }

    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {
        smartRefreshLayout.finishLoadmore();
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        //设置侧滑菜单
        recyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        // 设置监听器。
        recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);


        UiUtils.configRecycleView(recyclerView, new LinearLayoutManager(this));
    }

    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = (swipeLeftMenu, swipeRightMenu, viewType) -> {
        // 注意：哪边不想要菜单，那么不要添加即可。
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        int width = UiUtils.dip2px(MyCommunityActivity.this, 70);
        {
            SwipeMenuItem bjItem = new SwipeMenuItem(MyCommunityActivity.this)
                    .setBackground(R.color.color9a9a9a)
                    .setText("编辑")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);

            SwipeMenuItem deleteItem = new SwipeMenuItem(MyCommunityActivity.this)
                    .setBackground(R.color.colorOrigin)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);

            swipeRightMenu.addMenuItem(bjItem);// 添加菜单到左侧。
            swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (menuPosition == 1) {
                // Item被侧滑删除时，删除数据，并更新adapter。
                // TODO: 2018/1/29
                MyCommunityListDataBean.DataBean dataBean = adapter.getInfos().get(adapterPosition);
                mPresenter.deleteCommunity(dataBean.getId());
            } else if (menuPosition == 0) {
//                进入帖子编辑界面
                MyCommunityListDataBean.DataBean dataBean = adapter.getInfos().get(adapterPosition);
                Intent intent = new Intent(MyCommunityActivity.this, PushCommunityActivity.class);
                intent.putStringArrayListExtra("pic_url_list", (ArrayList<String>) dataBean.getPic_h());
                intent.putExtra("vid", dataBean.getVid());
                intent.putExtra("is_edit", true);
                intent.putExtra("title", dataBean.getTitle());
                intent.putExtra("content", dataBean.getMain_txt());
                startActivity(intent);

            }
        }
    };


    @Override
    public void setAdapter(DefaultAdapter adapter) {
        this.adapter = (MyCommunityAdapter) adapter;
        recyclerView.setAdapter(adapter);
    }
}
