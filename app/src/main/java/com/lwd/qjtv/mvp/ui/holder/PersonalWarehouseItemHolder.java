package com.lwd.qjtv.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.GiftUtils;
import com.lwd.qjtv.mvp.model.entity.PersonalWareHouseBean;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/6.
 */

public class PersonalWarehouseItemHolder extends BaseHolder<PersonalWareHouseBean.DataBean> {

    private final Context context;
    @BindView(R.id.item_personal_warehouse_gift_iv)
    ImageView giftIv;
    @BindView(R.id.item_personal_warehouse_cb)
    public CheckBox checkBox;
    @BindView(R.id.item_personal_warehouse_gift_name_tv)
    TextView nameTv;
    @BindView(R.id.item_personal_warehouse_reduce_tv)
    TextView reduceTv;
    @BindView(R.id.item_personal_warehouse_number_tv)
    TextView numTv;
    @BindView(R.id.item_personal_warehouse_add_tv)
    TextView addTv;
    private ChooseGiftCallback callback;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private int count;
    private boolean selectAll;
    private PersonalWareHouseBean.DataBean data;

    public PersonalWarehouseItemHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(PersonalWareHouseBean.DataBean data, int position) {
        this.data = data;
        giftIv.setBackground(itemView.getContext().getResources().getDrawable(GiftUtils.getGiftIdres(data.getGift_id())));
        numTv.setText(data.getCount() + "");
        nameTv.setText(GiftUtils.getGiftName(data.getGift_id()));
        initListener(data);
    }

    private void initListener(PersonalWareHouseBean.DataBean data) {
        itemView.setOnClickListener(v -> checkBox.setChecked(!checkBox.isChecked()));
        checkBox.setOnCheckedChangeListener((compoundButton, b) ->
                callback.chooseGift(Integer.parseInt(data.getGift_id()), Integer.parseInt(numTv.getText().toString()), b)
        );
        reduceTv.setOnClickListener(view -> {
            count = Integer.parseInt(data.getCount());
//            addTv.setClickable(true);
            int num = Integer.parseInt(numTv.getText().toString());
            if (num != 1) {
                num--;
                numTv.setText(num + "");
            } else {
//                reduceTv.setClickable(false);
                UiUtils.makeText(context, "已经减到最低了");
            }
            if (checkBox.isChecked())
                callback.chooseGift(Integer.parseInt(data.getGift_id()), Integer.parseInt(numTv.getText().toString()), checkBox.isChecked());
        });
        addTv.setOnClickListener(view -> {
            count = Integer.parseInt(data.getCount());
//            reduceTv.setClickable(true);
            int num = Integer.parseInt(numTv.getText().toString());
            if (num < count) {
                num++;
                numTv.setText(num + "");
            } else {
//                addTv.setClickable(false);
                UiUtils.makeText(context, "已经加到最高了");
            }
            if (checkBox.isChecked())
                callback.chooseGift(Integer.parseInt(data.getGift_id()), Integer.parseInt(numTv.getText().toString()), checkBox.isChecked());
        });
    }

    public void setChooseGiftCallBack(ChooseGiftCallback callback) {
        this.callback = callback;
    }


    public void setSelectAll(boolean allSelect) {
//        this.selectAll = allSelect;
//        callback.chooseGift(Integer.parseInt(data.getGift_id()), Integer.parseInt(numTv.getText().toString()), selectAll);
    }

    @Override
    protected void onRelease() {

    }

    public interface ChooseGiftCallback {
        void chooseGift(int id, int num, boolean isChoose);
    }
}