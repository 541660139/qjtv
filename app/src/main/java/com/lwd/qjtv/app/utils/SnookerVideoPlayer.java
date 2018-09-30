package com.lwd.qjtv.app.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.video.GSYBaseVideoPlayer;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/26.
 */

public class SnookerVideoPlayer extends GSYBaseVideoPlayer {

    public SnookerVideoPlayer(Context context) {
        this(context, null);
    }

    public SnookerVideoPlayer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SnookerVideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public boolean setUp(String s, boolean b, File file, Object... objects) {
        return false;
    }

    @Override
    public boolean setUp(List<GSYVideoModel> gsyVideoModels, int tDuration, boolean cacheWithPlay, int position, File cachePath, Map<String, String> mapHeadData, Object... objects) {
        return false;
    }

    @Override
    public boolean setUp(List<GSYVideoModel> gsyVideoModels, boolean cacheWithPlay, int position, File cachePath, Map<String, String> mapHeadData, Object... objects) {
        return false;
    }



    @Override
    protected void setStateAndUi(int i) {

    }

    @Override
    protected void addTextureView() {

    }

    @Override
    protected void setSmallVideoTextureView(OnTouchListener onTouchListener) {

    }

    @Override
    protected void onClickUiToggle() {

    }

    @Override
    public ImageView getFullscreenButton() {
        return null;
    }

    @Override
    public ImageView getBackButton() {
        return null;
    }

    @Override
    public void onPrepared() {

    }

    @Override
    public void onAutoCompletion() {

    }

    @Override
    public void onCompletion() {

    }

    @Override
    public void onBufferingUpdate(int i) {

    }

    @Override
    public void onSeekComplete() {

    }

    @Override
    public void onError(int i, int i1) {

    }

    @Override
    public void onInfo(int i, int i1) {

    }

    @Override
    public void onVideoSizeChanged() {

    }

    @Override
    public void onBackFullscreen() {

    }

    @Override
    public void onVideoPause() {

    }

    @Override
    public void onVideoResume() {

    }
}
