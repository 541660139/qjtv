package com.lwd.qjtv.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.ui.holder.ReChargeItemViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/4/1.
 */

public class RechargeGridAdapter extends RecyclerView.Adapter<ReChargeItemViewHolder> {
    private int i = 0;
    private Context context;
    private OnItemChooseCallBack callBack;
    private List<String> list = new ArrayList<>();
    private EditText editText;

    public RechargeGridAdapter(Context context, OnItemChooseCallBack callBack) {
        this.context = context;
        this.callBack = callBack;
    }

    @Override
    public ReChargeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReChargeItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recharge_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final ReChargeItemViewHolder holder, final int position) {
        final String s = list.get(position);
        holder.moneyTv.setText("￥"+s );
        if (position != (list.size() - 1)) {
            holder.moneyTv.setVisibility(View.VISIBLE);
            holder.moneyEdt.setVisibility(View.GONE);
        } else {
            holder.moneyTv.setVisibility(View.GONE);
            holder.moneyEdt.setVisibility(View.VISIBLE);
            holder.moneyEdt.setText(s);
            editText = holder.moneyEdt;
        }
        if (position == i) {
            holder.moneyTv.setBackgroundResource(R.mipmap.recharge_item_selector);
            holder.moneyEdt.setBackgroundResource(R.mipmap.recharge_item_selector);
//            holder.moneyTv.setBackground(context.getResources().getDrawable(R.mipmap.recharge_item_selector));
//            holder.moneyEdt.setBackground(context.getResources().getDrawable(R.mipmap.recharge_item_selector));
//            callBack.chooseItem(position,s);
        } else {

            holder.moneyTv.setBackgroundResource(R.drawable.recharge_item_bg);
            holder.moneyEdt.setBackgroundResource(R.drawable.recharge_item_bg);
//            holder.moneyEdt.setBackground(context.getResources().getDrawable(R.drawable.recharge_item_bg));
//            holder.moneyTv.setBackground(context.getResources().getDrawable(R.drawable.recharge_item_bg));
        }
//        holder.itemView.setOnClickListener(
//                view -> {
//            callBack.chooseItem(position, s);
//            if (i != position) {
//                i = position;
//                notifyDataSetChanged();
//            }
//            if (editText != null) {
//                editText.clearFocus();
//            }
//        });
        holder.itemView.setOnClickListener(view -> {
            callBack.chooseItem(position, s);
            if (i != position) {
                i = position;
                notifyDataSetChanged();
            }
            if (editText != null) {
                editText.clearFocus();
            }
        });
//        holder.moneyEdt.setOnClickListener(view -> {
//            callBack.chooseItem(position, s);
//            if (i != position) {
//                i = position;
//                notifyDataSetChanged();
//            }
//        });
        holder.moneyEdt.setOnClickListener(view -> {
            callBack.chooseItem(position, s);
            if (i != position) {
                i = position;
                notifyDataSetChanged();
            }
        });
        holder.moneyEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(charSequence.toString() == null || charSequence.toString().equals("")){
//                    return;
//                }
                if(charSequence.toString() == null || charSequence.toString().equals(""))
                    return;
                if(Integer.parseInt(charSequence.toString())< 50){
                    UiUtils.makeText(context,"其他充值金额输入不得少于50元！");
                    return;
                }
//                if (Integer.parseInt(charSequence.toString()) <= 6) {
//                    callBack.chooseItem(position, 6 + "");
//                } else
                    callBack.chooseItem(position, charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.moneyEdt.setOnFocusChangeListener((view, b) -> {
                    callBack.chooseItem(position, s);
                    if (i != position) {
                        i = position;
                        notifyDataSetChanged();
                    }
                }
        );
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public List<String> getList() {
        return list;
    }

    public interface OnItemChooseCallBack {
        void chooseItem(int position, String awardStr);
    }
}
