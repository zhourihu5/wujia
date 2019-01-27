package com.wujia.intellect.terminal.mvp.setting.view;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wujia.businesslib.TitleFragment;
import com.wujia.intellect.terminal.R;
import com.wujia.lib.widget.LoadingDialog;
import com.wujia.lib.widget.MySwitch;
import com.wujia.lib.widget.SimpleDialog;
import com.wujia.lib.widget.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：务业服务 home
 */
public class SettingHomeFragment extends TitleFragment {

    @BindView(R.id.item_set_member)
    RelativeLayout itemSetMember;
    @BindView(R.id.item_manager_card)
    RelativeLayout itemManagerCard;
    @BindView(R.id.item_set_lock_pic)
    RelativeLayout itemSetLockPic;
    @BindView(R.id.item_wifi_connection)
    RelativeLayout itemWifiConnection;
    @BindView(R.id.item_allow_look_door_num_switch)
    MySwitch itemAllowLookDoorNumSwitch;
    @BindView(R.id.item_allow_look_door_num)
    LinearLayout itemAllowLookDoorNum;
    @BindView(R.id.item_clear_cache)
    LinearLayout itemClearCache;
    @BindView(R.id.item_check_update)
    RelativeLayout itemCheckUpdate;

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
    public int getTitle() {
        return R.string.setting;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用

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
                break;
            case R.id.item_set_lock_pic:
                startActivity(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
                break;
            case R.id.item_wifi_connection:
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                break;
            case R.id.item_allow_look_door_num:
                itemAllowLookDoorNumSwitch.toggle();
                break;
            case R.id.item_clear_cache:
                new SimpleDialog.Builder().title(getString(R.string.clear_cache)).listener(new SimpleDialog.OnInputDialogListener() {
                    @Override
                    public void dialogSureClick() {
                        ToastUtil.showShort(mContext, getString(R.string.cache_clear_ed));
                    }
                }).build(mContext).show();
                break;
            case R.id.item_check_update:

                final LoadingDialog loadingDialog = new LoadingDialog(mContext);
                loadingDialog.setTitle(getString(R.string.check_update_ing));
                loadingDialog.show();

                itemCheckUpdate.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismiss();
                        start(UpdateFragment.newInstance());
                    }
                }, 2000);


                break;
        }
    }
}
