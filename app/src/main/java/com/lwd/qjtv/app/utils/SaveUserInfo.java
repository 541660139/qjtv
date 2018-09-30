package com.lwd.qjtv.app.utils;

import android.net.Uri;

import com.lwd.qjtv.mvp.model.entity.MyUserInfo;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/8.
 */

public class SaveUserInfo {

    public static void saveUser(String uid, String userName, String phone, String avater, String sex, String level) {
        setUid(uid);
        setUserName(userName);
        setPhone(phone);
        setAvater(avater);
        setLevel(level);
        setSex(sex);
        SaveUserInfo.setLogin(true);
        Constant.HAS_LOGIN = true;

    }

    public static MyUserInfo getUserInfo() {
        MyUserInfo userInfo = new MyUserInfo();
        userInfo.setUserID(Long.parseLong(getUid()));
        userInfo.setNickname(getUserName());
        userInfo.setUserAvater(Uri.parse(getAvater()).getPath());
        return userInfo;
    }


    public static void logout() {
        setLogin(false);
        setUid("0");
        setLevel("0");
        setUserName("0");
        setPhone("0");
        setAvater("0");
        Constant.HAS_LOGIN = false;
//        Preference.clearFlag(Constant.UID);
//        Preference.clearFlag(Constant.LEVEL);
//        Preference.clearFlag(Constant.USER_NAME);
//        Preference.clearFlag(Constant.PHONE);
//        Preference.clearFlag(Constant.AVATER);
    }


    public static String getUid() {
        return Preference.get(Constant.UID, "0");

    }

    public static void setToken(String apiToken) {
        Preference.put(Constant.API_TOKEN, apiToken);
    }

    public static String getToken() {
        return Preference.get(Constant.API_TOKEN, "");
    }

    public static boolean getLogin() {
        return Preference.get(Constant.IS_LOGIN, false);
    }

    public static String getLevel() {
        return Preference.get(Constant.LEVEL, "1");
    }

    public static String getExperienceValue() {
        return Preference.get(Constant.EX_VALUE, "0");
    }

    public static String getExperienceMax() {
        return Preference.get(Constant.EX_MAX, "0");
    }

    public static String getUserName() {
        return Preference.get(Constant.USER_NAME, "0");
    }

    public static String getPhone() {
        return Preference.get(Constant.PHONE, "0");
    }

    public static String getAvater() {
        return Preference.get(Constant.AVATER, "0");
    }

    public static void setUid(String uid) {
        Preference.put(Constant.UID, uid);
    }

    public static void setUserName(String userName) {
        Preference.put(Constant.USER_NAME, userName);
    }

    public static void setPhone(String phone) {
        Preference.put(Constant.PHONE, phone);
    }

    public static void setUnamekey(String Unamekey) {
        Preference.put(Constant.UNAMEKEY, Unamekey);
    }

    public static String getUnamekey() {
        return Preference.get(Constant.UNAMEKEY, "0");
    }


    public static void setAvater(String avater) {
        Preference.put(Constant.AVATER, avater);
    }

    public static void setLogin(boolean login) {
        Preference.put(Constant.IS_LOGIN, login);
    }

    public static void setCoin(String coin) {
        Preference.put(Constant.COIN, coin);
    }

    public static String getCoin() {
        return Preference.get(Constant.COIN, "0");
    }

    public static void setYqLink(String yq_link) {
        Preference.put(Constant.YQ_LINK, yq_link);
    }

    public static String getYqLink() {
        return Preference.get(Constant.YQ_LINK, "0");
    }

    public static void setLevel(String level) {
        Preference.put(Constant.LEVEL, level);
    }

    public static void setSex(String sex) {
        Preference.put(Constant.SEX, sex);
    }

    public static void setExperienceValue(String experienceValue) {
        Preference.put(Constant.EX_VALUE, experienceValue);
    }

    public static void setExperienceMax(String experienceMax) {
        Preference.put(Constant.EX_MAX, experienceMax);
    }

    public static void setUserType(String usertype) {
        Preference.put(Constant.IS_RECHARGE_USER, usertype == null ? false : !"1".equals(usertype));
        Preference.put(Constant.IS_TOP_LEVEL_USER, usertype == null ? false : "3".equals(usertype));
    }

    public static boolean getUserType() {
        return Preference.getBoolean(Constant.IS_RECHARGE_USER, false);
    }

    public static boolean isTopLevelUser() {
        return Preference.getBoolean(Constant.IS_TOP_LEVEL_USER, false);
    }


    public static boolean getHasOtherCai() {
        return Preference.getBoolean(Constant.HASOTHERCAI);
    }

    public static void setHasOtherCai(boolean hasOtherCai) {
        Preference.putBoolean(Constant.HASOTHERCAI, hasOtherCai);
    }


}
