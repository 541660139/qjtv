//package com.shuyu.gsyvideoplayer.video;
//
//import android.content.Context;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//
//import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
//import com.shuyu.gsyvideoplayer.model.MovieVideoDetails;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by shuyu on 2016/12/20.
// */
//
//public class ListGSYVideoPlayer extends StandardGSYVideoPlayer {
//
//    protected List<GSYVideoModel> mUriList = new ArrayList<>();
//    protected int mPlayPosition;
//    private int t = -1;
//    //    private List<MovieVideoDetails.DataBean.VideoNumListBean> mList;
//    private String mUrl_name;
//    private String mVideo_id;
//    private String mV_type;
//    private String mOrigin;
//    private GSYVideoModel mGsyVideoModel;
//
//    /**
//     * 1.5.0开始加入，如果需要不同布局区分功能，需要重载
//     */
//    public ListGSYVideoPlayer(Context context, Boolean fullFlag) {
//        super(context, fullFlag);
//    }
//
//    public ListGSYVideoPlayer(Context context) {
//        super(context);
//    }
//
//    public ListGSYVideoPlayer(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//
//    /**
//     * 设置播放URL
//     *
//     * @param url           播放url
//     * @param cacheWithPlay 是否边播边缓存
//     * @param objects       object[0]目前为title
//     * @return
//     */
//    public boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay, int position, Object... objects) {
//        t++;
//        mUriList = url;
//        mPlayPosition = position;
//        GSYVideoModel gsyVideoModel = url.get(0);
//        boolean set = setUp(gsyVideoModel.getUrl(), cacheWithPlay, objects);
//        if (!TextUtils.isEmpty(gsyVideoModel.getTitle())) {
//            mTitleTextView.setText(gsyVideoModel.getTitle());
//        }
//        return set;
//    }
//
//
//    //有头或者没头多连接
//    @Override
//    public boolean setUp(List<GSYVideoModel> gsyVideoModels, int tDuration, boolean cacheWithPlay, int position, File cachePath, Map<String, String> mapHeadData, Object... objects) {
////        t++;
////        mList = (List<MovieVideoDetails.DataBean.VideoNumListBean>) objects[0];
//        mUriList = gsyVideoModels;
//        mPlayPosition = position;
//        GSYVideoModel gsyVideoModel = mUriList.get(0);
//        if (!TextUtils.isEmpty(gsyVideoModel.getTitle())) {
//            mTitleTextView.setText(gsyVideoModel.getTitle());
//        }
//        return super.setUp(gsyVideoModels, tDuration, cacheWithPlay, position, cachePath, mapHeadData, "");
//    }
//
//
//    //有头单链接
//    @Override
//    public boolean setUp(List<GSYVideoModel> gsyVideoModels, boolean cacheWithPlay, int position, File cachePath, Map<String, String> mapHeadData, Object... objects) {
//        t++;
//        mUriList = gsyVideoModels;
//        mPlayPosition = position;
//        GSYVideoModel gsyVideoModel = gsyVideoModels.get(0);
//        if (!TextUtils.isEmpty(gsyVideoModel.getTitle())) {
//            mTitleTextView.setText(gsyVideoModel.getTitle());
//        }
//        return super.setUp(gsyVideoModels, cacheWithPlay, position, cachePath, mapHeadData, "");
//    }
//
////    @Override
////    protected void prepareVideo() {
//////        GSYPreViewManager.instance().prepare(mUrl, mMapHeadData, mLooping, mSpeed);
//////        super.prepareVideo();
////    }
//
//    /**
//     * 设置播放URL
//     *
//     * @param url           播放url
//     * @param cacheWithPlay 是否边播边缓存
//     * @param cachePath     缓存路径，如果是M3U8或者HLS，请设置为false
//     * @param objects       object[0]目前为title
//     * @return
//     */
//
//    //没头单链接
//    public boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay, int position, File cachePath, Object... objects) {
//        t++;
//        mUriList = url;
//        mPlayPosition = position;
//        GSYVideoModel gsyVideoModel = url.get(0);
//        boolean set = setUp(gsyVideoModel.getUrl(), cacheWithPlay, cachePath, objects);
//        if (!TextUtils.isEmpty(gsyVideoModel.getTitle())) {
//            mTitleTextView.setText(gsyVideoModel.getTitle());
//        }
//        return set;
//    }
//
//    @Override
//    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar, boolean isMember, String type) {
//        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar, isMember, type);
//        if (gsyBaseVideoPlayer != null) {
//            ListGSYVideoPlayer listGSYVideoPlayer = (ListGSYVideoPlayer) gsyBaseVideoPlayer;
//            listGSYVideoPlayer.mPlayPosition = t;
//            listGSYVideoPlayer.mUriList = mUriList;
//            if (t >= 1 && !isMember && type.equals("2")) {
//                mGsyVideoModel = mUriList.get(0);
//
//            } else {
//                mGsyVideoModel = mUriList.get(t);
//
//            }
//            if (!TextUtils.isEmpty(mGsyVideoModel.getTitle())) {
//                listGSYVideoPlayer.mTitleTextView.setText(mGsyVideoModel.getTitle());
//            }
//        }
//        return gsyBaseVideoPlayer;
//    }
//
//    @Override
//    public void onCompletion() {
//        if (mPlayPosition < (mUriList.size() - 1)) {
//            return;
//        }
//        super.onCompletion();
//    }
//
////    @Override
////    public void onAutoCompletion() {
//////
//////////        if (mPlayPosition < (mUriList.size() - 1)) {
//////        if (mPlayPosition < (mList.size() - 1)) {
//////                mPlayPosition++;
//////            mUrl_name = mList.get(mPlayPosition).getUrl_name();//要播放的剧名
//////            mVideo_id = mList.get(mPlayPosition).getVid();//要播放的id
//////            mV_type = mList.get(mPlayPosition).getV_type();//要播放的vType
//////            mUrl = mList.get(mPlayPosition).getUrl();//要播放的url
//////
//////
//////                GSYVideoModel gsyVideoModel = mUriList.get(mPlayPosition);
//////////            MovieVideoDetails.DataBean.VideoNumListBean videoNumListBean = mList.get(mPlayPosition);
////////
//////            super.setUp(gsyVideoModel.getUrl(), mCache, mCachePath, mObjects);
//////////                if (!TextUtils.isEmpty(gsyVideoModel.getTitle())) {
//////////                    mTitleTextView.setText(gsyVideoModel.getTitle());
//////////                }
////////                startPlayLogic();
////////                return;
//////            }
////        super.onAutoCompletion();
////    }
//
//}


