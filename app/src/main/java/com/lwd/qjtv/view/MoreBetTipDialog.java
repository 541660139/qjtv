package com.lwd.qjtv.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/29.
 */

public class MoreBetTipDialog extends View {

    private AlertDialog alertDialog;
    private Context context;
    private CheckBox cbChoose;
    private TextView sureTv;

    public MoreBetTipDialog(Context context) {
        super(context);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_download_hint_view, null);
        init(view);
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setView(view);
    }

    public void show() {
        if (alertDialog != null)
            alertDialog.show();
    }

    private void init(View view) {
        cbChoose = (CheckBox) view.findViewById(R.id.cb_not_ask_next_time);
        sureTv = (TextView) view.findViewById(R.id.dialog_download_hint_sure_tv);
        initListener();
    }

    private void initListener() {
        cbChoose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 不再提醒
                    Preference.put(Constant.IS_MORE_BET_TIP, false);
                } else {
                    // 每次提醒
                    Preference.put(Constant.IS_MORE_BET_TIP, true);
                }
            }
        });
        sureTv.setOnClickListener(v -> {
            if (alertDialog != null) alertDialog
                    .dismiss();
        });
    }

}
