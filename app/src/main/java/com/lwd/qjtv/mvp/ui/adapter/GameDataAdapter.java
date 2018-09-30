package com.lwd.qjtv.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GameDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/9/7.
 */

public class GameDataAdapter extends RecyclerView.Adapter<GameDataAdapter.GameDataHolder> {


    private Context mContext;

    private List<GameDataBean.DataBean> resList = new ArrayList<>();


    protected GameDataAdapter.OnItemClickListener mOnItemClickListener = null;

    public GameDataAdapter(List<GameDataBean.DataBean> resList, Context context) {
        this.resList = resList;
        mContext = context;
    }


    @Override
    public GameDataHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new GameDataHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_game_data_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(GameDataHolder gameDataHolder, int i) {
//        设置时间
        if (resList.get(i).getTime() != null) ;
        gameDataHolder.tv.setText(resList.get(i).getTime());

        gameDataHolder.receiver_game_data.setLayoutManager(new LinearLayoutManager(mContext));

        gameDataHolder.receiver_game_data.setAdapter(new GameChilderDataAdapter(resList.get(i).getPhase_list()));
    }


    @Override
    public int getItemCount() {
        return resList == null ? 0 : resList.size();
    }


    class GameDataHolder extends RecyclerView.ViewHolder {


        TextView tv;
        RecyclerView receiver_game_data;

        public GameDataHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.tv_time);
            receiver_game_data = view.findViewById(R.id.receiver_game_data);
        }
    }


    class GameChilderDataAdapter extends RecyclerView.Adapter<GameChilderDataAdapter.GameChilderDataHolder> {

        private List<GameDataBean.DataBean.PhaseListBean> mList = new ArrayList<>();

        public GameChilderDataAdapter(List<GameDataBean.DataBean.PhaseListBean> resList) {
            this.mList = resList;

        }

        @Override
        public GameChilderDataAdapter.GameChilderDataHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new GameChilderDataHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_game_childer_data_layout, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(GameChilderDataAdapter.GameChilderDataHolder gameChilderDataHolder, int i) {
            gameChilderDataHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onChildItemClickListener(mList.get(i));
                }
            });
            gameChilderDataHolder.tv_game_title.setText(mList.get(i).getComplete_name());
            gameChilderDataHolder.tv_game_star_time.setText(mList.get(i).getHour_second());
            gameChilderDataHolder.tv_game_type.setText(mList.get(i).getIs_end().equals("1") ? "直播中" : "竞猜中");
            gameChilderDataHolder.tv_score_1.setText(mList.get(i).getScore_a());
            gameChilderDataHolder.tv_name_1.setText(mList.get(i).getPlay_a());
            gameChilderDataHolder.tv_score_2.setText(mList.get(i).getScore_b());
            gameChilderDataHolder.tv_name_2.setText(mList.get(i).getPlay_b());
            if (mList.get(i).getPic_a() != null && !TextUtils.isEmpty(mList.get(i).getPic_a()))
                Glide.with(mContext).load(mList.get(i).getPic_a()).into(gameChilderDataHolder.circleimageview1);
            if (mList.get(i).getPic_b() != null && !TextUtils.isEmpty(mList.get(i).getPic_b()))
                Glide.with(mContext).load(mList.get(i).getPic_b()).into(gameChilderDataHolder.circleimageview2);
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }


        class GameChilderDataHolder extends RecyclerView.ViewHolder {


            TextView tv_game_title;

            TextView tv_score_1;

            TextView tv_name_1;

            ImageView circleimageview1;
            TextView tv_score_2;

            TextView tv_name_2;

            ImageView circleimageview2;

            TextView tv_game_star_time;
            TextView tv_game_type;


            public GameChilderDataHolder(View view) {
                super(view);
                tv_game_title = view.findViewById(R.id.tv_game_title);
                circleimageview1 = view.findViewById(R.id.circleimageview1);
                tv_score_1 = view.findViewById(R.id.tv_score_1);
                tv_name_1 = view.findViewById(R.id.tv_name_1);
                circleimageview2 = view.findViewById(R.id.circleimageview2);
                tv_score_2 = view.findViewById(R.id.tv_score_2);
                tv_name_2 = view.findViewById(R.id.tv_name_2);
                tv_game_star_time = view.findViewById(R.id.tv_game_star_time);
                tv_game_type = view.findViewById(R.id.tv_game_type);


            }
        }
    }


    public void setOnItemClickListener(GameDataAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {

        void onChildItemClickListener(GameDataBean.DataBean.PhaseListBean  haseListBeapn);

    }

}
