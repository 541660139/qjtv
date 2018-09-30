package com.lwd.qjtv.mvp.model.entity;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by DELL on 2018/9/3.
 */

public class CommonTabBean implements CustomTabEntity {

    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public CommonTabBean(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }

}
