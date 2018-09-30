package com.lwd.qjtv.mvp.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.ui.adapter.PhotoPickerAdapter;

/**
 * Created by Administrator on 2017/12/27.
 */

public class PhotoPickViewHolder extends RecyclerView.ViewHolder {
    public ImageView iv;
    public ImageView deleteIv;
    public TextView addPhotoTv;
    private PhotoPickerAdapter.OnDeleteCallback onDeleteCallback;
    private OnItemClickCallBack itemClickCallback;

    public PhotoPickViewHolder(View itemView) {
        super(itemView);
        iv = (ImageView) itemView.findViewById(R.id.item_suggset_image);
        deleteIv = (ImageView) itemView.findViewById(R.id.iv_delete);
        addPhotoTv = (TextView) itemView.findViewById(R.id.item_suggestion_add_photo_tv);
        deleteIv.setOnClickListener(v -> {
            if (onDeleteCallback != null)
                onDeleteCallback.onDeleteItem(getPosition());
        });
        itemView.setOnClickListener(v -> {
            if (itemClickCallback != null)
                itemClickCallback.onItemClickCallback(getPosition(), itemView);
        });
    }


    public void setDeleteItemClick(PhotoPickerAdapter.OnDeleteCallback onDeleteCallback) {
        this.onDeleteCallback = onDeleteCallback;
    }

    public void setOnItemClickCallback(OnItemClickCallBack itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public interface OnItemClickCallBack {
        void onItemClickCallback(int position, View itemView);
    }

}
