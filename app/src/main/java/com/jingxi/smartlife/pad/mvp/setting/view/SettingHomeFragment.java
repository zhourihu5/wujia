package com.jingxi.smartlife.pad.mvp.setting.view;

import android.app.WallpaperManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jingxi.smartlife.pad.mvp.setting.contract.SettingContract;
import com.jingxi.smartlife.pad.mvp.setting.data.VersionBean;
import com.jingxi.smartlife.pad.mvp.setting.presenter.SettingPresenter;
import com.wujia.businesslib.base.MvpFragment;
import com.wujia.businesslib.listener.OnDialogListener;
import com.jingxi.smartlife.pad.R;
import com.wujia.businesslib.dialog.LoadingDialog;
import com.jingxi.smartlife.pad.mvp.setting.contract.SettingContract;
import com.jingxi.smartlife.pad.mvp.setting.data.VersionBean;
import com.jingxi.smartlife.pad.mvp.setting.presenter.SettingPresenter;
import com.wujia.lib.widget.WjSwitch;
import com.wujia.businesslib.dialog.SimpleDialog;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.FileUtil;
import com.wujia.lib_common.utils.VersionUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：物业服务 home
 */
public class SettingHomeFragment extends MvpFragment<SettingPresenter> implements SettingContract.View {

    @BindView(R.id.layout_title_tv)
    TextView layoutTitleTv;
    @BindView(R.id.layout_back_btn)
    TextView layoutBackBtn;
    @BindView(R.id.item_set_member)
    RelativeLayout itemSetMember;
    @BindView(R.id.item_manager_card)
    RelativeLayout itemManagerCard;
    @BindView(R.id.item_set_lock_pic)
    RelativeLayout itemSetLockPic;
    @BindView(R.id.item_wifi_connection)
    RelativeLayout itemWifiConnection;
    @BindView(R.id.item_allow_look_door_num_switch)
    WjSwitch itemAllowLookDoorNumSwitch;
    @BindView(R.id.item_allow_look_door_num)
    LinearLayout itemAllowLookDoorNum;
    @BindView(R.id.item_clear_cache)
    LinearLayout itemClearCache;
    @BindView(R.id.item_check_update)
    RelativeLayout itemCheckUpdate;
    private LoadingDialog loadingDialog;

    public SettingHomeFragment() {
    }

    public static SettingHomeFragment newInstance() {
        SettingHomeFragment fragment = new SettingHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting_home;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用

        layoutTitleTv.setText(R.string.setting);

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！

    }

    @OnClick({R.id.item_set_member, R.id.item_manager_card, R.id.item_set_lock_pic, R.id.item_wifi_connection, R.id.item_allow_look_door_num, R.id.item_clear_cache, R.id.item_check_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_set_member:

                start(FamilyMemberFragment.newInstance());
                break;
            case R.id.item_manager_card:
                startForResult(CardManagerFragment.newInstance(), CardManagerFragment.REQUEST_CODE_CARD_MANAGER);
                break;
            case R.id.item_set_lock_pic:
//                startActivity(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getActivity());
                try {
                    wallpaperManager.setResource(R.raw.bg_lockscreen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.item_wifi_connection:
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                break;
            case R.id.item_allow_look_door_num:
                itemAllowLookDoorNumSwitch.toggle();
                break;
            case R.id.item_clear_cache:
                new SimpleDialog.Builder().title(getString(R.string.clear_cache)).listener(new OnDialogListener() {
                    @Override
                    public void dialogSureClick() {
                        ToastUtil.showShort(mContext, getString(R.string.cache_clear_ed));
                        FileUtil.deleteFile(FileUtil.getDowndloadApkPath(mContext));
                    }
                }).build(mContext).show();
                break;
            case R.id.item_check_update:

                loadingDialog = new LoadingDialog(mContext);
                loadingDialog.setTitle(getString(R.string.check_update_ing));
                loadingDialog.show();

                mPresenter.checkVersion();
                break;
        }
    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter();
    }

    @Override
    public void onDataLoadSucc(int requestCode, Object object) {

        VersionBean bean = (VersionBean) object;
        ArrayList<VersionBean.Version> list = bean.infoList;

//        String pname = AppContext.get().getPackageName();
        String pname = "com.jingxi.smartlife.pad";
        int versionId = VersionUtil.getVersionCode();

        for (VersionBean.Version v : list) {
            if (pname.equals(v.packageName)) {
                if (v.versionId > versionId) {

                    start(UpdateFragment.newInstance(v, bean.remark));

                    break;
                }
            }
        }

        if (null != loadingDialog) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    @Override
    public void onDataLoadFailed(int requestCode, ApiException apiException) {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
}
