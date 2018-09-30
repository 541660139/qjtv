package com.lwd.qjtv.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.SaveUserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/19.
 */

public class RaceView extends LinearLayout {

    @BindView(R.id.item_race_view_peilv_tv)
    TextView peilvTv;
    @BindView(R.id.item_race_view_name_tv)
    TextView nameTv;
    @BindView(R.id.item_race_view_ll)
    LinearLayout linearLayout;
    private String id;
    private Context context;
    private String title, selector, peilv, match_id, jc_id, jc_pl_id, jc_type;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BetDialog betDialog = new BetDialog(context);
            betDialog.setData(title, selector, peilv);
            betDialog.setApiParam(match_id, jc_id, jc_pl_id, jc_type);
            betDialog.show();
        }
    };

    public RaceView(Context context) {
        this(context, null);
    }

    public RaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = View.inflate(context, R.layout.item_race_view_layout, this);
        ButterKnife.bind(this, view);
        this.setOnClickListener(view1 -> {
            if (SaveUserInfo.getCoin().equals("0")) {
                //显示当前余额不足dialog
                Toast.makeText(context, "当前余额不足哦~可以前往充值", Toast.LENGTH_SHORT).show();
            } else
                handler.sendEmptyMessage(1);
        });
    }

    public void setOnItemClick(){
        if (SaveUserInfo.getCoin().equals("0")) {
            //显示当前余额不足dialog
            Toast.makeText(context, "当前余额不足哦~可以前往充值", Toast.LENGTH_SHORT).show();
        } else
            handler.sendEmptyMessage(1);
    }

    public void setIsClick(boolean isClick) {
        if (isClick)
            linearLayout.setBackground(getResources().getDrawable(R.drawable.circle_corner_unclick));
    }

    public void setApiParam(String title, String selector, String peilv, String match_id, String jc_id, String jc_pl_id, String jc_type) {
        this.title = title;
        this.selector = selector;
        this.peilv = peilv;
        this.match_id = match_id;
        this.jc_id = jc_id;
        this.jc_pl_id = jc_pl_id;
        this.jc_type = jc_type;
    }

    public void setData(String string, String name, String id) {
        peilvTv.setText("赔率：" + string);
        nameTv.setText(name);
        this.id = id;
    }


}
