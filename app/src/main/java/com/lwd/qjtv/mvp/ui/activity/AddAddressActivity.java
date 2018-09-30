package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.EditCheckUtil;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SoftInputUtils;
import com.lwd.qjtv.app.utils.ToastUtil;
import com.lwd.qjtv.di.component.DaggerAddAddressComponent;
import com.lwd.qjtv.di.module.AddAddressModule;
import com.lwd.qjtv.mvp.contract.AddAddressContract;
import com.lwd.qjtv.mvp.presenter.AddAddressPresenter;
import com.lwd.qjtv.view.ChangeAddressPopwindow;


import butterknife.BindView;

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
 * Created by ZhengQian on 2017/5/18.
 */

public class AddAddressActivity extends BaseActivity<AddAddressPresenter> implements AddAddressContract.View {

    //保存按钮
    @BindView(R.id.put_address_save_tv)
    TextView saveTv;
    //姓名编辑框
    @BindView(R.id.address_name_edt)
    EditText nameEdt;
    //电话编辑框
    @BindView(R.id.address_phone_edt)
    EditText phoneEdt;
    //地址编辑框
    @BindView(R.id.address_address_edt)
    TextView addressEdt;
    //详细地址编辑框
    @BindView(R.id.address_details_address_edt)
    EditText detailsAddressEdt;
    //默认地址
    @BindView(R.id.activity_addaddress_cb)
    CheckBox addAddressCb;

    //是否是默认地址
    private boolean is_default;
    private String name;
    private String phone;
    private String address;
    private String detailsAddress;
    private String id = "0";

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerAddAddressComponent
                .builder()
                .appComponent(appComponent)
                .addAddressModule(new AddAddressModule(this)) //请将AddAddressModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_addaddress;
    }


    @Override
    public void initData() {
        setTitle("添加新地址");
        getParseIntent();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Preference.getBoolean(Constant.IS_LOGIN))
            finish();
    }

    //获取传递的intent值
    private void getParseIntent() {
        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            phone = getIntent().getStringExtra("phone");
            address = getIntent().getStringExtra("address");
            detailsAddress = getIntent().getStringExtra("details_addresss");
            is_default = "1".equals(getIntent().getStringExtra("is_default"));
            nameEdt.setText(name);
            phoneEdt.setText(phone);
            addressEdt.setText(address);
            detailsAddressEdt.setText(detailsAddress);
            addAddressCb.setChecked(is_default);
        }
    }

    private void initListener() {
        saveTv.setOnClickListener(view -> {
            //检查信息是否为空
            boolean b = checkInfoIsEmpty();
            if (!b)
                return;
            //获取是否勾选默认地址
            String isDefault = addAddressCb.isChecked() ? "1" : "0";
            //判断是否是新增地址
            if (id == null || "0".equals(id))
                mPresenter.addAddress(name, phone, detailsAddress, address, isDefault);
            else
                mPresenter.changeAddress(name, phone, detailsAddress, address, isDefault, id);
        });
        addressEdt.setOnClickListener(view -> {
            ChangeAddressPopwindow mChangeAddressPopwindow = new ChangeAddressPopwindow(this);
            //收起键盘
            SoftInputUtils.hideSoftInput(view, this);
            //设置初始默认地址
            mChangeAddressPopwindow.setAddress("广东", "深圳", "福田区");
            mChangeAddressPopwindow.showAtLocation(addressEdt, Gravity.BOTTOM, 0, 0);
            mChangeAddressPopwindow
                    .setAddresskListener((province, city, area) -> {
                                String place = province + " " + city + " " + area;
                                addressEdt.setText(place);
                            }
                    );
        });
    }

    private boolean checkInfoIsEmpty() {
        name = nameEdt.getText().toString().trim();
        address = addressEdt.getText().toString().trim();
        detailsAddress = detailsAddressEdt.getText().toString().trim();
        phone = phoneEdt.getText().toString().trim();
        if (name == null || "".equals(name) || name.equals("请输入收货人姓名")) {
            showMessage("姓名不能为空");
            return false;
        }
        if (address == null || "".equals(address) || address.equals("请选择收货人地区")) {
            showMessage("地区不能为空");
            return false;
        }
        if (detailsAddress == null || "".equals(detailsAddress) || detailsAddress.equals("请填写收货人详细地址")) {
            showMessage("详细地址不能为空");
            return false;
        }
        if (phone == null || "".equals(phone) || phone.equals("请输入收货人电话")) {
            showMessage("电话不能为空");
            return false;
        }
        if (!EditCheckUtil.isMobileNO(phone)) {
            showMessage("请填写正确的手机号");
            return false;
        }
        return true;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(WEApplication.getContext(), message);
//        checkNotNull(message);
//        UiUtils.SnackbarText(message);
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

    }


}
