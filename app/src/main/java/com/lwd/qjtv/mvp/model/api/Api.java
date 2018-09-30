package com.lwd.qjtv.mvp.model.api;

import com.lwd.qjtv.app.utils.Constant;

/**
 * Created by jess on 8/5/16 11:25
 * contact with jess.yan.effort@gmail.com
 */
public interface Api {
    String APP_DOMAIN = Constant.IP;
    String RequestSuccess = "1";
    String INDEX = "index.php?";

    //点量解析全地址
    String parseUrl = "http://update.flvurl.cn/siteparser/rule/custom/langweida/update.json";
}
