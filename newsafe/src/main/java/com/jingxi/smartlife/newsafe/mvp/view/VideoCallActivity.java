package com.jingxi.smartlife.newsafe.mvp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.jingxi.smartlife.newsafe.R;
import com.wujia.businesslib.dialog.LoadingDialog;
import com.wujia.lib_common.base.BaseActivity;

import org.jetbrains.annotations.NotNull;
import org.linphone.mediastream.video.display.GL2JNIView;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-21
 * description ：
 */
public class VideoCallActivity extends BaseActivity implements View.OnClickListener {

    private String sessionId;
    private GL2JNIView surfaceView;
    private LoadingDialog loadingDialog;
    private View frore;
    private Runnable runnable;
    private ImageButton btnCall;
    private boolean btnCallFlag;
    @NotNull
    public static boolean started=false;


    @Override
    protected int getLayout() {
        return R.layout.activity_video_call;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        findViewById(R.id.btn1).setOnClickListener(this);

        surfaceView = findViewById(R.id.surfaceView);
        frore = findViewById(R.id.surface_foreground);

        btnCall = findViewById(R.id.btn3);
        btnCall.setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);

        sessionId = getIntent().getStringExtra("sessionId");
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(this);
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
                //TODO接听


                btnCall.setBackgroundResource(R.mipmap.btn_safe_hangup);
            } else {
                finish();
            }
            btnCallFlag = !btnCallFlag;
        } else if (v.getId() == R.id.btn4) {//开门
            //TODO 开门
            finish();
        } else if (v.getId() == R.id.btn6) {//音量
            updateSurface();
        } else if (v.getId() == R.id.btn9) {//挂断
            finish();
        }
    }

    @Override
    public void finish() {
        //TODO 挂断
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != runnable && null != surfaceView) {
            surfaceView.removeCallbacks(runnable);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSurface();
        surfaceView.postDelayed(runnable, 1000);
    }

    public void updateSurface() {
    }


}
