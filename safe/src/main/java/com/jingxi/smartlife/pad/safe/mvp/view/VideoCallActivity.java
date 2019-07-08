package com.jingxi.smartlife.pad.safe.mvp.view;

import android.media.AudioManager;
import android.media.SoundPool;
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
import com.wujia.businesslib.event.EventBaseButtonClick;
import com.wujia.businesslib.event.EventBusUtil;
import com.wujia.businesslib.event.IMiessageInvoke;
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

    private SoundPool mSoundPool;
    private int sampleId;
    private int mCurrentId;
    private View btn_safe_open;

    private EventBaseButtonClick eventBaseButtonClick=new EventBaseButtonClick(new IMiessageInvoke<EventBaseButtonClick>() {
        @Override
        public void eventBus(EventBaseButtonClick event) {
            if(com.intercom.sdk.IntercomConstants.kButtonUnlock.equals(event.keyCmd)){
                onClick(btn_safe_open);
            }else if(com.intercom.sdk.IntercomConstants.kButtonPickup.equals(event.keyCmd)){
                if (!btnCallFlag) {
                    onClick(btnCall);
                }
            }
        }
    });
    private boolean accepted=false;

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
        btn_safe_open= findViewById(R.id.btn4);
        btn_safe_open.setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);

        sessionId = getIntent().getStringExtra("sessionId");
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(mContext);
        }
        loadingDialog.setCancelOnTouchOutside(true);
        loadingDialog.setTitle("正在连接中...");
        loadingDialog.show();

//        runnable = new Runnable() {//todo remove it,and add SurfaceHolder.Callback
//            @Override
//            public void run() {
//                updateSurface();
//                frore.setVisibility(View.GONE);
//                if (loadingDialog != null) {
//                    loadingDialog.dismiss();
//                }
//            }
//        };

        mSoundPool = new SoundPool(1, AudioManager.STREAM_VOICE_CALL,0);
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if(!ringStoped){
                    startRing();
                }
            }
        });
        sampleId=  mSoundPool.load(mContext, R.raw.call_ring,1);

        surfaceView.getHolder().addCallback(this);
        LogUtil.i("initEventAndData");
        EventBusUtil.register(eventBaseButtonClick);
    }

    void startRing(){
        stopRing();
        mCurrentId = mSoundPool.play(sampleId, 1f, 1f, 1,  -1 , 1);
        LogUtil.i("mCurrentId=="+mCurrentId);
    }
    boolean ringStoped=false;
    private void stopRing(){
        LogUtil.i("stopRing");
        if(mCurrentId!=0){
            mSoundPool.stop(mCurrentId);
//            mCurrentId=0;
        }
        ringStoped=true;
    }
    void acceptCall(){
        if(!accepted){
            accepted=true;
            manager.acceptCall(sessionId);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn1) {
            finish();
        } else if (v.getId() == R.id.btn3) {//接听
            if (!btnCallFlag) {
                stopRing();
                acceptCall();
                btnCall.setBackgroundResource(R.mipmap.btn_safe_hangup);
            } else {
                finish();
            }
            btnCallFlag = !btnCallFlag;
        } else if (v.getId() == R.id.btn4) {//开门
            stopRing();
            acceptCall();
            manager.openDoor(sessionId);
            finish();
        } else if (v.getId() == R.id.btn6) {//refresh
            updateSurface();
        } else if (v.getId() == R.id.btn9) {//挂断
            finish();
        }
    }

    @Override
    public void finish() {
        LogUtil.i("finish");
        stopRing();
        manager.hangupCall(sessionId);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        LogUtil.i("onDestroy");
        super.onDestroy();
        if ( null != surfaceView) {
            surfaceView.removeCallbacks(runnable);
            surfaceView.getHolder().removeCallback(this);
        }
        DoorAccessManager.getInstance().removeConversationUIListener(this);
        manager.hangupCall(sessionId);
        EventBusUtil.unregister(eventBaseButtonClick);
        if(mSoundPool!=null){
            stopRing();
            mSoundPool.setOnLoadCompleteListener(null);
            try {
                mSoundPool.release();//fixme
                //06-26 20:02:31.900 10264-10272/com.jingxi.smartlife.pad E/System: Uncaught exception thrown by finalizer
//                06-26 20:02:31.901 10264-10272/com.jingxi.smartlife.pad E/System: java.lang.NullPointerException: Attempt to invoke interface method 'android.os.IBinder com.android.internal.app.IAppOpsCallback.asBinder()' on a null object reference
//                at android.os.Parcel.readException(Parcel.java:1626)
//                at android.os.Parcel.readException(Parcel.java:1573)
//                at com.android.internal.app.IAppOpsService$Stub$Proxy.stopWatchingMode(IAppOpsService.java:420)
//                at android.media.SoundPool.release(SoundPool.java:194)
//                at android.media.SoundPool.finalize(SoundPool.java:203)
//                at java.lang.Daemons$FinalizerDaemon.doFinalize(Daemons.java:202)
//                at java.lang.Daemons$FinalizerDaemon.run(Daemons.java:185)
//                at java.lang.Thread.run(Thread.java:818)
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        LogUtil.i("surfaceCreated");
        manager.updateCallWindow(sessionId, surfaceView);

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        LogUtil.i("surfaceDestroyed");
        manager.updateCallWindow(sessionId, null);
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
//        Toast.makeText(this, "开始传输视频", Toast.LENGTH_SHORT).show();
        frore.setVisibility(View.GONE);
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
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
