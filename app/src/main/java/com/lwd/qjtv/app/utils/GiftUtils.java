package com.lwd.qjtv.app.utils;

import com.lwd.qjtv.R;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/14.
 */

public class GiftUtils {

    public static String getGiftName(String id) {
        String giftname = "";
        int i = Integer.parseInt(id);
        switch (i) {
            case 1:
                giftname = "领结";
                break;
            case 2:
                giftname = "皮头";
                break;
            case 3:
                giftname = "爆米花";
                break;
            case 4:
                giftname = "巧粉";
                break;
            case 5:
                giftname = "鲜花";
                break;
            case 6:
                giftname = "啤酒";
                break;
            case 7:
                giftname = "手表";
                break;
            case 8:
                giftname = "礼炮";
                break;
            case 9:
                giftname = "奖杯";
                break;
            case 10:
                giftname = "游艇";
                break;
            case 11:
                giftname = "飞机";
                break;
            case 12:
                giftname = "跑车";
                break;
            case 13:
                giftname = "火箭";
                break;
            case 14:
                giftname = "龙";
                break;
        }
        return giftname;
    }

    public static int getGiftId(String giftName) {
        int idRes = 0 ;
        switch (giftName) {
            case "领结":
                idRes = 1;
                break;
            case "皮头":
                idRes = 2;
                break;
            case "爆米花":
                idRes = 3;
                break;
            case "巧粉":
                idRes = 4;
                break;
            case "鲜花":
                idRes = 5;
                break;
            case "啤酒":
                idRes = 6;
                break;
            case "手表":
                idRes = 7;
                break;
            case "礼炮":
                idRes = 8;
                break;
            case "奖杯":
                idRes = 9;
                break;
            case "游艇":
                idRes = 10;
                break;
            case "飞机":
                idRes = 11;
                break;
            case "跑车":
                idRes = 12;
                break;
            case "火箭":
                idRes = 13;
                break;
            case "龙":
                idRes = 14;
                break;
        }
        return idRes;
    }

    public static int getGiftIdres(String id) {
        int idRes = 0;
        int i = Integer.parseInt(id);
        switch (i) {
            case 1:
                idRes = R.mipmap.gift_one;
                break;
            case 2:
                idRes = R.mipmap.gift_two;
                break;
            case 3:
                idRes = R.mipmap.gift_three;
                break;
            case 4:
                idRes = R.mipmap.gift_four;
                break;
            case 5:
                idRes = R.mipmap.gift_five;
                break;
            case 6:
                idRes = R.mipmap.gift_six;
                break;
            case 7:
                idRes = R.mipmap.gift_seven;
                break;
            case 8:
                idRes = R.mipmap.gift_eight;
                break;
            case 9:
                idRes = R.mipmap.gift_nine;
                break;
            case 10:
                idRes = R.mipmap.gift_ten;
                break;
            case 11:
                idRes = R.mipmap.gift_eleven;
                break;
            case 12:
                idRes = R.mipmap.gift_twelve;
                break;
            case 13:
                idRes = R.mipmap.gift_thirteen;
                break;
            case 14:
                idRes = R.mipmap.gift_forteen;
                break;
        }
        return idRes;
    }

}
