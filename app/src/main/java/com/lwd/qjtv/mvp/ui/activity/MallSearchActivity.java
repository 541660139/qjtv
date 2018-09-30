package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.MallRecordSQLiteOpenHelper;
import com.lwd.qjtv.di.component.DaggerMallSearchComponent;
import com.lwd.qjtv.di.module.MallSearchModule;
import com.lwd.qjtv.mvp.contract.MallSearchContract;
import com.lwd.qjtv.mvp.model.entity.MallListBean;
import com.lwd.qjtv.mvp.model.entity.SearchMallBean;
import com.lwd.qjtv.mvp.presenter.MallSearchPresenter;
import com.lwd.qjtv.mvp.ui.adapter.SearchHistoryAdapter;
import com.lwd.qjtv.mvp.ui.holder.SearchHistoryItemHolder;
import com.lwd.qjtv.view.CustomLoadingListItemCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/15.
 */

public class MallSearchActivity extends BaseActivity<MallSearchPresenter> implements MallSearchContract.View, OnRefreshListener, OnLoadmoreListener, SearchHistoryItemHolder.CursorItemCLickListener, DefaultAdapter.OnRecyclerViewItemClickListener<Object> {

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    private String words = "";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.zq_refreshlayout)
    SmartRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.activity_search_edt)
    EditText searchEdt;
    //    @BindView(R.id.activity_search_lv)
//    GridView listView;
    @BindView(R.id.activity_search_delete_tv)
    TextView deleteTv;
    @BindView(R.id.activity_search_tv)
    TextView searchTv;
    @BindView(R.id.activity_search_history_tv)
    TextView historyTv;
    @BindView(R.id.activity_search_cancel_tv)
    ImageView cancelTv;
    @BindView(R.id.activity_search_delete_all_iv)
    ImageView deleteAllIv;
    @BindView(R.id.activity_search_history_lv)
    RecyclerView listView;
    @BindView(R.id.search_activity_search_ll)
    RelativeLayout historyLl;
    @BindView(R.id.search_activity_list_ll)
    RelativeLayout dataLL;
    private MallRecordSQLiteOpenHelper helper = new MallRecordSQLiteOpenHelper(this);
    private SQLiteDatabase db;
    private SearchHistoryAdapter adapter;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions = new RxPermissions(this);
        DaggerMallSearchComponent
                .builder()
                .appComponent(appComponent)
                .mallSearchModule(new MallSearchModule(this))  //首字母改成小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
