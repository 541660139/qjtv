package com.lwd.qjtv.app.utils.photoutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import com.lwd.qjtv.app.utils.photoutils.alertview.AlertView;
import com.lwd.qjtv.app.utils.photoutils.alertview.OnItemClickListener;


/**
 * Created by ZhengQian on 2018/1/18.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class TakePicDialog {
    private Context context;
    private int resultCode;
    private PhotoCallback photoCallback;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            photoCallback.photo();
        }
    };

    public TakePicDialog(Context context) {
        this.context = context;
    }

    public void show(final Activity activity) {
        new AlertView(null, null, "取消", null, new String[]{"从手机相册选择", "拍照"}, context, AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                // TODO Auto-generated method stub
                if (position == 0) {
                    // 从相册中选择
                    Intent i = new Intent(
                            // 相册
                            Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activity.startActivityForResult(i, resultCode);
                } else if (position == 1) {
                    // 拍照
                    handler.sendEmptyMessage(1);
                }
            }
        }).show();
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setPhotoCallback(PhotoCallback photoCallback) {
        this.photoCallback = photoCallback;
    }

    public interface PhotoCallback {
        void photo();
    }
}