package com.shuyu.gsyvideoplayer.video;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuyu on 2016/12/20.
 */

public class ListGSYVideoPlayer extends StandardGSYVideoPlayer {

    protected List<GSYVideoModel> mUriList = new ArrayList<>();
    protected int mPlayPosition;

    /**
     * 1.5.0开始加入，如果需要不同布局区分功能，需要重载
     */
    public ListGSYVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public ListGSYVideoPlayer(Context context) {
        super(context);
    }

    public ListGSYVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 设置播放URL
     *
     * @param url           播放url
     * @param cacheWithPlay 是否边播边缓存
     * @param objects       object[0]目前为title
     * @return
     */
    public boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay, int position, Object... objects) {
        mUriList = url;
        mPlayPosition = position;
        GSYVideoModel gsyVideoModel = url.get(position);
        boolean set = setUp(gsyVideoModel.getUrl(), cacheWithPlay, objects);
        if (!TextUtils.isEmpty(gsyVideoModel.getTitle())) {
            mTitleTextView.setText(gsyVideoModel.getTitle());
        }
        return set;
    }

    /**
     * 设置播放URL
     *
     * @param url           播放url
     * @param cacheWithPlay 是否边播边缓存
     * @param cachePath     缓存路径，如果是M3U8或者HLS，请设置为false
     * @param objects       object[0]目前为title
     * @return
     */
    public boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay, int position, File cachePath, Object... objects) {
        mUriList = url;
        mPlayPosition = position;
        GSYVideoModel gsyVideoModel = url.get(position);
        boolean set = setUp(gsyVideoModel.getUrl(), cacheWithPlay, cachePath, objects);
        if (!TextUtils.isEmpty(gsyVideoModel.getTitle())) {
            mTitleTextView.setText(gsyVideoModel.getTitle());
        }
        return set;
    }

    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar, boolean isMember, String type) {
        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar, isMember, type);
        if (gsyBaseVideoPlayer != null) {
            ListGSYVideoPlayer listGSYVideoPlayer = (ListGSYVideoPlayer) gsyBaseVideoPlayer;
            listGSYVideoPlayer.mPlayPosition = mPlayPosition;
            listGSYVideoPlayer.mUriList = mUriList;
            listGSYVideoPlayer.mTitleTextView.setText(this.getTitleTextView().getText());
            if (mUriList.size() > mPlayPosition) {
                GSYVideoModel gsyVideoModel = mUriList.get(mPlayPosition);
                if (!TextUtils.isEmpty(gsyVideoModel.getTitle())) {
                    listGSYVideoPlayer.mTitleTextView.setText(gsyVideoModel.getTitle());
                }
                listGSYVideoPlayer.testTv.setText(gsyVideoModel.getTitle());
            }
        }
        return gsyBaseVideoPlayer;
    }

    //    @Override
