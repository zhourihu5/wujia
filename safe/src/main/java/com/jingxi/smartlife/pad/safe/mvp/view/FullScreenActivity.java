package com.jingxi.smartlife.pad.safe.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.intercom.sdk.IntercomConstants;
import com.intercom.sdk.IntercomObserver;
import com.jingxi.smartlife.pad.safe.R;
import com.jingxi.smartlife.pad.sdk.JXPadSdk;
import com.jingxi.smartlife.pad.sdk.doorAccess.DoorAccessManager;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorDevice;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorEvent;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessConversationUI;
import com.wujia.businesslib.base.DataManager;
import com.wujia.businesslib.dialog.LoadingDialog;
import com.wujia.businesslib.util.LoginUtil;
import com.wujia.lib_common.base.BaseActivity;
import com.wujia.lib_common.base.Constants;
import com.wujia.lib_common.utils.LogUtil;

import java.util.List;

public class FullScreenActivity extends BaseActivity implements View.OnClickListener, SurfaceHolder.Callback, DoorAccessConversationUI, SeekBar.OnSeekBarChangeListener, IntercomObserver.OnPlaybackListener {

    private SurfaceView surfaceView;
    private DoorAccessManager mDoorAccessManager;
    private String mSessionId;
    private ImageView btnVol, btnPlay, btnRefresh;
    private TextView tvPlaybackCurrentTime, tvPlaybackCountTime;
    private SeekBar seekBar;
    private boolean isPause, isPlayback;
    private LinearLayout layoutLive, layoutPlaybackl;
    private int seek;
    private int max;
    private final long unit = 1000 * 1000;

    //是否在滑动进度条
    private boolean isTouchSeek = false;
    private boolean isSeeeionIdValid;
    private LoadingDialog loadingDialog;
    private String familyID;


    @Override
    protected int getLayout() {
        return R.layout.activity_safe_full;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

        layoutPlaybackl = findViewById(R.id.l1);
        layoutLive = findViewById(R.id.l2);

        surfaceView = findViewById(R.id.sv);
        btnVol = findViewById(R.id.btn2);
        btnRefresh = findViewById(R.id.btn9);

        findViewById(R.id.btn1).setOnClickListener(this);
        btnVol.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);

        mDoorAccessManager = JXPadSdk.getDoorAccessManager();
        mSessionId = getIntent().getStringExtra(Constants.INTENT_KEY_1);
        isPlayback = getIntent().getBooleanExtra(Constants.INTENT_KEY_2, false);

