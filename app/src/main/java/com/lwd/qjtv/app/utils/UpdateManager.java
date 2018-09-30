package com.lwd.qjtv.app.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.provider7.ProviderSevenUriUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateManager {
    private Context mContext; //上下文

    private String apkUrl =
            "download_url"; //apk下载地址
    private static final String savePath = "/sdcard/updateAPK/"; //apk保存到SD卡的路径
    private String saveFileName = savePath + "snooker"; //完整路径名
    private NumberProgressBar mProgress; //下载进度条控件
    private static final int DOWNLOADING = 1; //表示正在下载
    private static final int DOWNLOADED = 2; //下载完毕
    private static final int DOWNLOAD_FAILED = 3; //下载失败
    private int progress; //下载进度
    private boolean cancelFlag = false; //取消下载标志位
    private String versionCode;
    private double serverVersion = 2.0; //从服务器获取的版本号
    private double clientVersion = 1.0; //客户端当前的版本号
    private String updateDescription = "更新描述"; //更新内容描述信息
    private boolean forceUpdate = true; //是否强制更新

    private AlertDialog alertDialog1, alertDialog2; //表示提示对话框、进度条对话框

    /**
     * 构造函数
     */
    public UpdateManager(Context context) {
        this.mContext = context;
    }

    /**
     * 显示更新对话框
     */
    public void showNoticeDialog(String versionCode, String updateDes, boolean forceupdate, String downUrl, boolean check) {
//        //如果版本最新，则不需要更新
        if (!isUpdate(WEApplication.app_ver.split("\\."), versionCode.split("\\."))) {
            if (check)
                ToastUtil.showToast(mContext, "当前未检测到更新~");
            return;
        }
        apkUrl = downUrl;
        forceUpdate = forceupdate;
        this.versionCode = versionCode;
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("发现新版本 ：" + versionCode);
        dialog.setMessage(updateDes);
        dialog.setPositiveButton("现在更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                arg0.dismiss();
                showDownloadDialog();
            }
        });
        //是否强制更新
        if (forceUpdate == false) {
            dialog.setNegativeButton("待会更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    arg0.dismiss();
                }
            });
        }
        alertDialog1 = dialog.create();
        alertDialog1.setCancelable(!forceupdate);
        alertDialog1.show();
    }

    /**
     * 显示进度条对话框
     */
    public void showDownloadDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("正在更新");
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (NumberProgressBar) v.findViewById(R.id.update_progress);
        dialog.setView(v);
        //如果是强制更新，则不显示取消按钮
        if (forceUpdate == false) {
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    arg0.dismiss();
                    cancelFlag = false;
                }
            });
        }
        alertDialog2 = dialog.create();
        alertDialog2.setCancelable(false);
        alertDialog2.show();

        //下载apk
        downloadAPK();
    }

    /**
     * 下载apk的线程
     */
    public void downloadAPK() {
        new Thread(new Runnable() {
            private String apkFileName;

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    URL url = new URL(apkUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn .setRequestProperty("Accept-Encoding", "identity");
                    conn.connect();

                    int length = conn.getContentLength();
                    Log.d("length", "length:" + length);
                    InputStream is = conn.getInputStream();

                    File file = new File(savePath);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    apkFileName = saveFileName + versionCode + ".apk";
                    File ApkFile = new File(apkFileName);
                    FileOutputStream fos = new FileOutputStream(ApkFile);

                    int count = 0;
                    byte buf[] = new byte[1024];

                    do {
                        int numread = is.read(buf);
                        count += numread;
                        Log.d("length", "count:" + count);
                        progress = (int) (((float) count / length) * 100);
                        //更新进度
                        mHandler.sendEmptyMessage(DOWNLOADING);
                        if (numread <= 0) {
                            //下载完成通知安装
                            mHandler.sendEmptyMessage(DOWNLOADED);
                            break;
                        }
                        fos.write(buf, 0, numread);
                    } while (!cancelFlag); //点击取消就停止下载.

                    fos.close();
                    is.close();
                } catch (Exception e) {
                    mHandler.sendEmptyMessage(DOWNLOAD_FAILED);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 更新UI的handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case DOWNLOADING:
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOADED:
                    if (alertDialog2 != null)
                        alertDialog2.dismiss();
                    installAPK();
                    break;
                case DOWNLOAD_FAILED:
                    Toast.makeText(mContext, "" + mContext.getResources().getString(R.string.net_less), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 下载完成后自动安装apk
     */
    public void installAPK() {
        File apkFile = new File(saveFileName + versionCode + ".apk");
        if (!apkFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        ProviderSevenUriUtils.setIntentDataAndType(mContext,
                intent, "application/vnd.android.package-archive", apkFile, true);
        mContext.startActivity(intent);
    }


    /**
     * 判断版本
     *
     * @param clientVersion 客户端版本,获取版本号 调用.split(".") 生成string[]
     * @param serverVersion 服务端版本,获取版本号 调用.split(".") 生成string[]
     * @return
     */
    private boolean isUpdate(String[] clientVersion, String[] serverVersion) {
        boolean isUpdate = false;
        if (clientVersion.length > 0 && serverVersion.length > 0) {
            if (Integer.parseInt(clientVersion[0]) > Integer.parseInt(serverVersion[0]))
                isUpdate = false;
            else if (Integer.parseInt(clientVersion[0]) == Integer.parseInt(serverVersion[0])) {
                if (clientVersion.length > 1 && serverVersion.length > 1) {
                    if (Integer.parseInt(clientVersion[1]) > Integer.parseInt(serverVersion[1])) {
                        return false;
                    } else if (Integer.parseInt(clientVersion[1]) == Integer.parseInt(serverVersion[1])) {
                        if (clientVersion.length > 2 && serverVersion.length > 2) {
                            if (Integer.parseInt(clientVersion[2]) > Integer.parseInt(serverVersion[2]))
                                return false;
                            else if (Integer.parseInt(clientVersion[2]) == Integer.parseInt(serverVersion[2]))
                                return false;
                            else if (Integer.parseInt(clientVersion[2]) < Integer.parseInt(serverVersion[2]))
                                return true;
                        } else if (serverVersion.length > 2 && !serverVersion[2].equals("0"))
                            return true;
                        else
                            return false;
                    } else
                        isUpdate = true;
                } else if (serverVersion.length > 1 && !serverVersion[1].equals("0"))
                    return true;
                else
                    return false;
            } else
                return true;
        } else
            return false;
        return isUpdate;
    }

//    public String getVersion(Context context) {
//      try {
//              PackageManager manager = context.getPackageManager();
//              PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
//              String version = info.versionName;
//               return this.getString(R.string.version_name) + version;
//           } catch (Exception e) {
//               e.printStackTrace();
//               return this.getString(R.string.can_not_find_version_name);
//           }
//    }

}