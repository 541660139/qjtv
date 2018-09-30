package com.lwd.qjtv.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.CommonUtil;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.mvp.model.entity.GiftBean;
import com.lwd.qjtv.mvp.ui.activity.RechargeActivity;
import com.lwd.qjtv.mvp.ui.adapter.GiftPagerAdapter;
import com.lwd.qjtv.mvp.ui.adapter.GiftViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/19.
 */

public class GiftView extends LinearLayout implements GiftViewAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.send)
    Button sendBtn;
    @BindView(R.id.popup_gifts_name_tv)
    EditText nameEdt;
    @BindView(R.id.popup_gifts_num)
    EditText numTv;
    @BindView(R.id.popup_gifts_close_iv)
    ImageView closeIv;
    @BindView(R.id.recharge_tv)
    TextView rechargeTv;
    @BindView(R.id.popup_gifts_coin_tv)
    TextView coinTv;
    @BindView(R.id.popup_gifts_cb)
    CheckBox checkBox;
    @BindView(R.id.popup_isopen_ll)
    LinearLayout isOpenLl;
    private static final int ROW = 2;
    private static final int COLUMN = 4;

    private GiftView.Indicator indicator;
    private Context context;

    private GiftViewAdapter.OnRecyclerViewItemClickListener listener;
    private GiftPagerAdapter giftPagerAdapter;
    private ArrayList<View> viewContainer = new ArrayList<>();

    public GiftView(Context context) {
        this(context, null);
    }

    public GiftView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GiftView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.popup_gifts, this);
        indicator = new GiftView.Indicator((ViewGroup) findViewById(R.id.indicator));
        ButterKnife.bind(this, view);
        coinTv.setText(Preference.get(Constant.COIN, "0"));
        int pageSize = getPageSize();
        List<GiftBean> resList = initGiftList();
        for (int i = 0; i < pageSize; i++) {
            View giftView = LayoutInflater.from(getContext()).inflate(R.layout.gift_recyclerview, null);
            RecyclerView recyclerView = (RecyclerView) giftView.findViewById(R.id.gift_recyclerview);
            List<GiftBean> integers = resList.subList(8 * i, 8 * (i + 1) > getTotalSize() ? resList.size() : 8 * (i + 1));
            GiftViewAdapter adapter = new GiftViewAdapter(integers);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
//            adapter.addList(integers);
            viewContainer.add(giftView);
        }
        giftPagerAdapter = new GiftPagerAdapter(viewContainer);
        viewPager.setAdapter(giftPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicator.setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rechargeTv.setOnClickListener(view1 -> context.startActivity(new Intent(context, RechargeActivity.class)));
        isOpenLl.setOnClickListener(v -> checkBox.setChecked(!checkBox.isChecked()));
    }

    @NonNull
    private List<GiftBean> initGiftList() {
        List<Integer> resList = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        List<String> coinList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        resList.add(R.mipmap.gift_one);
        idList.add("1");
        coinList.add("5");
        nameList.add("领结");
        resList.add(R.mipmap.gift_two);
        idList.add("2");
        coinList.add("10");
        nameList.add("皮头");
        resList.add(R.mipmap.gift_three);
        idList.add("3");
        coinList.add("20");
        nameList.add("爆米花");
        resList.add(R.mipmap.gift_four);
        idList.add("4");
        coinList.add("50");
        nameList.add("巧粉");
        resList.add(R.mipmap.gift_five);
        idList.add("5");
        coinList.add("100");
        nameList.add("小丑献花");
        resList.add(R.mipmap.gift_six);
        idList.add("6");
        coinList.add("100");
        nameList.add("巫师醉酒");
        resList.add(R.mipmap.gift_seven);
        idList.add("7");
        coinList.add("500");
        nameList.add("手表");
        resList.add(R.mipmap.gift_eight);
        idList.add("8");
        coinList.add("100");
        nameList.add("马叔开炮");
        resList.add(R.mipmap.gift_nine);
        idList.add("9");
        coinList.add("100");
        nameList.add("小将雄起");
        resList.add(R.mipmap.gift_ten);
        idList.add("10");
        coinList.add("5000");
        nameList.add("游艇");
        resList.add(R.mipmap.gift_eleven);
        idList.add("11");
        coinList.add("10000");
        nameList.add("飞机");
        resList.add(R.mipmap.gift_twelve);
        idList.add("12");
        coinList.add("100");
        nameList.add("总统驾到");
        resList.add(R.mipmap.gift_thirteen);
        idList.add("13");
        coinList.add("200");
        nameList.add("火箭升天");
        resList.add(R.mipmap.gift_forteen);
        idList.add("14");
        coinList.add("200");
        nameList.add("定海神丁");
        List<GiftBean> giftBeanList = new ArrayList<>();
        for (int i = 0; i < resList.size(); i++) {
            GiftBean giftBean = new GiftBean();
            giftBean.setId(idList.get(i));
            giftBean.setCoin(coinList.get(i));
            giftBean.setIdRes(resList.get(i));
            giftBean.setGiftName(nameList.get(i));
            giftBeanList.add(giftBean);
        }
        return giftBeanList;
    }

    public void setCoin() {
        coinTv.setText(Preference.get(Constant.COIN, "0"));
    }

    private int getPageSize() {
        return getTotalSize() / (ROW * COLUMN) + 1;
    }

    private int getTotalSize() {
        return 14;
    }

    public void setOnSendClickListener(OnClickListener onSendClickListener) {
        sendBtn.setOnClickListener(onSendClickListener);
    }

    public void setItemClickListener(GiftViewAdapter.OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public void setNumTv(String num) {
        numTv.setText(num == null || num.equals("") ? "0" : num);
    }

    public void setNameEdt(String name) {
        nameEdt.setText(name == null || name.equals("") ? "0" : name);
    }

    public String getNum() {
        return numTv.getText().toString().trim();
    }

    public String getName() {
        return nameEdt.getText().toString().trim();
    }

    public void setCheckBoxListener(CompoundButton.OnCheckedChangeListener checkBoxListener) {
        if (checkBoxListener != null)
            checkBox.setOnCheckedChangeListener(checkBoxListener);
    }


    public String getIsOpen() {
        boolean checked = checkBox.isChecked();
        return checked ? "0" : "1";
    }

    public void setCloseListener(OnClickListener onClickListener) {
        if (onClickListener != null)
            closeIv.setOnClickListener(onClickListener);
    }

    @Override
    public void onItemClick(View view, Object data, int position) {
        if (listener != null)
            listener.onItemClick(view, data, position);
    }

    private class Indicator {
        private ViewGroup rootView;
        private ArrayList<ImageView> imageList = new ArrayList<>();

        public Indicator(ViewGroup root) {
            rootView = root;
            int pageSize = getPageSize();
            for (int i = 0; i < pageSize; i++) {
                ImageView imageView = new ImageView(getContext());
                LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int px = CommonUtil.dip2px(4);
                params.setMargins(px, 0, px, 0);
                imageView.setLayoutParams(params);
                if (i == 0) {
                    imageView.setImageResource(R.mipmap.input_emoji_indicator_hover);
                } else {
                    imageView.setImageResource(R.mipmap.input_emoji_indicator);
                }
                imageList.add(imageView);
                rootView.addView(imageView);
            }
        }

        public void setSelected(int position) {
            for (int i = 0; i < imageList.size(); i++) {
                if (i != position) {
                    imageList.get(i).setImageResource(R.mipmap.input_emoji_indicator);
                } else {
                    imageList.get(i).setImageResource(R.mipmap.input_emoji_indicator_hover);
                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = ((Activity) context).getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                if (hideInputMethod(context, v)) {
                    nameEdt.clearFocus();
                    //// TODO: 2017/7/3  进行网络请求，查询用户名是否有效
                    return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                }
                nameEdt.requestFocus();
            } else {
//                nameEdt.setFocusable(true);
                nameEdt.requestFocus();
            }

        }
        return super.dispatchTouchEvent(ev);
    }

    public static Boolean hideInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }

    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}


