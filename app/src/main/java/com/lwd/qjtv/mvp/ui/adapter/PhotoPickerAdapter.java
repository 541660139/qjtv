package com.lwd.qjtv.mvp.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.ui.holder.PhotoPickViewHolder;

import java.util.ArrayList;
import java.util.List;


public class PhotoPickerAdapter extends RecyclerView.Adapter<PhotoPickViewHolder> {

    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private OnDeleteCallback onDeleteCallback;
    private Context mContext;
    private List<Bitmap> bitmap = new ArrayList<>();
    private int MAX_NUM = 4;

    public PhotoPickerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public PhotoPickViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        PhotoPickViewHolder suggestionViewHolder = new
                PhotoPickViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_photo_pick_layout, parent, false));
        suggestionViewHolder.setOnItemClickCallback((position, itemView) -> {
            if (mOnItemClickListener != null && bitmap.size() == 0 || bitmap.size() == position) {
                mOnItemClickListener.onItemClick(itemView, viewType, null, position);
            }
        });
        if (onDeleteCallback != null)
            suggestionViewHolder.setDeleteItemClick(onDeleteCallback);
        return suggestionViewHolder;
    }

    @Override
    public void onBindViewHolder(PhotoPickViewHolder holder, int position) {
        if ((bitmap.size() == 0 || bitmap.size() == position)) {
            if (bitmap.size() < MAX_NUM) {
                holder.iv.setImageResource(R.mipmap.ima_more_picture);
                holder.deleteIv.setVisibility(View.GONE);
                holder.addPhotoTv.setVisibility(View.VISIBLE);
            }
        } else {
            holder.iv.setImageBitmap(bitmap.get(position));
            holder.deleteIv.setVisibility(View.VISIBLE);
            holder.addPhotoTv.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return bitmap == null ? 1 : bitmap.size() + 1 <= MAX_NUM ? bitmap.size() + 1 : MAX_NUM;
    }

    public void setImageBitmap(List<Bitmap> bitmap) {
        this.bitmap = bitmap;
        notifyDataSetChanged();
    }

    public void setMAX_NUM(int MAXNUM){
        this.MAX_NUM = MAXNUM;
    }


    public interface OnRecyclerViewItemClickListener<T> {
        void onItemClick(View view, int viewType, T data, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnDeleteCallback {
        void onDeleteItem(int position);
    }

    public void setDeleteCallback(OnDeleteCallback onDeleteCallback) {
        this.onDeleteCallback = onDeleteCallback;
    }
}
