package com.lwd.qjtv.mvp.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.di.component.DaggerMineNewComponent;
import com.lwd.qjtv.di.module.MineNewModule;
import com.lwd.qjtv.mvp.contract.MineNewContract;
import com.lwd.qjtv.mvp.model.entity.UserInfro;
import com.lwd.qjtv.mvp.presenter.MineNewPresenter;
import com.lwd.qjtv.mvp.ui.activity.CollectionActivity;
import com.lwd.qjtv.mvp.ui.activity.GuessCenterActivity;
import com.lwd.qjtv.mvp.ui.activity.LoginActivity;
import com.lwd.qjtv.mvp.ui.activity.MineFActivity;
import com.lwd.qjtv.mvp.ui.activity.MinePublisActivity;
import com.lwd.qjtv.mvp.ui.activity.RechargeActivity;
import com.lwd.qjtv.mvp.ui.activity.SettingActivity;
import com.lwd.qjtv.mvp.ui.activity.UserInfoActivity;
import com.lwd.qjtv.mvp.ui.activity.WatchRecordeActivity;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MineNewFragment extends BaseFragment<MineNewPresenter> implements MineNewContract.View {
    @BindView(R.id.circle_avater)
    CircleImageView circle_avater;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.shezhi)
    ImageView shezhi;
    @BindView(R.id.tv_gz_number)
    TextView tv_gz_number;
    @BindView(R.id.tv_fs_number)
    TextView tv_fs_number;
    @BindView(R.id.tv_fb_number)
    TextView tv_fb_number;
    @BindView(R.id.fragment_mine_guess_ll)
    LinearLayout fragment_mine_guess_ll;

    @BindView(R.id.fragment_mine_qianbao_ll)
    LinearLayout fragment_mine_qianbao_ll;

    @BindView(R.id.fragment_mine_sc_ll)
    LinearLayout fragment_mine_sc_ll;

    @BindView(R.id.fragment_mine_bf_ll)
    LinearLayout fragment_mine_bf_ll;
    @BindView(R.id.fragment_mine_lx_ll)
    LinearLayout fragment_mine_lx_ll;


    @BindView(R.id.rl_gz)
    RelativeLayout rl_gz;


    @BindView(R.id.rl_fs)
    RelativeLayout rl_fs;

    @BindView(R.id.rl_fb)
    RelativeLayout rl_fb;


    @BindView(R.id.rl_mine_top)
    RelativeLayout rl_mine_top;

    //客服qq
    private String keFuQQ = "1152144009";


    public static MineNewFragment newInstance() {
        MineNewFragment fragment = new MineNewFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMineNewComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mineNewModule(new MineNewModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_mine_new, container, false);
    }

    @Override
    public void initData() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int bannerWidth = dm.widthPixels;
        int bannerHeight = bannerWidth * 145 / 375;

        ViewGroup.LayoutParams layoutParams = rl_mine_top.getLayoutParams();
        layoutParams.height = bannerHeight;
        if (Preference.get(Constant.IS_LOGIN, false)) {
            mPresenter.getPersonalCenter();
        }

    }


    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {
        if (data != null) {
            UserInfro data1 = (UserInfro) data;
            if (data1.getStatus().equals("1")) {

                if (data1.getData().getAvater() != null && !TextUtils.isEmpty(data1.getData().getAvater()))
                    Glide.with(getContext()).load(data1.getData().getAvater()).placeholder(R.mipmap.video_place_holder).into(circle_avater);

                if (data1.getData().getUsername() != null && !TextUtils.isEmpty(data1.getData().getUsername()))
                    tv_name.setText(data1.getData().getUsername());

                if (data1.getData().getFan_count() != null && !TextUtils.isEmpty(data1.getData().getFan_count()))
                    tv_fs_number.setText(data1.getData().getFan_count());
                if (data1.getData().getFollow_count() != null && !TextUtils.isEmpty(data1.getData().getFollow_count()))
                    tv_gz_number.setText(data1.getData().getFollow_count());
                if (data1.getData().getMy_publish_count() != null && !TextUtils.isEmpty(data1.getData().getMy_publish_count()))
                    tv_fb_number.setText(data1.getData().getMy_publish_count());
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getPersonalCenter();

        if (!Preference.get(Constant.IS_LOGIN, false)) {
            tv_name.setText("未登录");
            circle_avater.setImageResource(R.mipmap.video_place_holder);
        }

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

    }


    @OnClick({R.id.shezhi, R.id.circle_avater, R.id.fragment_mine_guess_ll, R.id.fragment_mine_qianbao_ll,
            R.id.fragment_mine_sc_ll
            , R.id.fragment_mine_bf_ll, R.id.fragment_mine_lx_ll, R.id.rl_gz, R.id.rl_fs, R.id.rl_fb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shezhi:
//                设置
                startActivity(new Intent(WEApplication.getContext(), SettingActivity.class));
                break;
            case R.id.circle_avater:
//                头像

                if (!SaveUserInfo.getLogin())
                    startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                else
                    startActivity(new Intent(WEApplication.getContext(), UserInfoActivity.class));

                break;
            case R.id.fragment_mine_guess_ll:

                if (!SaveUserInfo.getLogin())
                    startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                else
                    startActivity(new Intent(WEApplication.getContext(), GuessCenterActivity.class));
                break;
            case R.id.fragment_mine_qianbao_ll:
                if (!SaveUserInfo.getLogin())
                    startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                else
                    startActivity(new Intent(WEApplication.getContext(), RechargeActivity.class));

                break;
            case R.id.fragment_mine_sc_ll:
                if (!SaveUserInfo.getLogin())
                    startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                else
                    startActivity(new Intent(WEApplication.getContext(), CollectionActivity.class));

                break;
            case R.id.fragment_mine_bf_ll:
                if (!SaveUserInfo.getLogin())
                    startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                else
                    startActivity(new Intent(WEApplication.getContext(), WatchRecordeActivity.class));

                break;
            case R.id.fragment_mine_lx_ll:
                if (SaveUserInfo.getLogin()) {
                    if (Utils.isQQClientAvailable(getContext())) {
                        final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=" + keFuQQ + "&version=1";
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
                    } else {
                        UiUtils.SnackbarText("您还没有安装QQ");
                    }

                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.rl_gz:
//                关注

                Intent intent = new Intent(getContext(), MineFActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.rl_fs:
//                粉丝

                intent = new Intent(getContext(), MineFActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
            case R.id.rl_fb:
//                发布
                intent = new Intent(getContext(), MinePublisActivity.class);
                startActivity(intent);
                break;

            default:

        }

    }
}
