package com.lwd.qjtv.view;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 网速显示view
 * static long  getMobileRxBytes()  //获取通过Mobile连接收到的字节总数，不包含WiFi
 * static long  getMobileRxPackets()  //获取Mobile连接收到的数据包总数
 * static long  getMobileTxBytes()  //Mobile发送的总字节数
 * static long  getMobileTxPackets()  //Mobile发送的总数据包数
 * static long  getTotalRxBytes()  //获取总的接受字节数，包含Mobile和WiFi等
 * static long  getTotalRxPackets()  //总的接受数据包数，包含Mobile和WiFi等
 * static long  getTotalTxBytes()  //总的发送字节数，包含Mobile和WiFi等
 * static long  getTotalTxPackets()  //发送的总数据包数，包含Mobile和WiFi等
 * static long  getUidRxBytes(int uid)  //获取某个网络UID的接受字节数
 * static long  getUidTxBytes(int uid) //获取某个网络UID的发送字节数
 * <p>
 */

public class NetSpeedView extends TextView {
    private Timer mTimer;
    private int uid;
    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;
    private long speed;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            setText(String.format("%s Kb/s", speed));
        }
    };

    public NetSpeedView(Context context) {
        this(context, null);
    }

    public NetSpeedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NetSpeedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        uid = getContext().getApplicationInfo().uid;
        lastTimeStamp = System.currentTimeMillis();
        lastTotalRxBytes = getTotalRxBytes();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                long nowTotalRxBytes = getTotalRxBytes();
                long nowTimeStamp = System.currentTimeMillis();
                speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000) / (nowTimeStamp - lastTimeStamp);//毫秒转换
                lastTimeStamp = nowTimeStamp;
                lastTotalRxBytes = nowTotalRxBytes;
                mHandler.sendEmptyMessage(0);
            }
        }, 0, 2000);
    }


    private long getTotalRxBytes() {
        long rxBytes = TrafficStats.getUidTxBytes(uid);
        return rxBytes == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转化为kb
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mHandler.removeCallbacksAndMessages(getWindowToken());
    }
}
