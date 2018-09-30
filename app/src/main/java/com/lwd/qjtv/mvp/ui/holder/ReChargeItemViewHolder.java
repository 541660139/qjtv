package com.lwd.qjtv.mvp.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lwd.qjtv.R;

/**
 * Created by Administrator on 2017/4/1.
 */

public class ReChargeItemViewHolder extends RecyclerView.ViewHolder {

    public TextView moneyTv;
    public EditText moneyEdt;

    public ReChargeItemViewHolder(View itemView) {
        super(itemView);
        moneyTv = (TextView) itemView.findViewById(R.id.money_tv);
        moneyEdt = (EditText) itemView.findViewById(R.id.money_edt);
    }
}
