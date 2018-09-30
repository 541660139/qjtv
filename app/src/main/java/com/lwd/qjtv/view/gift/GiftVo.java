package com.lwd.qjtv.view.gift;

/**
 * @author zhongxf
 * @Description 礼物的实体类
 * @Date 2016/6/6.
 */
public interface GiftVo {

//    String getUserId();

    String getName();//获取用户名

    int getNum();

//    Integer getGiftUrl();

    String getGiftName();

    String generateId();//可以使用 uid+giftid拼起来组成一个唯一标识

    String getGivenName();
}
