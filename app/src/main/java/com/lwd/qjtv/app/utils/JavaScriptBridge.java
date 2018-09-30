package com.lwd.qjtv.app.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.mvp.ui.activity.LoginActivity;
import com.lwd.qjtv.mvp.ui.activity.PushCommunityActivity;
import com.lwd.qjtv.mvp.ui.activity.RechargeActivity;
import com.lwd.qjtv.mvp.ui.activity.WebActivity;
import com.lwd.qjtv.mvp.ui.activity.WebAddressActivity;
import com.lwd.qjtv.mvp.ui.activity.WebNewActivity;
import com.lwd.qjtv.mvp.ui.activity.WebViewActivity;
import com.lwd.qjtv.view.BetDialog;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/2.
 */

public class JavaScriptBridge {

    private static final String TAG = "JavaScriptBridge";
    private Context c;
    private ProgressDialog mSaveDialog;
    private final static String ALBUM_PATH = Environment
            .getExternalStorageDirectory() + "/snooker/";
    private Bitmap mBitmap;
    private String mSaveMessage;
    private String mFileName;
    private String filePath;
    private Contact contact;

    public JavaScriptBridge(Context c) {
        this.c = c;
    }


    //在java中调用js代码
    public void sendInfoToJs(WebView view, String type) {
        //调用js中的函数：showInfoFromJava(msg)
        view.loadUrl("javascript:jump('" + type + "')");
//        webView.loadUrl("javascript:showInfoFromJava()");
    }

    @JavascriptInterface
    public void jump(String url, String id) {
        Log.d("js", "jump: " + url);
        Intent intent = new Intent(c, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("id", id);
        c.startActivity(intent);
    }

    @JavascriptInterface
    public void jumpaddress() {
        Intent intent = new Intent(c, WebAddressActivity.class);
//        intent.putExtra("isWeb",true);
        ((Activity) c).startActivityForResult(intent, 0x001);
    }

    @JavascriptInterface
    public void jumpAddress(String url) {
        Intent intent = new Intent(c, WebActivity.class);
        intent.putExtra("url", url);
        c.startActivity(intent);
    }

    @JavascriptInterface
    public void jumpDetail(String url) {
        Intent intent = new Intent(c, WebActivity.class);
        intent.putExtra("url", url);
        c.startActivity(intent);
    }

    @JavascriptInterface
    public void hf_a(String comment_id, String username) {
        Log.d("fangfa", "hf_a");
        if (Preference.getBoolean(Constant.IS_LOGIN) || Constant.HAS_LOGIN) {
            ((WebNewActivity) c).setCopy(comment_id, null, username);
        } else {
            Intent intent = new Intent(c, LoginActivity.class);
            c.startActivity(intent);
        }

    }

//    @JavascriptInterface
//    public void hf_b(String comment_id, String to_uid, String username) {
//        Log.d("fangfa", "hf_b");
//        if (Preference.getBoolean(Constant.IS_LOGIN) || Constant.HAS_LOGIN) {
//            ((WebNewActivity) c).setCopy(comment_id, to_uid, username);
//        } else {
//            Intent intent = new Intent(c, LoginActivity.class);
//            c.startActivity(intent);
//        }
//
//    }



    @JavascriptInterface
    public void jing_cai(String tp, String match_id) {

        HashMap<String, String> map = new HashMap<>();
        map.put("match_id", match_id);
        if (contact == null)
            contact = new Contact(c);
        String webUrl = contact.getWebUrl(tp, map);
        Intent intent = new Intent(c, WebNewActivity.class);
        intent.putExtra("url", webUrl);
        c.startActivity(intent);
    }

    @JavascriptInterface
    public void jing_cai(String match_id, String jc_type, String player_a_id, String player_a_name, String player_b_id, String player_b_name, String jc_id, String jc_pl_id, String peilv) {

        BetDialog betDialog = new BetDialog(c);
        betDialog.setData(player_a_name + " VS " + player_b_name, player_a_name + " " + (jc_pl_id.equals("1") ? "左胜" : "左负") + " " + player_b_name, peilv);
        betDialog.setApiParam(match_id, jc_id, jc_pl_id, jc_type);
        betDialog.show();
    }

    @JavascriptInterface
    public void jing_cai(String tp) {

        if (contact == null)
            contact = new Contact(c);
        String webUrl = contact.getWebUrl(tp);
        Intent intent = new Intent(c, WebNewActivity.class);
        intent.putExtra("url", webUrl);
        c.startActivity(intent);
    }

    @JavascriptInterface
    public void jump_info(String tp, String card_id, String p, String from_uid, String main_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("card_id", card_id);
        map.put("p", p);
//        map.put("from_uid",from_uid);
//        map.put("main_id",main_id);
        if (contact == null)
            contact = new Contact(c);
        String webUrl = contact.getWebUrl(tp, map);
        Intent intent = new Intent(c, WebNewActivity.class);
        intent.putExtra("url", webUrl);
        intent.putExtra("card_id", card_id);
        intent.putExtra("from_uid", from_uid);
        intent.putExtra("main_id", main_id);
        intent.putExtra("is_bbs_details", true);
        c.startActivity(intent);

    }

    @JavascriptInterface
    public void jump_fb(String main_id) {

        Log.d("fangfa", "jump_fb");
        if (Preference.getBoolean(Constant.IS_LOGIN) || Constant.HAS_LOGIN) {
            Intent intent = new Intent(c, PushCommunityActivity.class);
            intent.putExtra("vid", main_id);
            intent.putExtra("is_edit", false);
            c.startActivity(intent);

        } else {

            Intent intent = new Intent(c, LoginActivity.class);
            c.startActivity(intent);
        }
    }

    @JavascriptInterface
    public void jump_login(String id) {
        Log.d("fangfa", "jump_login");
        Intent intent = new Intent(c, LoginActivity.class);
        ((Activity) c).startActivityForResult(intent, Constant.WB_TO_LOGIN);

    }

    @JavascriptInterface
    public void saveImg(String imgURL, String none) {
        filePath = imgURL;
        mSaveDialog = ProgressDialog.show(c, "保存图片", "图片正在保存中，请稍等...", true);
        new Thread(connectNet).start();
    }

    @JavascriptInterface
    public void share(String title, String content, String icon, String url) {
        final OnekeyShare oks = new OnekeyShare();
        oks.setSilent(true);
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        if (!TextUtils.isEmpty(icon)) {
            oks.setImageUrl(icon);
        }
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("斯诺克");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);
        //启动分享
        oks.show(WEApplication.getContext());
    }

