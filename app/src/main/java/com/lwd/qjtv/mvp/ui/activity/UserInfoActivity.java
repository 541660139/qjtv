package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jess.arms.base.App;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.photoutils.TakePicDialog;
import com.lwd.qjtv.app.utils.photoutils.TakePicUtils;
import com.lwd.qjtv.di.component.DaggerUserInfoComponent;
import com.lwd.qjtv.di.module.UserInfoModule;
import com.lwd.qjtv.mvp.contract.UserInfoContract;
import com.lwd.qjtv.mvp.presenter.UserInfoPresenter;
import com.lwd.qjtv.mvp.ui.callback.PhotoCallBack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.wefor.circularanim.CircularAnim;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class UserInfoActivity extends BaseActivity<UserInfoPresenter> implements UserInfoContract.View, TakePicDialog.PhotoCallback {

    private static final int TAKE_PICTURE = 0;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int CUT_PHOTO_REQUEST_CODE = 2;
    public String path = "";
    public Uri photoUri;
    public ImageView my_info_head;
    @BindView(R.id.activity_userinfo_head_riv)
    CircleImageView round_iv;
    @BindView(R.id.activity_userinfo_receiver_address)
    LinearLayout addressLl;
    @BindView(R.id.activity_info_avater_ll)
    LinearLayout avaterLl;
    @BindView(R.id.activity_info_name_ll)
    LinearLayout nameLL;
    @BindView(R.id.activity_userinfo_name_tv)
    TextView nameTv;
    @BindView(R.id.activity_userinfo_logout_tv)
    TextView logoutTv;
    @BindView(R.id.activity_userinfo_phone_tv)
    TextView phoneTv;
    private Map<String, String> map = new HashMap<>();
    private AlertDialog changeNameDialog;
    private EditText changeNameEdt;
    private TextView nameSureTv;
    private TextView nameCancelTv;
    private File file;

    private AppComponent appComponent;
    private ImageLoader imageLoader;

    private TakePicDialog takePicDialog;
    private TakePicUtils takePicUtils;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerUserInfoComponent
                .builder()
                .appComponent(appComponent)
                .userInfoModule(new UserInfoModule(this)) //请将UserInfoModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    public PhotoCallBack callBack = new PhotoCallBack() {
        @Override
        public void doSuccess(String path) {
            uploadPic(path);
        }

        @Override
        public void doError() {

        }
    };


    private void uploadPic(String path) {
        File file = new File(path);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartFile =
                MultipartBody.Part.createFormData("user_profile", file.getName(), imageBody);
        mPresenter.uploadPic(multipartFile);
    }

    @Override
    public int initView() {
        return R.layout.activity_userinfo;
    }

    @Override
    public void initData() {
        setTitle("用户中心");
        if (!Preference.getBoolean(Constant.IS_RELEASE)) {
            addressLl.setVisibility(View.GONE);
        }
        initListener();
        initNameView();
//        initPhoneView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Preference.getBoolean(Constant.IS_LOGIN))
            finish();
        nameTv.setText(SaveUserInfo.getUserName() == null ? "" : SaveUserInfo.getUserName());
        phoneTv.setText(SaveUserInfo.getPhone() == null ? "" : SaveUserInfo.getPhone());
        appComponent = ((App) getApplicationContext()).getAppComponent();
        imageLoader = appComponent.imageLoader();
        String avater = SaveUserInfo.getAvater();
        if (avater != null && !"".equals(avater))
            Glide.with(WEApplication.getContext()).load(SaveUserInfo.getAvater()).into(round_iv);
    }

    private void initNameView() {
        View layoutView = LayoutInflater.from(this).inflate(R.layout.dialog_change_name, null);
        changeNameDialog = new AlertDialog.Builder(this).create();
        changeNameDialog.setView(layoutView);
        changeNameEdt = (EditText) layoutView.findViewById(R.id.dialog_change_name_edt);
        nameSureTv = (TextView) layoutView.findViewById(R.id.dialog_change_name_sure_tv);
        nameCancelTv = (TextView) layoutView.findViewById(R.id.dialog_change_name_cancel_tv);
        nameSureTv.setOnClickListener(view -> {
            String s = changeNameEdt.getText().toString();
            map.put("username", s);
            mPresenter.changeName(s);
            changeNameDialog.dismiss();
        });
        nameCancelTv.setOnClickListener(view -> {
            if (changeNameDialog != null)
                changeNameDialog.dismiss();
        });
    }


    private void initListener() {
        //点击姓名
        nameLL.setOnClickListener(view -> {
            if (changeNameDialog != null)
                changeNameDialog.show();
        });

//        //点击手机号
//        phoneTv.setOnClickListener(view -> {
//            if (changePhoneDialog != null)
//                changePhoneDialog.show();
//        });

        //点击退出
        logoutTv.setOnClickListener(view -> showLogoutDialog());

        //点击头像
        avaterLl.setOnClickListener(view -> {
//            callBack = new PhotoCallBack() {
//                @Override
//                public void doSuccess(String path) {
//                    File file = new File(path);
//                    RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                    MultipartBody.Part MultipartFile =
//                            MultipartBody.Part.createFormData("user_profile", file.getName(), imageBody);
//                    //// TODO: 2017/5/13 更换头像
//                    mPresenter.uploadPic(MultipartFile);
//                }
//
//                @Override
//                public void doError() {
//
//                }
//            };

//            comfireImgSelection(WEApplication.getContext(), round_iv);

            if (takePicDialog == null) {
                takePicDialog = new TakePicDialog(this);
                takePicDialog.setPhotoCallback(this);
                takePicDialog.setResultCode(RESULT_LOAD_IMAGE);
            }
            takePicDialog.show(this);
            if (takePicUtils == null) {
                takePicUtils = new TakePicUtils(this);
            }


        });
        addressLl.setOnClickListener(view -> {
                    Intent intent = new Intent(WEApplication.getContext(), AddressManagerActivity.class);
                    intent.putExtra("isWeb", false);
                    startActivity(intent);
                }
        );
    }

    private void showLogoutDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("警告");
        alertDialog.setMessage("确定要退出账号吗？");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定", (dialogInterface, i) -> {
            CircularAnim.fullActivity(UserInfoActivity.this, logoutTv)
                    .colorOrImageRes(R.color.colorOrigin)
                    .go(() -> {
                        SaveUserInfo.logout();
                        dialogInterface.dismiss();
                        setResult(0);
                        JMessageClient.logout();
                        killMyself();
                    });

        });
        alertDialog.show();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
