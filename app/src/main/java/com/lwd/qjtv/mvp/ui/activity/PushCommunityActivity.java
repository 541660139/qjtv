package com.lwd.qjtv.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.photoutils.TakePicDialog;
import com.lwd.qjtv.app.utils.photoutils.TakePicUtils;
import com.lwd.qjtv.di.component.DaggerPushCommunityComponent;
import com.lwd.qjtv.di.module.PushCommunityModule;
import com.lwd.qjtv.mvp.contract.PushCommunityContract;
import com.lwd.qjtv.mvp.presenter.PushCommunityPresenter;
import com.lwd.qjtv.mvp.ui.adapter.PhotoPickerAdapter;
import com.lwd.qjtv.mvp.ui.callback.PhotoCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class PushCommunityActivity extends BaseActivity<PushCommunityPresenter> implements PushCommunityContract.View, PhotoPickerAdapter.OnDeleteCallback, PhotoPickerAdapter.OnRecyclerViewItemClickListener, TakePicDialog.PhotoCallback {

    @BindView(R.id.activity_push_community_back_tv)
    TextView activityPushCommunityBackTv;
    @BindView(R.id.activity_push_community_push_tv)
    TextView activityPushCommunityPushTv;
    @BindView(R.id.activity_push_community_title_edt)
    EditText activityPushCommunityTitleEdt;
    @BindView(R.id.activity_push_community_content_edt)
    EditText activityPushCommunityContentEdt;
    @BindView(R.id.activity_push_community_text_num_tv)
    TextView activityPushCommunityTextNumTv;
    @BindView(R.id.activity_push_community_num_tv)
    TextView activityPushCommunityNumTv;
    @BindView(R.id.activity_push_community_recyclerview)
    RecyclerView activityPushCommunityRecyclerview;
    private PhotoPickerAdapter photoPickerAdapter;
    private final int MAXNUM = 100;

    private List<Bitmap> bitmaps = new ArrayList<>();
    private List<String> picUrls = new ArrayList<>();
    private String pic;
    private int type;
    public static final int TAKE_PICTURE = 0;
    public static final int RESULT_LOAD_IMAGE = 1;
    public static final int CUT_PHOTO_REQUEST_CODE = 2;
    private TakePicDialog takePicDialog;
    private TakePicUtils takePicUtils;
    private File file;
    private String path;
    public PhotoCallBack callBack = new PhotoCallBack() {
        @Override
        public void doSuccess(String path) {
            uploadPic(path);
        }

        @Override
        public void doError() {

        }
    };
    //    private String vid;
    private boolean is_edit;
    private ArrayList<String> pic_url_list;
    private String editorTitle;
    private String editorContent;

    private void uploadPic(String path) {
        File file = new File(path);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartFile =
                MultipartBody.Part.createFormData("user_profile", file.getName(), imageBody);
        mPresenter.uploadPic(multipartFile);
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerPushCommunityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .pushCommunityModule(new PushCommunityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_push_community; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData() {
        initRecyclerView();
        initListener();
//        vid = getIntent().getStringExtra("vid");
        is_edit = getIntent().getBooleanExtra("is_edit", false);
        if (is_edit) {
            initEditorAgain();
        }

    }

    private void initEditorAgain() {
        editorTitle = getIntent().getStringExtra("title");
        editorContent = getIntent().getStringExtra("content");
        pic_url_list = getIntent().getStringArrayListExtra("pic_url_list");
        if (editorTitle != null)
            activityPushCommunityTitleEdt.setText(editorTitle);

        if (editorContent != null)
            activityPushCommunityContentEdt.setText(editorContent);

        if (pic_url_list != null && pic_url_list.size() > 0)
            mPresenter.GetImageBitmapsForUrls(pic_url_list);
    }

    private void initListener() {
        activityPushCommunityBackTv.setOnClickListener(v -> finish());
        activityPushCommunityPushTv.setOnClickListener(v -> push());
        activityPushCommunityTitleEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                activityPushCommunityTextNumTv.setText(length + "/30");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void push() {
        String title = activityPushCommunityTitleEdt.getText().toString();
        String content = activityPushCommunityContentEdt.getText().toString();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < picUrls.size(); i++) {
            if ((picUrls.size() - 1) == i)
                str.append(picUrls.get(i));
            else
                str.append(picUrls.get(i) + ",");
        }
        pic = str.toString();
//        if (vid == null) {
//            showMessage("vid不能为空");
//            return;
//        }
        if (title == null || title.equals("")) {
            showMessage("标题不能为空");
            return;
        }
        if (content == null || content.equals("")) {
            showMessage("内容不能为空");
            return;
        }
        hideSoftWindow(activityPushCommunityContentEdt);
        mPresenter.pushCommunity(title, content, pic, is_edit);
    }

    private void initRecyclerView() {
        photoPickerAdapter = new PhotoPickerAdapter(this);
        photoPickerAdapter.setDeleteCallback(this);
        photoPickerAdapter.setOnItemClickListener(this);
        photoPickerAdapter.setMAX_NUM(MAXNUM);
        activityPushCommunityRecyclerview.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        activityPushCommunityRecyclerview.setAdapter(photoPickerAdapter);
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
        UiUtils.SnackbarText(message);
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
        picUrls.add((String) o);
        activityPushCommunityNumTv.setText(picUrls.size() + "/" + MAXNUM);
    }

    @Override
    public void onDeleteItem(int position) {
        if (bitmaps != null && bitmaps.size() > 0) {
            bitmaps.remove(position);
            if (picUrls != null && picUrls.size() > 0)
                picUrls.remove(position);
            if (photoPickerAdapter != null) {
                photoPickerAdapter.setImageBitmap(bitmaps);
                activityPushCommunityNumTv.setText(picUrls.size() + "/" + MAXNUM);
            }
        }
    }

    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {
        if (takePicDialog == null) {
            takePicDialog = new TakePicDialog(this);
            takePicDialog.setPhotoCallback(this);
            takePicDialog.setResultCode(RESULT_LOAD_IMAGE);
        }
        takePicDialog.show(this);
        if (takePicUtils == null) {
            takePicUtils = new TakePicUtils(this);
        }
    }

    @Override
    public void photo() {
        takePicUtils.photo(this, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                        bitmaps.add(bitmap);
                        photoPickerAdapter.setImageBitmap(bitmaps);
                        if (callBack != null)
                            callBack.doSuccess(path);
                    }
                }
                break;
        }
    }


    @Override
    public void loadImage(List<Bitmap> list) {
        bitmaps.clear();
        bitmaps.addAll(list);
        photoPickerAdapter.setImageBitmap(bitmaps);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideSoftWindow(activityPushCommunityContentEdt);
    }


    private void hideSoftWindow(View view) {
        InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (view != null) {
            boolean var = im.isActive();
            if (var) {
                im.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
