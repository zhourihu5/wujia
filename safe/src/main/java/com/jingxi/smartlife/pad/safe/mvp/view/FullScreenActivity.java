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
import android.widget.Toast;

import com.intercom.sdk.IntercomConstants;
import com.intercom.sdk.IntercomObserver;
import com.jingxi.smartlife.pad.safe.R;
import com.jingxi.smartlife.pad.sdk.JXPadSdk;
import com.jingxi.smartlife.pad.sdk.doorAccess.DoorAccessManager;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorEvent;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessConversationUI;
import com.wujia.businesslib.Constants;
import com.wujia.lib_common.base.BaseActivity;
import com.wujia.lib_common.utils.LogUtil;

public class FullScreenActivity extends BaseActivity implements View.OnClickListener, SurfaceHolder.Callback, DoorAccessConversationUI, SeekBar.OnSeekBarChangeListener, IntercomObserver.OnPlaybackListener {

    private SurfaceView surfaceView;
    private DoorAccessManager manager;
    private String sessionId;
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

        manager = JXPadSdk.getDoorAccessManager();
        sessionId = getIntent().getStringExtra(Constants.INTENT_KEY_1);
        isPlayback = getIntent().getBooleanExtra(Constants.INTENT_KEY_2, false);

        if (isPlayback) {
            layoutPlaybackl.setVisibility(View.VISIBLE);
            seekBar = $(R.id.safe_seekbar);
            btnPlay = findViewById(R.id.btn3);

            tvPlaybackCurrentTime = $(R.id.safe_progress_time_current_tv);
            tvPlaybackCountTime = $(R.id.safe_progress_time_count_tv);

            btnPlay.setOnClickListener(this);
            manager.addPlayBackListener(this);

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
//                manager.seekPlayBack(sessionId, seek * 100 / max);
//                if (max > seek) {
//                    manager.startPlayBack(sessionId);
//                }
                tvPlaybackCurrentTime.setText(String.valueOf(format(seek)));
                tvPlaybackCountTime.setText(String.valueOf(format(max)));
            }
            manager.updatePlayBackWindow(sessionId, surfaceView);

        } else {
            layoutLive.setVisibility(View.VISIBLE);
            manager.addConversationUIListener(this);
            surfaceView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    manager.updateCallWindow(sessionId, surfaceView);
                }
            }, 500);
        }
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
                manager.startPlayBack(sessionId);
                manager.seekPlayBack(sessionId, seek * 100 / max);
                manager.updatePlayBackWindow(sessionId, surfaceView);
            } else {//暂停
                btnPlay.setImageResource(R.mipmap.btn_safe_full_play);
                manager.pausePlayBack(sessionId);
            }
            isPause = !isPause;
        } else if (v.getId() == R.id.btn9) {//刷新
            manager.updateCallWindow(sessionId, surfaceView);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
//        manager.updateCallWindow(sessionId, surfaceView);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (isPlayback) {
            manager.updatePlayBackWindow(sessionId, surfaceView);
        } else {
            manager.updateCallWindow(sessionId, null);
        }
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
        manager.seekPlayBack(sessionId, seek * 100 / max);
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
            manager.pausePlayBack(sessionId);
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
        manager.pausePlayBack(sessionId);
        DoorAccessManager.getInstance().removeConversationUIListener(this);
        DoorAccessManager.getInstance().removePlayBackListener(this);

    }
}
