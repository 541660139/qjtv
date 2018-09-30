package com.lwd.qjtv.mvp.ui.holder;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.AddressBean;
import com.lwd.qjtv.mvp.ui.activity.AddAddressActivity;
import com.lwd.qjtv.mvp.ui.adapter.AddressManagerAdapter;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/6.
 */

public class AddressManagerItemHolder extends BaseHolder<AddressBean.DataBean> {

    @BindView(R.id.item_address_manager_name_tv)
    TextView nameTv;
    @BindView(R.id.item_address_manager_phone_tv)
    TextView phoneTv;
    @BindView(R.id.item_address_manager_address_tv)
    TextView addressTv;
    @BindView(R.id.item_address_manager_edit_iv)
    ImageView editIv;
    @BindView(R.id.item_address_manager_default_tv)
    TextView defaultTv;
    @BindView(R.id.ll)
    LinearLayout linearLayout;
    private boolean isWeb;
    private AddressManagerAdapter.ClickCallback callback;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public AddressManagerItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = UiUtils.getScreenWidth(itemView.getContext());
        linearLayout.setLayoutParams(layoutParams);

    }

    @Override
    public void setData(AddressBean.DataBean data, int position) {
        nameTv.setText(data.getReceiver());
        addressTv.setText(data.getZone() + data.getAddress());
        phoneTv.setText(data.getMobile());
        defaultTv.setVisibility("0".equals(data.getIs_default()) ? View.GONE : View.VISIBLE);
        editIv.setOnClickListener(view -> {
            if (!isWeb) {
                Intent intent = new Intent(itemView.getContext(), AddAddressActivity.class);
                intent.putExtra("id", data.getId());
                intent.putExtra("name", data.getReceiver());
                intent.putExtra("phone", data.getMobile());
                intent.putExtra("address", data.getZone());
                intent.putExtra("details_addresss", data.getAddress());
                intent.putExtra("is_default", data.getIs_default());
                itemView.getContext().startActivity(intent);
            }
        });
        itemView.setOnClickListener(v -> {
            callback.click(data,position);
        });
    }

    public void setCallback(AddressManagerAdapter.ClickCallback callback) {
        this.callback = callback;
    }

    public void setIsWeb(boolean isWeb) {
        this.isWeb = isWeb;
    }


    @Override
    protected void onRelease() {
    }


}