package com.jingxi.smartlife.newsafe.mvp.view;

import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;

import com.jingxi.smartlife.newsafe.R;
import com.wujia.lib_common.base.BaseActivity;

public class FullScreenLiveActivity extends BaseActivity implements View.OnClickListener {

    private SurfaceView surfaceView;

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

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img1) {
            setResult(200);
            finish();
        } else if (v.getId() == R.id.img2) {//音量
        } else if (v.getId() == R.id.img3) {//刷新
        }
    }
}