//        UiUtils.SnackbarText(message);
        UiUtils.makeText(this, message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void setData(Object o) {
        Message message = (Message) o;
        switch (message.what) {
            case 1:
                nameTv.setText(SaveUserInfo.getUserName() == null ? "" : SaveUserInfo.getUserName());
                break;
            case 2:
                String avater = SaveUserInfo.getAvater();
                if (avater != null && !"".equals(avater))
                    Glide.with(WEApplication.getContext()).load(SaveUserInfo.getAvater()).into(round_iv);
                break;
        }
    }


//    // 拍照
//    public void comfireImgSelection(Context context, ImageView my_info) {
//        my_info_head = my_info;
//        new AlertView(null, null, "取消", null, new String[]{"从手机相册选择", "拍照"}, this, AlertView.Style.ActionSheet,
//                (o, position) -> {
//                    // TODO Auto-generated method stub
//                    if (position == 0) {
//                        // 从相册中选择
//                        Intent i = new Intent(
//                                // 相册
//                                Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        startActivityForResult(i, RESULT_LOAD_IMAGE);
//                    } else if (position == 1) {
//                        // 拍照
//                        //判断是否开户相册权限
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                            if (ContextCompat.checkSelfPermission(this,
//                                    Manifest.permission.CAMERA)
//                                    != PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
//                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                                    != PERMISSION_GRANTED) {
//                                //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
//                                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                                        Manifest.permission.CAMERA)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
//                                    Toast.makeText(this, "权限" + "申请失败", Toast.LENGTH_SHORT).show();
//
//                                } else {
//                                    //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
//                                    ActivityCompat.requestPermissions(this,
//                                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                                }
//                            } else {
//                                photo();
//                            }
//
//                        }
//                    } else {
//                        photo();
//                    }
//                }).show();
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (takePicUtils != null)
                    file = takePicUtils.getFile();
                if (file != null && file.exists())
                    takePicUtils.startPhotoZoom(takePicUtils.getPhotoUri(), this, CUT_PHOTO_REQUEST_CODE);
                break;
            case RESULT_LOAD_IMAGE:
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        takePicUtils.startPhotoZoom(uri, this, CUT_PHOTO_REQUEST_CODE);
                    }
                }
                break;
            case CUT_PHOTO_REQUEST_CODE:
                if (takePicUtils != null) {
                    file = takePicUtils.getFile();
                    path = takePicUtils.getPath();
                }
                if (resultCode == RESULT_OK && null != data) {// 裁剪返回
                    if (path != null && path.length() != 0) {
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        round_iv.setImageBitmap(bitmap);
                        if (callBack != null)
                            callBack.doSuccess(path);
                    }
                }
                break;
        }
    }

