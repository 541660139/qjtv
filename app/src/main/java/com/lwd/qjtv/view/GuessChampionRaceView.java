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

public class GuessChampionRaceView extends LinearLayout {

    @BindView(R.id.item_race_view_peilv_tv)
    TextView peilvTv;
    @BindView(R.id.item_race_view_name_tv)
    TextView nameTv;
    @BindView(R.id.item_race_view_ll)
    LinearLayout linearLayout;
    private String id;
    private Context context;
    private String title, selector, peilv, match_id, player_id;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            GuessChampionBetDialog betDialog = new GuessChampionBetDialog(context);
            betDialog.setData(title, selector, peilv);
            betDialog.setApiParam(match_id, player_id);
            betDialog.show();
        }
    };

    public GuessChampionRaceView(Context context) {
        this(context, null);
    }

    public GuessChampionRaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuessChampionRaceView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    public void setIsClick(boolean isClick) {
        if (isClick)
            linearLayout.setBackground(getResources().getDrawable(R.drawable.circle_corner_unclick));
    }

    public void setApiParam(String title, String selector, String peilv, String match_id, String player_id) {
        this.title = title;
        this.selector = selector;
        this.peilv = peilv;
        this.match_id = match_id;
        this.player_id = player_id;
    }

    public void setData(String string, String name, String id) {
        peilvTv.setText("赔率：" + string);
        nameTv.setText(name);
        this.id = id;
    }

    public void setEnable() {
        peilvTv.setTextColor(context.getResources().getColor(R.color.color666666));
        setClickable(false);
    }

    public void setClick(){
        peilvTv.setTextColor(context.getResources().getColor(R.color.color5690f2));
        setClickable(true);
    }
}
