package com.lwd.qjtv.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/15.
 */

public class ZQAvaterUtils {

    public static final int CUT_PHOTO_REQUEST_CODE = 0x001;
    public static final int TAKE_PICTURE = 0x002;
    public static final int RESULT_LOAD_IMAGE = 0x003;
    public static final int SELECTIMG_SEARCH = 0x004;
    private static ZQAvaterUtils zqAvaterUtils ;
    private Context context;
    private String path;
    private Uri photoUri;
    private WeakReference<Activity> weakReference ;
    private Activity activity;

    public static ZQAvaterUtils getInstantce(){
        if(zqAvaterUtils == null)
            return new ZQAvaterUtils();
        else
            return zqAvaterUtils;
    }

    public void init(Activity context) {
        this.context = context;
        weakReference = new WeakReference<>(context);
        activity = weakReference.get();
    }

    public Intent startPhotoZoom(Uri uri) {
        try {
            // 获取系统时间 然后将裁剪后的图片保存至指定的文件夹
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
            String address = sDateFormat.format(new Date());
            if (!com.lwd.qjtv.app.utils.FileUtils.isFileExist("")) {
                com.lwd.qjtv.app.utils.FileUtils.createSDDir("");
            }
            Uri imageUri = Uri.parse("file:///sdcard/formats/" + address + ".JPEG");
            final Intent intent = getCameraIntent(uri, address, imageUri);
            path = com.lwd.qjtv.app.utils.FileUtils.SDPATH + address + ".JPEG";
            return intent;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void startPhotoZoom(Uri uri , Intent cameraIntent) {
        try {
            // 获取系统时间 然后将裁剪后的图片保存至指定的文件夹
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
            String address = sDateFormat.format(new Date());
            if (!com.lwd.qjtv.app.utils.FileUtils.isFileExist("")) {
                com.lwd.qjtv.app.utils.FileUtils.createSDDir("");
            }
            path = com.lwd.qjtv.app.utils.FileUtils.SDPATH + address + ".JPEG";
            activity.startActivityForResult(cameraIntent, CUT_PHOTO_REQUEST_CODE);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @NonNull
    private Intent getCameraIntent(Uri uri, String address, Uri imageUri) {
        final Intent intent = new Intent("com.android.camera.action.CROP");

        // 照片URL地址
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 480);
        intent.putExtra("outputY", 480);
        // 输出路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        // 输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 不启用人脸识别
        intent.putExtra("noFaceDetection", false);
        intent.putExtra("return-data", false);
        intent.putExtra("fileurl", com.lwd.qjtv.app.utils.FileUtils.SDPATH + address + ".JPEG");
        return intent;
    }

    public String getPath(){
        return path;
    }

    public Intent photo() {
        try {
            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String sdcardState = Environment.getExternalStorageState();
            String sdcardPathDir = Environment.getExternalStorageDirectory().getPath() + "/tempImage/";
            File file = null;
            if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                // 有sd卡，是否有myImage文件夹
                File fileDir = new File(sdcardPathDir);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                // 是否有headImg文件
                long l = System.currentTimeMillis();
                file = new File(sdcardPathDir + l + ".JPEG");
            }
            if (file != null) {
                path = file.getPath();
                photoUri = Uri.fromFile(file);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                return openCameraIntent;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Intent startPictureCollection(Context context){
        Intent i = new Intent(
                // 相册
                Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return i;
    }
}