//    private void startPhotoZoom(Uri uri) {
//        try {
//            // 获取系统时间 然后将裁剪后的图片保存至指定的文件夹
//            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
//            String address = sDateFormat.format(new Date());
//            if (!FileUtils.isFileExist("")) {
//                FileUtils.createSDDir("");
//            }
//
//            Uri imageUri = Uri.parse("file:///sdcard/formats/" + address + ".JPEG");
//            final Intent intent = new Intent("com.android.camera.action.CROP");
//
//            // 照片URL地址
//            intent.setDataAndType(uri, "image/*");
//            intent.putExtra("crop", "true");
//            intent.putExtra("aspectX", 1);
//            intent.putExtra("aspectY", 1);
//            intent.putExtra("outputX", 480);
//            intent.putExtra("outputY", 480);
//            // 输出路径
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//            // 输出格式
//            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//            // 不启用人脸识别
//            intent.putExtra("noFaceDetection", false);
//            intent.putExtra("return-data", false);
//            intent.putExtra("fileurl", FileUtils.SDPATH + address + ".JPEG");
//            path = FileUtils.SDPATH + address + ".JPEG";
//            startActivityForResult(intent, CUT_PHOTO_REQUEST_CODE);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }


//    public void photo() {
//        try {
//            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            String sdcardState = Environment.getExternalStorageState();
//            String sdcardPathDir = Environment.getExternalStorageDirectory().getPath() + "/tempImage/";
//            file = null;
//            if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
//                // 有sd卡，是否有myImage文件夹
//                File fileDir = new File(sdcardPathDir);
//                if (!fileDir.exists()) {
//                    fileDir.mkdirs();
//                }
//                // 是否有headImg文件
//                long l = System.currentTimeMillis();
//                file = new File(sdcardPathDir + l + ".JPEG");
//            }
//            if (file != null) {
//                path = file.getPath();
//
//                photoUri = ProviderSevenUriUtils.getUriForFile(this, file);
////                photoUri = Uri.fromFile(file);
//                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                startActivityForResult(openCameraIntent, TAKE_PICTURE);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                photo();
            } else {
                Toast.makeText(this, "" + "权限" + permissions[0] + "申请失败", Toast.LENGTH_SHORT).show();
            }

        }
    }


    @Override
    public void photo() {
        takePicUtils.photo(this, TAKE_PICTURE);
    }
}


