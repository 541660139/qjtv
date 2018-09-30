package com.lwd.qjtv.mvp.ui.adapter;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GuessDetailsBean;
import com.lwd.qjtv.mvp.ui.holder.GuessDetailsNumItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/26.
 */

public class GuessDetailsNumAdapter extends DefaultAdapter<GuessDetailsBean.DataBean.GuessGameNumberBean.PlInfoBeanX> {

    private boolean isVisible;
    private GuessDetailsNumItemHolder holder;
    private CallBackChooseItem callback;
    private String id;
    private GuessDetailsBean.DataBean dataBean;
    private boolean isHistory;
    private boolean isChoose;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            GuessDetailsNumItemHolder mHolder = (GuessDetailsNumItemHolder) msg.obj;
            if (holder == null)
                holder = mHolder;
            else if (holder != mHolder) {
                holder.checkBox.setChecked(false);
                isHistory = true;
            }
//                callback.chooseItem(mInfos.get(holder.getPosition()), false);
            holder = mHolder;
//            holder.checkBox.setChecked(true);
        }
    };
    private String id1 = null;

    public GuessDetailsNumAdapter(List<GuessDetailsBean.DataBean.GuessGameNumberBean.PlInfoBeanX> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<GuessDetailsBean.DataBean.GuessGameNumberBean.PlInfoBeanX> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        GuessDetailsNumItemHolder mHolder = (GuessDetailsNumItemHolder) getHolder(view, viewType);
        mHolder.checkBox.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        mHolder.setOnItemClickListener(new BaseHolder.OnViewClickListener() {//设置Item点击事件
            @Override
            public void onViewClick(View view, int position) {
                if (mOnItemClickListener != null && mInfos.size() > 0) {
                    mOnItemClickListener.onItemClick(view, viewType, mInfos.get(position), position);
                }
                mHolder.checkBox.setChecked(!mHolder.checkBox.isChecked());
            }
        });
        return mHolder;
    }

    @Override
    public void onBindViewHolder(BaseHolder<GuessDetailsBean.DataBean.GuessGameNumberBean.PlInfoBeanX> holder, int position) {
        super.onBindViewHolder(holder, position);
        if (mInfos != null && mInfos.size() != 0 && mInfos.get(position) != null)
            holder.setData(mInfos.get(position), position);
        GuessDetailsNumItemHolder mHolder = (GuessDetailsNumItemHolder) holder;
        mHolder.setApiParams(dataBean);
        mHolder.raceView.setClickable(!isVisible);
        if (id != null && id.equals(mInfos.get(position).getId() + "")) {
            mHolder.checkBox.setChecked(true);
            id = null;
            this.holder = mHolder;
            isHistory = true;
        } else {
            isHistory = false;
//            mHolder.checkBox.setChecked(false);
        }
        mHolder.checkBox.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        mHolder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                id1 = mInfos.get(position).getId() + "";
                for (int i = 0; i < mInfos.size(); i++) {
                    callback.chooseItem(mInfos.get(i), false);
                }
                Message message = new Message();
                message.obj = mHolder;
                handler.sendMessage(message);
                callback.chooseItem(mInfos.get(position), true);
            } else {
//                if(isHistory && ){
//
//                }
//                if (this.holder == mHolder && !isHistory) {
//                    callback.chooseItem(mInfos.get(position), false);
//                    isHistory = false;
//                }

                if (id1 != null && id1.equals(mInfos.get(position).getId() + "")) {
                    callback.chooseItem(mInfos.get(position), false);
                }
            }
        });
    }


    @Override
    public BaseHolder<GuessDetailsBean.DataBean.GuessGameNumberBean.PlInfoBeanX> getHolder(View v, int viewType) {
        return new GuessDetailsNumItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_guess_details_num_layout;
    }

    public void setApiParams(GuessDetailsBean.DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public void setChoose(String id) {
        this.id = id;
        notifyDataSetChanged();
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
        notifyDataSetChanged();
    }

    public void setCallback(CallBackChooseItem callback) {
        this.callback = callback;
    }

    public interface CallBackChooseItem {
        void chooseItem(GuessDetailsBean.DataBean.GuessGameNumberBean.PlInfoBeanX plInfoBeanX, boolean b);
    }
}