package com.wujia.intellect.terminal.safe.mvp.view;

import android.os.Bundle;
import android.view.View;

import com.wujia.intellect.terminal.safe.R;
import com.wujia.lib_common.base.BaseActivity;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-21
 * description ：
 */
public class VideoCallActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected int getLayout() {
        return R.layout.activity_video_call;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        findViewById(R.id.btn1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn1) {
            finish();
        }
    }
}
