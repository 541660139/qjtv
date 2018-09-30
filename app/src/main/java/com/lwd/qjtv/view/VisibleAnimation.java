package com.lwd.qjtv.view;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/22.
 */

public class VisibleAnimation {

    /**
     * 设置View的隐藏和显示
     * @param view
     * @param visibility
     */
    public static void setVisible(View view, int visibility) {
        if (view != null)
            view.setVisibility(visibility);
        if(visibility == View.GONE)
            view.setAnimation(getHideAction());
        else
            view.setAnimation(getShowAction());
    }

    /**
     * 获取显示动画
     * @return
     */
    public static TranslateAnimation getShowAction() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        translateAnimation.setDuration(500);
        translateAnimation.setRepeatCount(0);
        return translateAnimation;

    }

    /**
     * 获取消失动画
     * @return
     */
    public static TranslateAnimation getHideAction() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        translateAnimation.setDuration(500);
        translateAnimation.setRepeatCount(0);
        return translateAnimation;
    }

}
