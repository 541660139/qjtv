package com.lwd.qjtv.view.gift;

import android.animation.LayoutTransition;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;


import com.lwd.qjtv.mvp.ui.message.GiftMessage;

import io.rong.imlib.model.UserInfo;

/**
 * Created by gongyasen on 16/7/27.
 */
public class GiftLayout extends LinearLayout {
    private GiftShowManager giftManger;

    public GiftLayout(Context context) {
        this(context, null);
    }

    public GiftLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GiftLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutTransition(new LayoutTransition());
        giftManger = new GiftShowManager(getContext(), this);
        setOrientation(VERTICAL);
    }

    public void start() {
        giftManger.showGift();//开始显示礼物
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (giftManger != null) giftManger.stop();
    }

    public void addGift(final GiftMessage msg) {
        UserInfo userInfo = msg.getUserInfo();
        if (userInfo != null) {
            String name = userInfo.getName();
            String userId = userInfo.getUserId();
            String number = (msg.getGiftNumber() == null || msg.getGiftNumber().equals("")) ? "1" : msg.getGiftNumber();
            String goalUserName = msg.getGoalUserName();
            String giftName = msg.getGiftName();
            if (!TextUtils.isEmpty(name)
                    && !TextUtils.isEmpty(userId)
                    && !TextUtils.isEmpty(giftName)
                    && !TextUtils.isEmpty(goalUserName)
                    && !TextUtils.isEmpty(number)) {
                giftManger.addGift(new GiftVo() {
                    @Override
                    public String getName() {
                        return name;
                    }

                    @Override
                    public int getNum() {
                        return Integer.valueOf(number);
                    }

                    @Override
                    public String getGiftName() {
                        return giftName;
                    }

                    @Override
                    public String generateId() {
                        return goalUserName + giftName;
                    }

                    @Override
                    public String getGivenName() {
                        return goalUserName;
                    }
                });
            } else {
//                LogUtils.e(this, "礼物消息异常");
            }
        } else {
//            LogUtils.e(this, "礼物消息无用户信息");
        }

    }
}
