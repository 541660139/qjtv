package com.lwd.qjtv.app.utils;

import android.app.Activity;

import com.jess.arms.base.App;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.mvp.ui.activity.LiveActivity;
import com.lwd.qjtv.mvp.ui.activity.MyGuessActivity;
import com.lwd.qjtv.mvp.ui.activity.RechargeCostActivity;
import com.lwd.qjtv.mvp.ui.message.RCServerPushMessage;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;

import static com.lwd.qjtv.app.utils.Constant.PUSH_MSG_CAL_COIN;
import static com.lwd.qjtv.app.utils.Constant.PUSH_MSG_CHANGE_LIVE_URL;
import static com.lwd.qjtv.app.utils.Constant.PUSH_MSG_LIVE_OVER;
import static com.lwd.qjtv.app.utils.Constant.PUSH_MSG_LIVE_START;
import static com.lwd.qjtv.app.utils.Constant.PUSH_MSG_UPDATE_GONGGAO;
import static com.lwd.qjtv.app.utils.RongYunUtils.MESSAGE_ARRIVED;

/**
 * Created by ZhengQian on 2017/12/4.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class RongYunMessageListener implements RongIMClient.OnReceiveMessageListener {
    @Override
    public boolean onReceived(Message message, int i) {
        MessageContent msg = message.getContent();
        RongYunUtils.handleEvent(MESSAGE_ARRIVED, message.getContent());
        if (msg instanceof RCServerPushMessage) {
            String status = ((RCServerPushMessage) msg).getStatus();
            Activity currentActivity = ((App) WEApplication.getContext()).getAppComponent().appManager().getCurrentActivity();
            switch (status) {
                case PUSH_MSG_LIVE_START:
                    //首页
                    //刷新首页直播数据
//                    if (currentActivity instanceof MainActivity) {
//                        WatchBallFragment homeFragment = ((MainActivity) currentActivity).getHomeFragment();
//                        if (homeFragment != null)
//                            homeFragment.onRefresh(null);
//                    }
                    break;
                case PUSH_MSG_LIVE_OVER:
                    //直播间
                    //提示直播结束
                    if (currentActivity instanceof LiveActivity) {
                        ((LiveActivity) currentActivity).liveOver();
                    }
                    break;
                case PUSH_MSG_CAL_COIN:
                    //我的竞猜刷新、收支记录刷新
                    if (currentActivity instanceof MyGuessActivity || currentActivity instanceof RechargeCostActivity) {
                    }
                    break;
                case PUSH_MSG_UPDATE_GONGGAO:
                    //直播间刷新公告
//                    if (currentActivity instanceof LiveActivity) {
//                        ((LiveActivity) currentActivity).setGonggaoTv(((RCServerPushMessage) msg).getGonggao());
//                    }
                    break;
                case PUSH_MSG_CHANGE_LIVE_URL:
                    //直播间刷新直播地址
                    if (currentActivity instanceof LiveActivity) {
                        ((LiveActivity) currentActivity).setUrl(((RCServerPushMessage) msg).getPlayUrl(), ((RCServerPushMessage) msg).getOrigin());
                    }
                    break;
            }
        }
        return false;
    }
}
