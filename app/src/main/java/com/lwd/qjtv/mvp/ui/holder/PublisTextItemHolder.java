package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.PublisDataBean;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class PublisTextItemHolder extends BaseHolder<PublisDataBean.DataBean> {
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    @BindView(R.id.checkbox)
    public CheckBox checkbox;
    @BindView(R.id.tv_data_day)
    TextView tv_data_day;
    @BindView(R.id.tv_data_month)
    TextView tv_data_month;

    @BindView(R.id.tv_content)
    TextView tv_content;

    private OnRecyclerViewTvItemClickListener onRecyclerViewTvItemClickListener;

    public PublisTextItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(PublisDataBean.DataBean data, int position) {
        String create_time = data.getCreate_time();
        String[] time = create_time.split("-");
        String year = time[0];
        String month = time[1];
        String day = time[2];

        if (data.isVisible()) {
            checkbox.setVisibility(View.VISIBLE);
        } else {
            checkbox.setVisibility(View.GONE);
        }

        if (data.isSeleter()) {
            checkbox.setChecked(true);
        } else {
            checkbox.setChecked(false);
        }
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

//                onRecyclerViewTvItemClickListener.onItemTVClick();
                // TODO Auto-generated method stub
                if (isChecked) {
                    data.setSeleter(true);
                } else {
                    data.setSeleter(false);
                }
            }
        });

        tv_data_day.setText(day);
        tv_data_month.setText(month + "月");
        tv_content.setText(data.getTitle());

    }

    public interface OnRecyclerViewTvItemClickListener {
        void onItemTVClick();
    }

    public void setOnItemTvClickListener(OnRecyclerViewTvItemClickListener listener) {
        this.onRecyclerViewTvItemClickListener = listener;
    }

}