    @JavascriptInterface
    public void jumpRecharge() {
        Intent intent = new Intent(c, RechargeActivity.class);
        c.startActivity(intent);
    }

    @JavascriptInterface
    public void addQQ(String qq) {
        final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq + "&version=1";
        c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
    }

    @JavascriptInterface
    public void paySuccess(int status, String msg) {
        switch (status) {
            case 1:
                UiUtils.makeText(c, msg);
                ((Activity) c).finish();
                break;
            case 12:
                showDialog(status, msg);
                break;
            case 13:
                UiUtils.makeText(c, msg);
                break;
            case 15:
                showDialog(status, msg);
                break;
            default:
                UiUtils.makeText(c, msg);
                break;
        }
    }

    private void showDialog(int status, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        AlertDialog alertDialog = builder.create();
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "前往充值", (dialog, which) -> {
            switch (status) {
                case 12:
                    //// TODO: 2017/8/9 联系客服
                    break;
                case 15:
                    c.startActivity(new Intent(c, RechargeActivity.class));
                    break;
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "取消", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.setTitle("温馨提醒");
        alertDialog.setMessage(msg);
        alertDialog.show();
    }


    /**
     * Get image from newwork
     *
     * @param path The path of image
     * @return byte[]
     * @throws Exception
     */
    public byte[] getImage(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        InputStream inStream = conn.getInputStream();
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return readStream(inStream);
        }
        return null;
    }

    /**
     * Get image from newwork
     *
     * @param path The path of image
     * @return InputStream
     * @throws Exception
     */
    public InputStream getImageStream(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return conn.getInputStream();
        }
        return null;
    }

    /**
     * Get data from stream
     *
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public void saveFile(Bitmap bm, String fileName) throws IOException {
        File dirFile = new File(ALBUM_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(ALBUM_PATH + fileName);
        Log.d(TAG, "saveFile: " + myCaptureFile.getPath());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        c.sendBroadcast(intent);
    }

    private Runnable saveFileRunnable = new Runnable() {


        @Override
        public void run() {
            try {
                saveFile(mBitmap, mFileName);
                mSaveMessage = "图片保存成功！";
            } catch (IOException e) {
                mSaveMessage = "图片保存失败！";
                e.printStackTrace();
            }
            messageHandler.sendMessage(messageHandler.obtainMessage());
        }
    };

    private Handler messageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mSaveDialog.dismiss();
            Toast.makeText(c, "保存成功", Toast.LENGTH_SHORT).show();
        }
    };

    /*
     * 连接网络
     * 由于在4.0中不允许在主线程中访问网络，所以需要在子线程中访问
     */
    private Runnable connectNet = new Runnable() {


        @Override
        public void run() {
            try {
                mFileName = "alipay.jpg";

                //以下是取得图片的两种方法
                //////////////// 方法1：取得的是byte数组, 从byte数组生成bitmap
                byte[] data = getImage(filePath);
                if (data != null) {
                    mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap
                } else {

                }
                ////////////////////////////////////////////////////////

                //******** 方法2：取得的是InputStream，直接从InputStream生成bitmap ***********/
                mBitmap = BitmapFactory.decodeStream(getImageStream(filePath));
                new Thread(saveFileRunnable).start();
                //********************************************************************/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
