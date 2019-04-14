package com.jingxi.smartlife.pad.safe.mvp.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.intercom.sdk.IntercomConstants;
import com.jingxi.smartlife.pad.safe.R;
import com.jingxi.smartlife.pad.sdk.JXPadSdk;
import com.jingxi.smartlife.pad.sdk.doorAccess.DoorAccessManager;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorEvent;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessConversationUI;
import com.wujia.businesslib.Constants;
import com.wujia.lib_common.base.BaseActivity;

public class FullScreenLiveActivity extends BaseActivity implements View.OnClickListener, SurfaceHolder.Callback, DoorAccessConversationUI {

    private SurfaceView surfaceView;
    private DoorAccessManager manager;
    private String sessionId;

    @Override
    protected int getLayout() {
        return R.layout.activity_safe_live_full;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

        surfaceView = findViewById(R.id.sv);
        findViewById(R.id.img1).setOnClickListener(this);
        findViewById(R.id.img2).setOnClickListener(this);
        findViewById(R.id.img3).setOnClickListener(this);

        manager = JXPadSdk.getDoorAccessManager();
        manager.addConversationUIListener(this);
        sessionId = getIntent().getStringExtra(Constants.INTENT_KEY_1);

        surfaceView.postDelayed(new Runnable() {
            @Override
            public void run() {
                manager.updateCallWindow(sessionId, surfaceView);
            }
        },500);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img1) {
            setResult(200);
            finish();
        } else if (v.getId() == R.id.img2) {//音量
        } else if (v.getId() == R.id.img3) {//刷新
            manager.updateCallWindow(sessionId, surfaceView);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        manager.updateCallWindow(sessionId, surfaceView);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        manager.updateCallWindow(sessionId, null);
    }

    @Override
    public void startTransPort(String sessionID) {
        if (!TextUtils.equals(sessionId, sessionID)) {
            return;
        }
        Toast.makeText(this, "开始传输视频", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshEvent(DoorEvent event) {

        if (!TextUtils.equals(sessionId, event.sessionId)) {
            return;
        }
        if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandHangup)) {
            finish();
        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandSessionTimeout)) {
            finish();
        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandPickupByOther)) {
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DoorAccessManager.getInstance().removeConversationUIListener(this);
    }
}
