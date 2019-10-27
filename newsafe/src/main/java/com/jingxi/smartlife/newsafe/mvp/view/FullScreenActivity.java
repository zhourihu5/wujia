package com.jingxi.smartlife.newsafe.mvp.view;

import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jingxi.smartlife.newsafe.R;
import com.wujia.lib_common.base.BaseActivity;

public class FullScreenActivity extends BaseActivity implements View.OnClickListener {

    private SurfaceView surfaceView;
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


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn1) {

        } else if (v.getId() == R.id.btn2) {//音量
        } else if (v.getId() == R.id.btn3) {//播放控制
        } else if (v.getId() == R.id.btn9) {//刷新
        }
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
    }
}
