package com.lwd.qjtv.app.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import java.util.ArrayList;

/**
 * 网络工具类，监听网络状况
 * Created by zhengqian on 2016/12/25.
 */
public class NetworkUtils {

    /**
     * 事件监听者列表
     */
    private static ArrayList<Handler> eventHandlerList = new ArrayList<>();
    private static int currNetwork;
    private static boolean hasNetwork;
    private static NetworkReceiver networkReceiver;

    /**
     * 添加IMLib 事件接收者。
     *
     * @param handler 接收网络变化信息的handler
     */
    public static void addEventHandler(Handler handler) {
        if (!eventHandlerList.contains(handler)) {
            eventHandlerList.add(handler);
        }
    }

    public static boolean isHasNetwork() {
        return hasNetwork;
    }

    /**
     * 移除IMLib 事件接收者。
     *
     * @param handler 注销避免内存泄漏
     */
    public static void removeEventHandler(Handler handler) {
        eventHandlerList.remove(handler);
    }

    /**
     * 获取当前网络类型
     *
     * @return 网络类型
     */
    public static int getCurrNetworkType() {
        return currNetwork;
    }

    public static void registerNetworkReceiver(Context context) {
        //注册网络状态改变广播
        networkReceiver = new NetworkReceiver();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mFilter.setPriority(IntentFilter.SYSTEM_LOW_PRIORITY);
        context.registerReceiver(networkReceiver, mFilter);
    }

    //网络广播接收
    public static class NetworkReceiver extends BroadcastReceiver {

        /**
         * 事件代码
         */
        public static final int MESSAGE_NETWORK_CONNECT = 0;
        public static final int MESSAGE_NETWORK_DISCONNECT = 1;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                return;
            }
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isAvailable()) {
                //网络连接
                hasNetwork = true;
                handleEvent(MESSAGE_NETWORK_CONNECT);
                currNetwork = netInfo.getType();
//                String name = netInfo.getTypeName();
                if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    //WiFi网络
                    handleEvent(22);
                } else if (netInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                    //有线网络
                } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    //3g网络
                    handleEvent(33);
                } else {
                    handleEvent(MESSAGE_NETWORK_CONNECT);
                }
            } else {
                //网络断开
                hasNetwork = false;
                handleEvent(MESSAGE_NETWORK_DISCONNECT);
            }
        }

        private void handleEvent(int what) {
            for (Handler handler : eventHandlerList) {
                android.os.Message m = android.os.Message.obtain();
                m.what = what;
                handler.sendMessage(m);
            }
        }
    }

    public static void unRegisterNetworkReceiver(Context context) {
        if (networkReceiver != null || context != null){
            try {
                context.unregisterReceiver(networkReceiver);
            } catch (IllegalArgumentException e) {
                if (e.getMessage().contains("Receiver not registered")) {
                    // Ignore this exception. This is exactly what is desired
                } else {
                    // unexpected, re-throw
                    throw e;
                }
            }
        }


    }
}
