package com.wujia.intellect.terminal.safe.mvp.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.intercom.sdk.IntercomConstants;
import com.jingxi.smartlife.pad.sdk.JXPadSdk;
import com.jingxi.smartlife.pad.sdk.doorAccess.DoorAccessManager;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorEvent;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessConversationUI;
import com.wujia.intellect.terminal.safe.R;
import com.wujia.lib_common.base.BaseActivity;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-21
 * description ：
 */
public class VideoCallActivity extends BaseActivity implements View.OnClickListener, SurfaceHolder.Callback, DoorAccessConversationUI {

    private String sessionId;
    private SurfaceView surfaceView;
    private DoorAccessManager manager;

    @Override
    protected int getLayout() {
        return R.layout.activity_video_call;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        findViewById(R.id.btn1).setOnClickListener(this);

        surfaceView = findViewById(R.id.surface);

        manager = JXPadSdk.getDoorAccessManager();
        manager.addConversationUIListener(this);

        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);

        sessionId = getIntent().getStringExtra("sessionId");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn1) {
            finish();
        }else if(v.getId() == R.id.btn3){//接听

        }else if(v.getId() == R.id.btn4){//开门

        }else if(v.getId() == R.id.btn6){//音量

        }else if(v.getId() == R.id.btn9){//挂断

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DoorAccessManager.getInstance().removeConversationUIListener(this);
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

    public void accept(View v) {
        manager.acceptCall(sessionId);
    }

    public void hangup(View v) {
        manager.hangupCall(sessionId);
        finish();
    }

    public void updateSurface(View v) {
        manager.updateCallWindow(sessionId, surfaceView);
    }

    @Override
    public void startTransPort(String sessionID) {
        Toast.makeText(this, "开始传输视频", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshEvent(DoorEvent event) {
        if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandHangup)) {
            manager.hangupCall(sessionId);
            finish();
        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandSessionTimeout)) {
            Toast.makeText(this, "超时", Toast.LENGTH_SHORT).show();
            manager.hangupCall(sessionId);
            finish();
        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandPickupByOther)) {
            Toast.makeText(this, "其他用户接听", Toast.LENGTH_SHORT).show();
            manager.hangupCall(sessionId);
            finish();
        }

    }
}
