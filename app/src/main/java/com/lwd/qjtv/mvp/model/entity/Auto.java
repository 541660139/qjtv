package com.lwd.qjtv.mvp.model.entity;


import com.lwd.qjtv.view.AutoRollLayout;

public class Auto implements AutoRollLayout.IShowItem {
    private String url;
    private String title;

    @Override
    public String getImageUrl() {
        return url;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public Auto(String url, String title) {
        super();
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Auto() {
        super();
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
