package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.GridLayoutManager;
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
import com.lwd.qjtv.app.utils.RecordSQLiteOpenHelper;
import com.lwd.qjtv.di.component.DaggerSearchComponent;
import com.lwd.qjtv.di.module.SearchModule;
import com.lwd.qjtv.mvp.contract.SearchContract;
import com.lwd.qjtv.mvp.model.entity.SearchBean;
import com.lwd.qjtv.mvp.model.entity.SearchBean.DataBean;
import com.lwd.qjtv.mvp.presenter.SearchPresenter;
import com.lwd.qjtv.mvp.ui.adapter.SearchHistoryAdapter;
import com.lwd.qjtv.mvp.ui.holder.SearchHistoryItemHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View, OnRefreshListener, OnLoadmoreListener, SearchHistoryItemHolder.CursorItemCLickListener, DefaultAdapter.OnRecyclerViewItemClickListener<String> {

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
    @BindView(R.id.activity_search_delete_all_iv)
    ImageView deleteAllTv;
    @BindView(R.id.activity_search_delete_tv)
    TextView deleteTv;
    @BindView(R.id.activity_search_tv)
    TextView searchTv;
    @BindView(R.id.search_activity_list_ll)
    RelativeLayout listLl;
    @BindView(R.id.activity_search_history_tv)
    TextView historyTv;
    @BindView(R.id.activity_search_cancel_tv)
    ImageView cancelTv;
    @BindView(R.id.activity_search_history_lv)
    RecyclerView listView;
    @BindView(R.id.search_activity_search_ll)
    RelativeLayout historyLl;
    private String searchString;
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);
    private SQLiteDatabase db;
    private SearchHistoryAdapter adapter;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions = new RxPermissions(this);
        DaggerSearchComponent
                .builder()
                .appComponent(appComponent)
                .searchModule(new SearchModule(this))  //首字母改成小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_search;
    }


    @Override
    public void initData() {
        queryData("");
        initListener();
    }


    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setOnLoadmoreListener(this);
        UiUtils.configRecycleView(mRecyclerView, new GridLayoutManager(this, 2));
    }


    private void initListener() {
        deleteAllTv.setOnClickListener(v -> searchEdt.setText(""));
        searchTv.setOnClickListener(v -> {
            String s = searchEdt.getText().toString();
            searchString = s;
            mPresenter.requestSearchList(true, s);
            if (s != null && !s.trim().equals(""))
                searchData();
        });
        cancelTv.setOnClickListener(view -> finish());

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
                listLl.setVisibility(View.GONE);
//                tipTv.setVisibility(View.GONE);
//                nodataRl.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        searchEdt.setOnEditorActionListener(
                (textView, i, keyEvent) -> {
                    if (i == EditorInfo.IME_ACTION_SEARCH) {
                        String s = searchEdt.getText().toString();
                        searchString = s;
                        mPresenter.requestSearchList(true, s);
                        if (s != null && !s.trim().equals(""))
                            searchData();
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
        mSwipeRefreshLayout.finishRefresh();
    }

    @Override
    public void showMessage(String message) {
//        UiUtils.SnackbarText(message);
        UiUtils.makeText(this, message);
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
        adapter.setOnItemClickListener((view, viewType, data, position) -> {
            SearchBean.DataBean dataBean = (DataBean) data;
            switch (dataBean.getV_type()) {
                case "1":
                    Intent intent = new Intent(this, VideoDetailsActivity.class);
                    intent.putExtra("pic", dataBean.getPic_h());
                    intent.putExtra("id", dataBean.getId());
                    intent.putExtra("type", dataBean.getV_type());
                    startActivity(intent);
                    break;
                case "2":
                    Intent intent1 = new Intent(this, VideoDetailsActivity.class);
                    intent1.putExtra("pic", dataBean.getPic_h());
                    intent1.putExtra("id", dataBean.getId());
                    intent1.putExtra("type", dataBean.getV_type());
                    startActivity(intent1);
                    break;
                case "3":
                    Intent intent2 = new Intent(this, LearnBallDetailsActivity.class);
                    intent2.putExtra("starId", dataBean.getId());
                    intent2.putExtra("videoId", dataBean.getId());
                    intent2.putExtra("v_type", dataBean.getV_type());
                    intent2.putExtra("pic", dataBean.getPic_h());
                    startActivity(intent2);
                    break;
                case "4":
                    Intent intent3 = new Intent(this, LearnBallDetailsActivity.class);
                    intent3.putExtra("starId", dataBean.getId());
                    intent3.putExtra("videoId", dataBean.getId());
                    intent3.putExtra("v_type", dataBean.getV_type());
                    intent3.putExtra("pic", dataBean.getPic_h());
                    startActivity(intent3);
                    break;
            }
        });
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
            listLl.setVisibility(View.GONE);
//            zqRefreshLayout.setVisibility(View.GONE);
//            tipTv.setVisibility(View.GONE);
//            listLl.setVisibility(View.GONE);
//            radioGroup.setVisibility(View.GONE);
        } else {
//            tv_tip.setText("搜索结果");
//            searchLl.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
            historyLl.setVisibility(View.GONE);
            listLl.setVisibility(View.VISIBLE);
//            zqRefreshLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
//            tipTv.setVisibility(View.VISIBLE);
//            listLl.setVisibility(View.VISIBLE);
//            radioGroup.setVisibility(View.VISIBLE);
        }
//        getIntData(tempName);
//        getNewSearchData(tempName);
        // 根据tempName去模糊查询数据库中有没有数据
        queryData(tempName);
    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

//    /**
//     * 模糊查询数据
//     */
//    private void queryData(String tempName) {
//        Cursor cursor = helper.getReadableDatabase().rawQuery(
//                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
//        // 创建adapter适配器对象 R.layout.cursor_text_style
//        adapter = new_pic CustomCursorAdapter(this, R.layout.cursor_text_style, cursor, new_pic String[]{"name"},
//                new_pic int[]{R.id.cursor_text}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
//        // 设置适配器
//        adapter.setCursorDeleteCallback(this);
//        listView.setDivider(new_pic ColorDrawable(Color.GRAY));
//        listView.setAdapter(adapter);
//        // TODO: 2017/6/10  单个删除
//        View v = LayoutInflater.from(this).inflate(R.layout.cursor_text_style, null);
//        v.findViewById(R.id.search_delete_iv).setOnClickListener(view1 -> {
//            Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
//        });
//        listView.setOnItemClickListener((adapterView, view, i, l) -> {
//            TextView nameTv = (TextView) view.findViewById(R.id.cursor_text);
//            searchEdt.setText(nameTv.getText().toString());
//        });
//        adapter.notifyDataSetChanged();

//    }

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
        List<String> dataList = new ArrayList<>();
        if (list.size() >= 10)
            dataList.addAll(list.subList(0, 10));
        else
            dataList.addAll(list);
        adapter = new SearchHistoryAdapter(dataList);
        adapter.setOnItemClickListener(this);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
        adapter.setOnCursorItemClickListener(this);

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
     * 删除单个数据
     */
    private void deleteOneData(String name) {
        db = helper.getWritableDatabase();
        db.delete("records", "name=?", new String[]{name});
        db.close();
        queryData("");
    }

    /**
     * 删除单个数据
     */
    private void deleteOneData(int position) {
        db = helper.getWritableDatabase();
//        db.delete("records", "name=?", new_pic String[]{name});
        db.execSQL("delete from records where id=" + position);
        db.close();
        queryData("");
    }


    @Override
    public void click(String name) {
        deleteOneData(name);
    }

    @Override
    public void onItemClick(View view, int viewType, String data, int position) {
        searchEdt.setText(data);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.requestSearchList(false, words == null || words.equals("") ? searchEdt.getText().toString() : words);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestSearchList(true, words == null || words.equals("") ? searchEdt.getText().toString() : words);
    }
}
