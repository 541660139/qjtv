package com.lwd.qjtv.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.lwd.qjtv.R;

/**
 * Created by Administrator on 2017/4/1.
 */

public class RechargeWayPopupWindow extends PopupWindow {
    private Context context;
    private PopupWindow popupWindow;
    private View view;

    public RechargeWayPopupWindow(Context context){
        this.context = context;

    }

    public void show(){
        if(popupWindow == null)
            return;
        popupWindow.showAsDropDown(getContentView());
    }

    public PopupWindow setLayout(int layoutId){
        popupWindow = new PopupWindow(context);
        view = LayoutInflater.from(context).inflate(layoutId,null);
        popupWindow.setContentView(view);
        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottomInOut);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        return popupWindow;
    }

    public View getItemView(int ids){
        if(view != null)
           return view.findViewById(ids);
        else
            return null;
    }
}