//        View view = LayoutInflater.from(getApplicationContext()).inflate(null, null);
//        mZQRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.zq_refreshlayout);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        return R.layout.activity_search;
    }


    @Override
    public void initData() {
        queryData("");
        initListener();
//        mPresenter.requestMallSearchList(true);//打开app时自动加载列表
    }


    private void initListener() {
        cancelTv.setOnClickListener(view -> finish());

        deleteAllIv.setOnClickListener(v -> searchEdt.setText(""));

        searchTv.setOnClickListener(v -> {
            String s = searchEdt.getText().toString();
            words = s;
            mPresenter.requestMallSearchList(true, s);
            if (s != null && !s.trim().equals(""))
                searchData();
        });

        deleteTv.setOnClickListener(view -> {
            deleteData();
            queryData("");
        });

        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                queryData(charSequence.toString());
                listView.setVisibility(View.VISIBLE);
                historyLl.setVisibility(View.VISIBLE);
//                tipTv.setVisibility(View.GONE);
//                nodataRl.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                dataLL.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        searchEdt.setOnEditorActionListener(
                (textView, i, keyEvent) -> {
                    if (i == EditorInfo.IME_ACTION_SEARCH) {
                        String s = searchEdt.getText().toString();
                        words = s;
                        mPresenter.requestMallSearchList(true, s);
                        if (s != null && !s.trim().equals(""))
                            searchData();
                        return true;
                    }
                    return false;
                });
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setOnLoadmoreListener(this);
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setOnLoadmoreListener(this);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(WEApplication.getContext()));
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.finishRefresh();
    }

    @Override
    public void showMessage(String message) {
//        UiUtils.SnackbarText(message);
        UiUtils.makeText(this,message);
    }

    @Override
    public void launchActivity(Intent intent) {
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void setData(Object o) {

    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        initRecycleView();
//        initPaginate();
    }

    /**
     * 开始加载更多
     */
    @Override
    public void startLoadMore() {
        isLoadingMore = true;
    }

    /**
     * 结束加载更多
     */
    @Override
    public void endLoadMore() {
        isLoadingMore = false;
        mSwipeRefreshLayout.finishLoadmore();
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    /**
     * 初始化Paginate,用于加载更多
     */
    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {

                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return false;
                }
            };

            mPaginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(10)
                    .setLoadingListItemCreator(new CustomLoadingListItemCreator())
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mRxPermissions = null;
        this.mPaginate = null;
    }


    private void searchData() {
        boolean hasData = hasData(searchEdt.getText().toString().trim());
        if (!hasData) {
            insertData(searchEdt.getText().toString().trim());
            queryData("");
        }
        // TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
//                    Toast.makeText(SearchActivity.this, "clicked!", Toast.LENGTH_SHORT).show();
        String tempName = searchEdt.getText().toString();
        if (tempName.length() == 0) {
//            tv_tip.setText("搜索历史");
            listView.setVisibility(View.VISIBLE);
            historyLl.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            dataLL.setVisibility(View.GONE);
//            zqRefreshLayout.setVisibility(View.GONE);
//            tipTv.setVisibility(View.GONE);
//            listLl.setVisibility(View.GONE);
//            radioGroup.setVisibility(View.GONE);
        } else {
//            tv_tip.setText("搜索结果");
//            searchLl.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
            historyLl.setVisibility(View.GONE);
//            zqRefreshLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            dataLL.setVisibility(View.VISIBLE);
//            tipTv.setVisibility(View.VISIBLE);
//            listLl.setVisibility(View.VISIBLE);
//            radioGroup.setVisibility(View.VISIBLE);
        }
//        getIntData(tempName);
//        getNewSearchData(tempName);
        // 根据tempName去模糊查询数据库中有没有数据
        queryData(tempName);
    }

    List<String> list = new ArrayList<>();


    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        findColumns(cursor, new String[]{"name"});
        list.clear();
        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor
                    .getColumnIndex("name")));
        }
        // 创建adapter适配器对象 R.layout.cursor_text_style
//        adapter = new_pic SimpleCursorAdapter(this, R.layout.cursor_text_style, cursor, new_pic String[]{"name"},
//                new_pic int[]{R.id.cursor_text}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器

//        listView.setDivider(new_pic ColorDrawable(Color.GRAY));
//        adapter = new_pic SearchCursorAdapter(this, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        adapter = new SearchHistoryAdapter(list);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
        adapter.setOnCursorItemClickListener(this);
        adapter.setOnItemClickListener(this);
        // TODO: 2017/6/10  单个删除
//        View v = LayoutInflater.from(this).inflate(R.layout.cursor_text_style, null);
//        v.findViewById(R.id.search_delete_iv).setOnClickListener(view1 -> {
//            Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
//        });
//        listView.setOnItemClickListener((adapterView, view, i, l) -> {
//            TextView nameTv = (TextView) view.findViewById(R.id.cursor_text);
//            searchEdt.setText(nameTv.getText().toString());
//        });
        adapter.notifyDataSetChanged();
    }

    private int[] mFrom;

    private void findColumns(Cursor c, String[] from) {
        if (c != null) {
            int i;
            int count = from.length;
            if (mFrom == null || mFrom.length != count) {
                mFrom = new int[count];
            }
            for (i = 0; i < count; i++) {
                mFrom[i] = c.getColumnIndexOrThrow(from[i]);
            }
        } else {
            mFrom = null;
        }
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 删除单个数据
     */
    private void deleteOneData(String name) {
        db = helper.getWritableDatabase();
        db.delete("records", "name=?", new String[]{name});
//        db.execSQL("delete from records where _id=" + position);
        db.close();
        queryData("");
    }


    @Override
    public void click(String name) {
        deleteOneData(name);
    }


    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.requestMallSearchList(false, words);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestMallSearchList(true, words);
    }

    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {
        if (data instanceof String) {
            searchEdt.setText((String) data);
        } else if (data instanceof SearchMallBean.DataBean) {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("id", ((MallListBean.DataBean) data).getGoods_id());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
