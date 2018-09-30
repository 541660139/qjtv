package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;

import com.jess.arms.utils.UiUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.lwd.qjtv.di.component.DaggerMatchSearchComponent;
import com.lwd.qjtv.di.module.MatchSearchModule;
import com.lwd.qjtv.mvp.contract.MatchSearchContract;
import com.lwd.qjtv.mvp.model.entity.SearchCollectionBean;
import com.lwd.qjtv.mvp.presenter.MatchSearchPresenter;

import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.ui.adapter.CollectionHotAdapter;
import com.lwd.qjtv.view.LoadingPageView;


import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MatchSearchActivity extends BaseActivity<MatchSearchPresenter> implements MatchSearchContract.View, DefaultAdapter.OnRecyclerViewItemClickListener<SearchCollectionBean.DataBean> {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_search_hot_lv)
    RecyclerView searchHotLv;
    @BindView(R.id.zq_refreshlayout)
    SmartRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.activity_search_delete_all_iv)
    ImageView deleteAllIv;
    @BindView(R.id.activity_search_edt)
    EditText searchEdt;
    @BindView(R.id.activity_search_cancel_tv)
    ImageView cancelTv;
    @BindView(R.id.activity_search_tv)
    TextView searchTv;
    @BindView(R.id.loading_page_view)
    LoadingPageView loadingPageView;
    private String searchString;
    private String id;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMatchSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .matchSearchModule(new MatchSearchModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_match_search; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            if (getIntent().hasExtra("id")) {
                id = getIntent().getStringExtra("id");
            }
        }
        loadingPageView.startLodingAnim();
        mPresenter.getHotSearch();
        initListener();
    }

    private void initListener() {
        deleteAllIv.setOnClickListener(v -> searchEdt.setText(""));
        searchTv.setOnClickListener(v -> {
            String s = searchEdt.getText().toString();
            searchString = s;
            mPresenter.requestSearchList(true, s, id);
            searchHotLv.setVisibility(View.GONE);
        });
        cancelTv.setOnClickListener(view -> finish());

        deleteAllIv.setOnClickListener(view -> {
            searchEdt.setText("");
            searchHotLv.setVisibility(View.VISIBLE);
        });

//        searchEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////                queryData(charSequence.toString());
////                listView.setVisibility(View.VISIBLE);
////                historyLl.setVisibility(View.VISIBLE);
////                listLl.setVisibility(View.GONE);
////                tipTv.setVisibility(View.GONE);
////                nodataRl.setVisibility(View.GONE);
//                mRecyclerView.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
        searchEdt.setOnEditorActionListener(
                (textView, i, keyEvent) -> {
                    if (i == EditorInfo.IME_ACTION_SEARCH) {
                        String s = searchEdt.getText().toString();
                        searchString = s;
                        mPresenter.requestSearchList(true, s, id);
                        return true;
                    }
                    return false;
                });
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
        Message message = (Message) o;
        switch (message.what) {
            case 1:
                List<SearchCollectionBean.DataBean> data = (List<SearchCollectionBean.DataBean>) message.obj;
                loadingPageView.checkData(data);
                searchHotLv.setVisibility(View.GONE);
                break;
            case 2:
                List<String> list = (List<String>) message.obj;
                UiUtils.configRecycleView(searchHotLv, new GridLayoutManager(this, 2));
                CollectionHotAdapter adapter = new CollectionHotAdapter(list);
                adapter.setOnItemClickListener((view, viewType, data1, position) -> {
                    searchEdt.setText((String) data1);
                });
                searchHotLv.setAdapter(adapter);
                break;
        }
    }


    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {

    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(this));
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int viewType, SearchCollectionBean.DataBean data, int position) {
        Intent intent = new Intent(this, VideoDetailsActivity.class);
        intent.putExtra("type", data.getV_type());
        intent.putExtra("id", data.getVideo_id());
        startActivity(intent);
    }
}
