package com.lwd.qjtv.app.utils.photoutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.example.provider7.ProviderSevenUriUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZhengQian on 2018/1/18.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class TakePicUtils {
    private File file;
    private String path;
    private Context context;
    private Uri photoUri;
    private int outputx = 200; //输出大小 x
    private int outputy = 200; //输出大小 y
    private int aspectx = 1;   //输出比例 x
    private int aspecty = 1;   //输出比例 y
    private String fileAddress;

    public TakePicUtils(Context context) {
        this.context = context;
    }

    public void photo(Activity activity, int takePicCode) {
        try {
            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String sdcardState = Environment.getExternalStorageState();
            String sdcardPathDir = Environment.getExternalStorageDirectory().getPath() + "/tempImage/";
            file = null;
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

                photoUri = ProviderSevenUriUtils.getUriForFile(context, file);
                //photoUri = Uri.fromFile(file);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                activity.startActivityForResult(openCameraIntent, takePicCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public void setAspectX(int aspectX) {
        this.aspectx = aspectX;
    }

    public void setAspectY(int aspectY) {
        this.aspecty = aspectY;
    }

    public void setOutputX(int outputX) {
        this.outputx = outputX;
    }

    public void setOutputY(int outputY) {
        this.outputy = outputY;
    }

    public void startPhotoZoom(Uri uri, Activity activity, int photoZoomCode) {
        try {
            // 获取系统时间 然后将裁剪后的图片保存至指定的文件夹
            if (fileAddress == null || fileAddress.equals("")) {
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                fileAddress = sDateFormat.format(new Date());
            }
            if (!FileUtils.isFileExist("")) {
                FileUtils.createSDDir("");
            }

            Uri imageUri = Uri.parse("file:///sdcard/formats/" + fileAddress + ".JPEG");
            final Intent intent = new Intent("com.android.camera.action.CROP");

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // 照片URL地址
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", aspectx);
            intent.putExtra("aspectY", aspecty);
            intent.putExtra("outputX", outputx);
            intent.putExtra("outputY", outputy);
            // 输出路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            // 输出格式
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            // 不启用人脸识别
            intent.putExtra("noFaceDetection", false);
            intent.putExtra("return-data", false);
            intent.putExtra("fileurl", FileUtils.SDPATH + fileAddress + ".JPEG");
            path = FileUtils.SDPATH + fileAddress + ".JPEG";
            activity.startActivityForResult(intent, photoZoomCode);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public File getFile() {
        return file;
    }

    public String getPath() {
        return path;
    }

}