        if (isPlayback) {
            layoutPlaybackl.setVisibility(View.VISIBLE);
            seekBar = $(R.id.safe_seekbar);
            btnPlay = findViewById(R.id.btn3);

            tvPlaybackCurrentTime = $(R.id.safe_progress_time_current_tv);
            tvPlaybackCountTime = $(R.id.safe_progress_time_count_tv);

            btnPlay.setOnClickListener(this);
            mDoorAccessManager.addPlayBackListener(this);

            max = getIntent().getIntExtra(Constants.INTENT_KEY_3, 0);
            if (max > 0) {
                seekBar.setOnSeekBarChangeListener(this);
                seekBar.setMax(max);
                seek = getIntent().getIntExtra(Constants.INTENT_KEY_4, 0);
                isPause = getIntent().getBooleanExtra(Constants.INTENT_KEY_5, false);
                if (isPause) {//开始
                    btnPlay.setImageResource(R.mipmap.btn_safe_full_play);
                } else {//暂停
                    btnPlay.setImageResource(R.mipmap.btn_safe_pause);
                }
                seekBar.setProgress(seek);
//                mDoorAccessManager.seekPlayBack(mSessionId, seek * 100 / max);
//                if (max > seek) {
//                    mDoorAccessManager.startPlayBack(mSessionId);
//                }
                tvPlaybackCurrentTime.setText(String.valueOf(format(seek)));
                tvPlaybackCountTime.setText(String.valueOf(format(max)));
            }
            mDoorAccessManager.updatePlayBackWindow(mSessionId, surfaceView);

        } else {
            try {
                familyID = DataManager.getDockKey();
            } catch (Exception e) {
                LoginUtil.toLoginActivity();
                e.printStackTrace();
                return;
            }
            layoutLive.setVisibility(View.VISIBLE);
            mDoorAccessManager.addConversationUIListener(this);
            setVideo();
//            surfaceView.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mDoorAccessManager.updateCallWindow(mSessionId, surfaceView);
//                }
//            }, 500);
        }
            surfaceView.getHolder().addCallback(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn1) {
            Intent intent = new Intent();
            intent.putExtra(Constants.INTENT_KEY_1, isPause);
            setResult(200, intent);
            finish();
        } else if (v.getId() == R.id.btn2) {//音量
        } else if (v.getId() == R.id.btn3) {//播放控制
            if (isPause) {//开始
                btnPlay.setImageResource(R.mipmap.btn_safe_pause);
                seekBar.setOnSeekBarChangeListener(this);
                mDoorAccessManager.startPlayBack(mSessionId);
                mDoorAccessManager.seekPlayBack(mSessionId, seek * 100 / max);
                mDoorAccessManager.updatePlayBackWindow(mSessionId, surfaceView);
            } else {//暂停
                btnPlay.setImageResource(R.mipmap.btn_safe_full_play);
                mDoorAccessManager.pausePlayBack(mSessionId);
            }
            isPause = !isPause;
        } else if (v.getId() == R.id.btn9) {//刷新
//            mDoorAccessManager.updateCallWindow(mSessionId, surfaceView);
            setVideo();
        }
    }
    private void setVideo() {

        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(mContext);
        }
        loadingDialog.setCancelOnTouchOutside(true);
        loadingDialog.setTitle("正在连接中...");
        loadingDialog.show();
        if (isSeeeionIdValid) {
            mDoorAccessManager.updateCallWindow(mSessionId, surfaceView);
            loadingDialog.dismiss();
            return;
        }
        List<DoorDevice> list = mDoorAccessManager.getDevices(familyID);
        for (DoorDevice device : list) {
            if (DoorDevice.TYPE_UNIT == device.getMyDeviceType()) {
                mSessionId = mDoorAccessManager.monitor(familyID, device);
                LogUtil.i("SafeOutsideFragment mSessionId " + mSessionId);

                mDoorAccessManager.updateCallWindow(mSessionId, surfaceView);
                return;
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (isPlayback) {
            mDoorAccessManager.updatePlayBackWindow(mSessionId, surfaceView);
        } else {
            mDoorAccessManager.updateCallWindow(mSessionId, surfaceView);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (isPlayback) {
            mDoorAccessManager.updatePlayBackWindow(mSessionId, null);
        } else {
            mDoorAccessManager.updateCallWindow(mSessionId, null);
        }
    }

    @Override
    public void startTransPort(String sessionID) {
        if (!TextUtils.equals(sessionID, mSessionId)) {
            return;
        }
        isSeeeionIdValid = true;
        LogUtil.i("SafeOutsideFragment 开始传输视频 mSessionId " + mSessionId);
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void refreshEvent(DoorEvent event) {

        if (!TextUtils.equals(mSessionId, event.sessionId)) {
            return;
        }
        if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandHangup)) {
            finish();
        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandSessionTimeout)) {
            isSeeeionIdValid=false;
//            finish();
        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandPickupByOther)) {
            finish();
        }

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        LogUtil.i("FullScreenActivity onProgressChanged seek = " + i);
        seek = i;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isTouchSeek = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isTouchSeek = false;
        mDoorAccessManager.seekPlayBack(mSessionId, seek * 100 / max);
    }

    @Override
    public void onMediaPlayEvent(String session_id, int event, long value) {
        if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventDuration) {
            max = (int) (value / unit);
            seekBar.setMax(max);
        } else if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventProgress) {
            seek = (int) (value / unit);
            if (isTouchSeek) {
                return;
            }
            seekBar.setOnSeekBarChangeListener(null);
            seekBar.setProgress(seek);
            seekBar.setOnSeekBarChangeListener(this);

            tvPlaybackCurrentTime.setText(String.valueOf(format(seek)));
            tvPlaybackCountTime.setText(String.valueOf(format(max)));
        } else if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventCompleted) {
            seek = max;
            seekBar.setOnSeekBarChangeListener(null);
            seekBar.setProgress(max);
            seekBar.setOnSeekBarChangeListener(this);
            mDoorAccessManager.pausePlayBack(mSessionId);
        }
        LogUtil.i("FullScreenActivity onMediaPlayEvent event = " + event + "  value = " + value + "  seek = " + seek + "  max = " + max);

    }

    private String format(int time) {

        int m = time / 60;
        int s = time % 60;

        String mstr;
        String sstr;
        if (m < 10) {
            mstr = "0" + m;
        } else {
            mstr = String.valueOf(m);
        }
        if (s < 10) {
            sstr = "0" + s;
        } else {
            sstr = String.valueOf(s);
        }

        return mstr + ":" + sstr;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isPlayback){
            mDoorAccessManager.updatePlayBackWindow(mSessionId,null);
            mDoorAccessManager.pausePlayBack(mSessionId);
        }else {
            mDoorAccessManager.updateCallWindow(mSessionId, null);
            mDoorAccessManager.hangupCall(mSessionId);
        }
        surfaceView.getHolder().removeCallback(this);
        DoorAccessManager.getInstance().removeConversationUIListener(this);
        DoorAccessManager.getInstance().removePlayBackListener(this);

    }
}
