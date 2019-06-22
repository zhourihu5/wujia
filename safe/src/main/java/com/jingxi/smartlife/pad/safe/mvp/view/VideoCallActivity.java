package com.jingxi.smartlife.pad.safe.mvp.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.intercom.sdk.IntercomConstants;
import com.jingxi.smartlife.pad.safe.R;
import com.jingxi.smartlife.pad.sdk.JXPadSdk;
import com.jingxi.smartlife.pad.sdk.doorAccess.DoorAccessManager;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorEvent;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessConversationUI;
import com.wujia.businesslib.dialog.LoadingDialog;
import com.wujia.lib_common.base.BaseActivity;
import com.wujia.lib_common.utils.LogUtil;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-21
 * description ：
 */
public class VideoCallActivity extends BaseActivity implements View.OnClickListener, SurfaceHolder.Callback, DoorAccessConversationUI {

    private String sessionId;
    private SurfaceView surfaceView;
    private DoorAccessManager manager;
    private LoadingDialog loadingDialog;
    private View frore;
    private Runnable runnable;
    private ImageButton btnCall;
    private boolean btnCallFlag;


    @Override
    protected int getLayout() {
        return R.layout.activity_video_call;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        findViewById(R.id.btn1).setOnClickListener(this);

        surfaceView = findViewById(R.id.surface);
        frore = findViewById(R.id.surface_foreground);

        manager = JXPadSdk.getDoorAccessManager();
        manager.addConversationUIListener(this);

        btnCall = findViewById(R.id.btn3);
        btnCall.setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);

        sessionId = getIntent().getStringExtra("sessionId");
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(mContext);
        }
        loadingDialog.setCancelOnTouchOutside(true);
        loadingDialog.setTitle("正在连接中...");
        loadingDialog.show();

        runnable = new Runnable() {
            @Override
            public void run() {
                updateSurface();
                frore.setVisibility(View.GONE);
                if (loadingDialog != null) {
                    loadingDialog.dismiss();
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn1) {
            finish();
        } else if (v.getId() == R.id.btn3) {//接听
            if (!btnCallFlag) {
                manager.acceptCall(sessionId);
                btnCall.setBackgroundResource(R.mipmap.btn_safe_hangup);
            } else {
                finish();
            }
            btnCallFlag = !btnCallFlag;
        } else if (v.getId() == R.id.btn4) {//开门
            manager.openDoor(sessionId);
            finish();
        } else if (v.getId() == R.id.btn6) {//音量
            updateSurface();
        } else if (v.getId() == R.id.btn9) {//挂断
            finish();
        }
    }

    @Override
    public void finish() {
        manager.hangupCall(sessionId);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != runnable && null != surfaceView) {
            surfaceView.removeCallbacks(runnable);
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        updateSurface();
        surfaceView.postDelayed(runnable, 1000);
    }

    public void updateSurface() {
        manager.updateCallWindow(sessionId, surfaceView);
    }

    @Override
    public void startTransPort(String sessionID) {
        if (!TextUtils.equals(sessionId, sessionID)) {
            return;
        }
        LogUtil.i("VideoCallActivity 开始传输视频 sessionId " + sessionId);
        Toast.makeText(this, "开始传输视频", Toast.LENGTH_SHORT).show();
        updateSurface();
    }

    @Override
    public void refreshEvent(DoorEvent event) {

        if (!TextUtils.equals(sessionId, event.sessionId)) {
            return;
        }
        LogUtil.i("VideoCallActivity refreshEvent " + event.cmd + "  sessionid = " + event.sessionId);

        if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandHangup)) {
            finish();
        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandSessionTimeout)) {
            Toast.makeText(this, "超时", Toast.LENGTH_SHORT).show();
            finish();
        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandPickupByOther)) {
            Toast.makeText(this, "其他用户接听", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