//    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
//        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar);
//        if (gsyBaseVideoPlayer != null) {
//            ListGSYVideoPlayer listGSYVideoPlayer = (ListGSYVideoPlayer) gsyBaseVideoPlayer;
//            listGSYVideoPlayer.mPlayPosition = mPlayPosition;
//            listGSYVideoPlayer.mUriList = mUriList;
//            GSYVideoModel gsyVideoModel = mUriList.get(mPlayPosition);
//            if (!TextUtils.isEmpty(gsyVideoModel.getTitle())) {
//                listGSYVideoPlayer.mTitleTextView.setText(gsyVideoModel.getTitle());
//            }
//        }
//        return gsyBaseVideoPlayer;
//    }

    @Override
    protected void resolveNormalVideoShow(View oldF, ViewGroup vp, GSYVideoPlayer gsyVideoPlayer) {
        if (gsyVideoPlayer != null) {
            ListGSYVideoPlayer listGSYVideoPlayer = (ListGSYVideoPlayer) gsyVideoPlayer;
            mPlayPosition = listGSYVideoPlayer.mPlayPosition;
            mUriList = listGSYVideoPlayer.mUriList;
            if (mUriList.size() > mPlayPosition) {
                GSYVideoModel gsyVideoModel = mUriList.get(mPlayPosition);
                if (!TextUtils.isEmpty(gsyVideoModel.getTitle())) {
                    mTitleTextView.setText(gsyVideoModel.getTitle());
                }
                testTv.setText(gsyVideoModel.getTitle());
            }
        }
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer);
    }

    public void setTest(String name) {
        if (testTv != null)
            testTv.setText(name);
        else
            getTestTv().setText(name);

    }


    @Override
    public void onCompletion() {
        if (mPlayPosition < (mUriList.size() - 1)) {
            return;
        }
        super.onCompletion();
    }

    @Override
    public void onAutoCompletion() {
//        if (mPlayPosition < (mUriList.size() - 1)) {
//            mPlayPosition++;
//            GSYVideoModel gsyVideoModel = mUriList.get(mPlayPosition);
//            setUp(gsyVideoModel.getUrl(), mCache, mCachePath, mObjects);
//            if (!TextUtils.isEmpty(gsyVideoModel.getTitle())) {
//                mTitleTextView.setText(gsyVideoModel.getTitle());
//            }
//            startPlayLogic();
//            return;
//        }
        super.onAutoCompletion();
    }

}